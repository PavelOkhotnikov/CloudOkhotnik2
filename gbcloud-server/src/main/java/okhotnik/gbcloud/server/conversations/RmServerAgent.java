package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerErrorResponse;
import okhotnik.gbcloud.common.messages.ServerOkResponse;
import okhotnik.gbcloud.common.messages.rm.RmRequest;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

@RespondsTo(RmRequest.class)
public class RmServerAgent extends ServerAgent
{
    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        final RmRequest request = (RmRequest) message;
        final Path pathToRemove = getCurrentDirectory().resolve(request.getTargetPath());
        try
        {
            if (Files.isDirectory(pathToRemove))
            {
                Files.walk(pathToRemove)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
            else
            {
                Files.delete(pathToRemove);
            }
        }
        catch (IOException e)
        {
            sendMessageToPeer(new ServerErrorResponse(e.getMessage()));
            return;
        }

        sendMessageToPeer(new ServerOkResponse());
    }
}
