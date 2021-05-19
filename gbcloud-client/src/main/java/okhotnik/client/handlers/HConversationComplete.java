package okhotnik.client.handlers;

import okhotnik.client.logging.ClientLogger;
import okhotnik.gbcloud.common.events.EConversationComplete;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HConversationComplete
{
    private void handleConversationComplete(@ObservesAsync final EConversationComplete event)
    {

    }
}
