package okhotnik.client.conversations;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import okhotnik.client.command.CLI;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerOkResponse;
import okhotnik.gbcloud.common.messages.cd.CdFailResponse;
import okhotnik.gbcloud.common.messages.cd.CdRequest;

import javax.inject.Inject;
import java.nio.file.Paths;

@ActiveAgent
@StartsWith(CdRequest.class)
@Expects({ServerOkResponse.class, CdFailResponse.class})
public class CdClientAgent extends AbstractConversation
{
    private static final String FAIL_MESSAGE = "Remote change dir failed. Reason: ";

    @Inject
    private CLI cli;

    @Getter
    @Setter
    private String targetDirectory;

    @Override
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        final CdRequest cdRequest = (CdRequest) initialMessage;
        cdRequest.setTargetDirectory(targetDirectory);
    }

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        if (message instanceof CdFailResponse)
        {
            final CdFailResponse cdFailResponse = (CdFailResponse) message;
            System.out.println();
            System.out.println(FAIL_MESSAGE + cdFailResponse.getReason());
        }
        else if (message instanceof ServerOkResponse)
        {
            cli.setRemoteDirectory(Paths.get(targetDirectory).normalize().toString());
            cli.updatePrompt();
        }
    }
}
