package okhotnik.client.conversations;

import org.jetbrains.annotations.NotNull;
import okhotnik.client.command.CLI;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.pwd.PwdRequest;
import okhotnik.gbcloud.common.messages.pwd.PwdResponse;

import javax.inject.Inject;

@ActiveAgent
@StartsWith(PwdRequest.class)
@Expects(PwdResponse.class)
public class PwdClientAgent extends AbstractConversation
{
    @Inject
    private CLI cli;

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        final PwdResponse response = (PwdResponse) message;
        System.out.println();
        System.out.println("Current remote dir is " + response.getCurrentDirectory());
        cli.updatePrompt();
    }
}
