package okhotnik.client.command;

import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.conversations.KeepAliveClientAgent;

import javax.inject.Inject;

@Keyword(Const.PING_COMMAND_KEYWORD)
@Description(Const.PING_COMMAND_DESCRIPTION)
public class PingCommand extends AbstractCommand
{
    @Inject
    private ClientConversationManager conversationManager;

    @Override
    public void run()
    {
        conversationManager.startConversation(KeepAliveClientAgent.class);
    }
}
