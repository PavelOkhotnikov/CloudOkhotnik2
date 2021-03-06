package okhotnik.client.command;

import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.conversations.GetClientAgent;
import okhotnik.gbcloud.common.conversations.ActiveAgent;

import javax.inject.Inject;

@Importance(3)
@Keyword(Const.GET_COMMAND_KEYWORD)
@Description(Const.GET_COMMAND_DESCRIPTION)
@Arguments(Const.TARGET_FILE_ARGUMENT_NAME)
public class GetCommand extends AbstractCommand
{
    @Inject
    @ActiveAgent
    private GetClientAgent agent;

    @Inject
    private ClientConversationManager conversationManager;

    @Override
    public void run()
    {
        agent.setFileName(getArgumentValue(Const.TARGET_FILE_ARGUMENT_NAME));
        conversationManager.startConversation(agent);
    }
}
