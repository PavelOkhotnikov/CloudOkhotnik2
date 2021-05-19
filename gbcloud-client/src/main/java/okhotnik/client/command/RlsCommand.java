package okhotnik.client.command;

import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.conversations.LsClientAgent;

import javax.inject.Inject;

@Importance(2)
@Keyword(Const.RLS_COMMAND_KEYWORD)
@Description(Const.RLS_COMMAND_DESCRIPTION)
public class RlsCommand extends AbstractCommand
{
    @Inject
    private ClientConversationManager conversationManager;

    @Override
    public void run()
    {
        conversationManager.startConversation(LsClientAgent.class);
    }
}
