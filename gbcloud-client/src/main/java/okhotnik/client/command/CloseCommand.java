package okhotnik.client.command;

import okhotnik.gbcloud.common.transport.INetworkEndpoint;

import javax.inject.Inject;

@Importance(5)
@Keyword(Const.CLOSE_COMMAND_KEYWORD)
@Description(Const.CLOSE_COMMAND_DESCRIPTION)
public class CloseCommand extends AbstractCommand
{
    @Inject
    private INetworkEndpoint networkEndpoint;

    @Override
    public void run()
    {
        networkEndpoint.stop();
    }
}
