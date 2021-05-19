package okhotnik.gbcloud.server.handlers;

import okhotnik.gbcloud.server.logging.ServerLogger;
import okhotnik.gbcloud.common.events.EConversationComplete;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ServerLogger.class)
public class HConversationComplete
{
    private void handleConversationComplete(@ObservesAsync final EConversationComplete event)
    {

    }
}
