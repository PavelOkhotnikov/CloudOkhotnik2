package okhotnik.client.handlers;

import okhotnik.client.logging.ClientLogger;
import okhotnik.gbcloud.common.events.EConversationFailed;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HConversationFailed
{
    private void handleConversationFailed(@ObservesAsync final EConversationFailed event)
    {

    }
}
