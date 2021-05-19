package okhotnik.gbcloud.server.conversations;

import org.jetbrains.annotations.NotNull;
import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.pwd.PwdRequest;
import okhotnik.gbcloud.common.messages.pwd.PwdResponse;

import javax.inject.Inject;

@RespondsTo(PwdRequest.class)
public class PwdServerAgent extends ServerAgent
{
    @Inject
    private PwdResponse response;

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        response.setCurrentDirectory(getServerDirectory().pwd());
        sendMessageToPeer(response);
    }
}
