package okhotnik.client.logging;

import lombok.SneakyThrows;
import okhotnik.client.handlers.*;
import okhotnik.client.events.EAuthFailure;
import okhotnik.client.events.EMessageReceived;
import okhotnik.gbcloud.common.events.*;
import ru.okhotnik.gbcloud.client.handlers.*;
import okhotnik.gbcloud.common.conversations.IConversationManager;
import okhotnik.gbcloud.common.logging.CommonLogger;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.transport.ITransportChannel;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class ClientLogger extends CommonLogger
{
    @Inject
    private IConversationManager conversationManager;

    @AroundInvoke
    @SneakyThrows
    private Object logMethod(final InvocationContext invocationContext)
    {
        final Class targetClass = invocationContext.getTarget().getClass();

        if (HMessageReceived.class.isAssignableFrom(targetClass))
        {
            final EMessageReceived event = (EMessageReceived) invocationContext.getParameters()[0];
            final IMessage message = event.getMessage();
            final ITransportChannel transportChannel = conversationManager.getTransportChannel();
            String remoteAddress = null;
            if (transportChannel.isConnected())
            {
                remoteAddress = transportChannel.getRemoteAddress();
            }

            write("Message of type " +
                    message.getClass().getSimpleName() +
                    " bound to conversation " +
                    message.getConversationId() +
                    " was received from " +
                    remoteAddress);
        }
        else if (HAuthSuccess.class.isAssignableFrom(targetClass))
        {
            write("Authentication success.");
        }
        else if (HAuthFailure.class.isAssignableFrom(targetClass))
        {
            final EAuthFailure event = (EAuthFailure) invocationContext.getParameters()[0];
            write("Authentication failure. Reason: " + event.getReason());
        }
        else if (HConversationFailed.class.isAssignableFrom(targetClass))
        {
            final EConversationFailed event = (EConversationFailed) invocationContext.getParameters()[0];
            write("Conversation " + event.getConversation().getId() + " failed. Reason: " +
                    event.getReason() + ". Remote: " + event.isRemote());
        }
        else if (HConversationTimedOut.class.isAssignableFrom(targetClass))
        {
            final EConversationTimedOut event = (EConversationTimedOut) invocationContext.getParameters()[0];
            write("Conversation " + event.getConversation().getId() + " timed out.");
        }
        else if (HConversationComplete.class.isAssignableFrom(targetClass))
        {
            final EConversationComplete event = (EConversationComplete) invocationContext.getParameters()[0];
            write("Conversation " + event.getConversation().getId() + " completed.");
        }
        else if (HConnectionClosed.class.isAssignableFrom(targetClass))
        {
            write("Disconnected.");
        }
        else if (HDirectoryCreated.class.isAssignableFrom(targetClass))
        {
            final EDirectoryCreated event = (EDirectoryCreated) invocationContext.getParameters()[0];
            write("Directory created: " + event.getLocalAbsolutePath());
        }
        else if (HFileCreated.class.isAssignableFrom(targetClass))
        {
            final EFileCreated event = (EFileCreated) invocationContext.getParameters()[0];
            write("File created: " + event.getLocalAbsolutePath());
        }

        return invocationContext.proceed();
    }
}
