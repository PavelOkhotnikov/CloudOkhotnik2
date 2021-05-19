package okhotnik.client.handlers;

import okhotnik.client.conversations.ClientConversationManager;
import okhotnik.client.conversations.PutClientAgent;
import okhotnik.client.logging.ClientLogger;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.events.EFileCreated;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.nio.file.Paths;

@ApplicationScoped
@Interceptors(ClientLogger.class)
public class HFileCreated
{
    private static final AnnotationLiteral<ActiveAgent> ACTIVE_AGENT_ANNOTATION = new AnnotationLiteral<ActiveAgent>() {};

    @Inject
    private ClientConversationManager conversationManager;

    private void handleFileCreated(@ObservesAsync final EFileCreated event)
    {
        final PutClientAgent agent = CDI.current().select(PutClientAgent.class, ACTIVE_AGENT_ANNOTATION).get();
        agent.setLocalRoot(Paths.get(event.getLocalRoot()));
        agent.setRelativeFilePath(Paths.get(event.getLocalRelativePath()));
        conversationManager.startConversation(agent);
    }
}
