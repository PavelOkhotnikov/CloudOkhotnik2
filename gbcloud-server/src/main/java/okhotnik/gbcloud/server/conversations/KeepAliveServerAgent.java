package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ping.KeepAliveMessage;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@RespondsTo(KeepAliveMessage.class)
public class KeepAliveServerAgent extends ServerAgent
{
    @Inject
    private KeepAliveMessage response;

    @Override
    public synchronized void processMessageFromPeer(final @NotNull IMessage message)
    {
        sendMessageToPeer(response);
    }
}
