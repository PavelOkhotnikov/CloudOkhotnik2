package okhotnik.gbcloud.common.conversations;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import okhotnik.gbcloud.common.events.EFileTransferComplete;
import okhotnik.gbcloud.common.events.EFileTransferFailed;
import okhotnik.gbcloud.common.events.EFileTransferProgress;
import okhotnik.gbcloud.common.filesystem.IFileWriter;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.transfer.FileDataAcceptedResponse;
import okhotnik.gbcloud.common.messages.transfer.FileDataRequest;
import okhotnik.gbcloud.common.messages.transfer.FileTransferError;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;

public class FileTransferReceivingAgent
{
    @Inject
    private IFileWriter fileWriter;

    @Getter
    @Setter
    private Path filePath;

    @Inject
    private Event<EFileTransferFailed> fileTransferFailedBus;

    @Inject
    private Event<EFileTransferProgress> fileTransferProgressBus;

    @Inject
    private Event<EFileTransferComplete> fileTransferCompleteBus;

    private IConversation conversation;

    public void start(final @NotNull Path filePath,
                      final @NotNull IConversation conversation) throws IOException
    {
        this.conversation = conversation;
        fileWriter.open(filePath);
        this.filePath = filePath;
    }

    public void stop()
    {
        try
        {
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void processMessage(final @NotNull IMessage message)
    {
        try
        {
            if (message instanceof FileTransferError)
            {
                final FileTransferError errorMessage = (FileTransferError) message;
                final EFileTransferFailed event = new EFileTransferFailed(conversation);
                event.setRemote(false);
                event.setReason(errorMessage.getReason());
                fileTransferFailedBus.fireAsync(event);
                return;
            }

            final FileDataRequest request = (FileDataRequest) message;
            fileWriter.write(request.getData());

            final FileDataAcceptedResponse response = new FileDataAcceptedResponse();
            response.setCseq(request.getCseq());
            conversation.sendMessageToPeer(response);
            if (!request.isLast())
            {
                fileTransferProgressBus.fireAsync(new EFileTransferProgress(request.getPercentComplete(), filePath.getFileName().toString()));
                conversation.continueConversation();
            }
            else
            {
                fileTransferCompleteBus.fireAsync(new EFileTransferComplete(filePath.getFileName().toString()));
            }
        }
        catch (IOException e)
        {
            conversation.sendMessageToPeer(new FileTransferError(e.getMessage()));
            final EFileTransferFailed event = new EFileTransferFailed(conversation);
            event.setRemote(false);
            event.setReason(e.getMessage());
            fileTransferFailedBus.fireAsync(event);
        }
    }
}
