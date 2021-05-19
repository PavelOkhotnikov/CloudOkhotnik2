package okhotnik.client.conversations;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerOkResponse;
import okhotnik.gbcloud.common.messages.rm.RmRequest;

@ActiveAgent
@StartsWith(RmRequest.class)
@Expects(ServerOkResponse.class)
public class RmClientAgent extends AbstractConversation
{
    @Getter
    @Setter
    private String targetPath;

    @Override
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        final RmRequest request = (RmRequest) initialMessage;
        request.setTargetPath(targetPath);
    }

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {

    }
}
