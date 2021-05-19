package okhotnik.client.handlers;

import okhotnik.client.logging.ClientLogger;
import okhotnik.client.command.CLI;
import okhotnik.client.events.EAuthSuccess;
import okhotnik.gbcloud.common.conversations.IConversationManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HAuthSuccess
{
    @Inject
    private CLI cli;

    private void handleAuthSuccess(@ObservesAsync final EAuthSuccess event)
    {
        final IConversationManager conversationManager = event.getConversationManager();
        final String server = conversationManager.getTransportChannel().getRemoteAddress();
        cli.setRemoteServer(server);
        cli.setRemoteDirectory("/");
        cli.updatePrompt();
    }
}
