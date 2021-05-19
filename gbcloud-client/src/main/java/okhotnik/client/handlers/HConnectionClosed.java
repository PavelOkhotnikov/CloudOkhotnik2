package okhotnik.client.handlers;

import okhotnik.client.command.CLI;
import okhotnik.client.events.EConnectionClosed;
import okhotnik.client.logging.ClientLogger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HConnectionClosed
{
    @Inject
    private CLI cli;

    private void handleConnectionClosed(@ObservesAsync final EConnectionClosed event)
    {
        System.out.println("Disconnected.");
        cli.resetPrompt();
        cli.updatePrompt();
    }
}
