package okhotnik.client.command;

import okhotnik.client.config.ClientConfig;
import okhotnik.gbcloud.common.transport.INetworkEndpoint;

import javax.inject.Inject;

@Importance(6)
@Keyword(Const.OPEN_COMMAND_KEYWORD)
@Description(Const.OPEN_COMMAND_DESCRIPTION)
@Arguments({Const.SERVER_ARGUMENT_NAME, Const.LOGIN_ARGUMENT_NAME, Const.PASSWORD_ARGUMENT_NAME})
public class OpenCommand extends AbstractCommand
{
    @Inject
    private ClientConfig config;

    @Inject
    private INetworkEndpoint networkEndpoint;

    @Override
    public void run()
    {
        config.setServerAddress(getArgumentValue(Const.SERVER_ARGUMENT_NAME));
        config.setLogin(getArgumentValue(Const.LOGIN_ARGUMENT_NAME));
        config.setPassword(getArgumentValue(Const.PASSWORD_ARGUMENT_NAME));

        networkEndpoint.start();
    }
}
