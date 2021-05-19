package okhotnik.client.conversations;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import okhotnik.gbcloud.common.conversations.*;
import org.jetbrains.annotations.NotNull;
import okhotnik.client.command.CLI;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.transfer.FileDataRequest;
import okhotnik.gbcloud.common.messages.transfer.FileTransferError;
import okhotnik.gbcloud.common.messages.transfer.GetRequest;

import javax.inject.Inject;

@ActiveAgent
@StartsWith(GetRequest.class)
@Expects({FileDataRequest.class, FileTransferError.class})
public class GetClientAgent extends AbstractConversation
{
    @Getter
    @Setter
    private String fileName;

    @Inject
    private CLI cli;

    @Inject
    private FileTransferReceivingAgent receivingAgent;

    @Override
    @SneakyThrows
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        final GetRequest request = (GetRequest) initialMessage;
        request.setFileName(fileName);
        receivingAgent.start(cli.getCurrentDirectory().resolve(fileName), this);
    }

    @Override
    protected void beforeFinish()
    {
        receivingAgent.stop();
    }

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        receivingAgent.processMessage(message);
    }
}
