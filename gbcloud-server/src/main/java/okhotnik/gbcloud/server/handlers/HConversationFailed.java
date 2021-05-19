package okhotnik.gbcloud.server.handlers;

import okhotnik.gbcloud.common.events.EConversationFailed;
import okhotnik.gbcloud.server.logging.ServerLogger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ServerLogger.class)
public class HConversationFailed
{
    private void handleConversationFailed(@ObservesAsync final EConversationFailed event)
    {

    }
}
