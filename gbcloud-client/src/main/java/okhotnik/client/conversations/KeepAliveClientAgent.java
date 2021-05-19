package okhotnik.client.conversations;

import org.jetbrains.annotations.NotNull;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ping.KeepAliveMessage;

@ActiveAgent
@StartsWith(KeepAliveMessage.class)
@Expects(KeepAliveMessage.class)
public class KeepAliveClientAgent extends AbstractConversation
{
    private long startTime;

    @Override
    public synchronized void processMessageFromPeer(final @NotNull IMessage message)
    {
        final long delta = System.currentTimeMillis() - startTime;
        System.out.println();
        System.out.println("Response from " +
                getConversationManager().getTransportChannel().getRemoteAddress() +
                ". rtt=" +
                delta +
                "ms");
    }

    @Override
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        startTime = System.currentTimeMillis();
    }
}
