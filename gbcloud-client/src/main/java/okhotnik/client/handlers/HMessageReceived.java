package okhotnik.client.handlers;

import okhotnik.client.events.EMessageReceived;
import okhotnik.client.logging.ClientLogger;
import okhotnik.gbcloud.common.conversations.IConversationManager;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HMessageReceived
{
    @Inject
    private IConversationManager conversationManager;

    private void handleMessageReceived(@ObservesAsync final EMessageReceived event)
    {
        conversationManager.dispatchMessage(event.getMessage());
    }
}
