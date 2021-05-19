package okhotnik.client.command;

import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.conversations.PwdClientAgent;

import javax.inject.Inject;

@Importance(2)
@Keyword(Const.RPWD_COMMAND_KEYWORD)
@Description(Const.RPWD_COMMAND_DESCRIPTION)
public class RpwdCommand extends AbstractCommand
{
    @Inject
    private ClientConversationManager conversationManager;

    @Override
    public void run()
    {
        conversationManager.startConversation(PwdClientAgent.class);
    }
}
