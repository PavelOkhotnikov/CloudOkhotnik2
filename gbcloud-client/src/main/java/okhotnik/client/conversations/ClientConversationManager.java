package okhotnik.client.conversations;

import okhotnik.gbcloud.common.conversations.AbstractConversationManager;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.IConversation;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;

@ApplicationScoped
public class ClientConversationManager extends AbstractConversationManager
{
    private static final AnnotationLiteral<ActiveAgent> ACTIVE_AGENT_ANNOTATION = new AnnotationLiteral<ActiveAgent>() {};

    public <T extends IConversation> void startConversation(Class<T> conversationClass)
    {
        startConversation(CDI.current().select(conversationClass, ACTIVE_AGENT_ANNOTATION).get());
    }

    public void authenticate()
    {
        startConversation(AuthenticationClientAgent.class);
    }
}
