package okhotnik.client.conversations;

import org.jetbrains.annotations.NotNull;
import okhotnik.client.config.ClientConfig;
import okhotnik.client.events.EAuthFailure;
import okhotnik.client.events.EAuthSuccess;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.auth.AuthFailResponse;
import okhotnik.gbcloud.common.messages.auth.AuthMessage;
import okhotnik.gbcloud.common.messages.auth.AuthSuccessResponse;
import okhotnik.gbcloud.common.utils.Util;

import javax.enterprise.event.Event;
import javax.inject.Inject;

@ActiveAgent
@StartsWith(AuthMessage.class)
@Expects({AuthFailResponse.class, AuthSuccessResponse.class})
public class AuthenticationClientAgent extends AbstractConversation
{
    @Inject
    private ClientConfig config;

    @Inject
    private Event<EAuthFailure> authFailureBus;

    @Inject
    private Event<EAuthSuccess> authSuccessBus;

    @Override
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        final AuthMessage authMessage = (AuthMessage) initialMessage;
        authMessage.setLogin(config.getLogin());
        authMessage.setPasswordHash(Util.hash(config.getPassword()));
    }

    @Override
    public synchronized void processMessageFromPeer(@NotNull IMessage message)
    {
        if (message instanceof AuthFailResponse)
        {
            final AuthFailResponse authFailResponse = (AuthFailResponse) message;
            authFailureBus.fireAsync(new EAuthFailure(authFailResponse.getReason()));
        }
        else if (message instanceof AuthSuccessResponse)
        {
            authSuccessBus.fireAsync(new EAuthSuccess(getConversationManager()));
        }
    }
}
