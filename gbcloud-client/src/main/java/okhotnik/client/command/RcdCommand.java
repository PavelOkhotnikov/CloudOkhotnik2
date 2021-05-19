package okhotnik.client.command;

import okhotnik.client.conversations.CdClientAgent;
import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.gbcloud.common.conversations.ActiveAgent;

import javax.inject.Inject;

@Importance(2)
@Keyword(Const.RCD_COMMAND_KEYWORD)
@Arguments(Const.TARGET_DIRECTORY_ARGUMENT_NAME)
@Description(Const.RCD_COMMAND_DESCRIPTION)
public class RcdCommand extends AbstractCommand
{
    @Inject
    @ActiveAgent
    private CdClientAgent agent;

    @Inject
    private ClientConversationManager conversationManager;

    @Override
    public void run()
    {
        final String targetDirectory = getArgumentValue(Const.TARGET_DIRECTORY_ARGUMENT_NAME);
        agent.setTargetDirectory(targetDirectory);
        conversationManager.startConversation(agent);
    }
}
