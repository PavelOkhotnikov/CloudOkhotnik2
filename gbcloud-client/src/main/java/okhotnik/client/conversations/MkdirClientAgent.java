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
import okhotnik.gbcloud.common.messages.mkdir.MkdirRequest;

@ActiveAgent
@StartsWith(MkdirRequest.class)
@Expects(ServerOkResponse.class)
public class MkdirClientAgent extends AbstractConversation
{
    @Getter
    @Setter
    private String directoryPath;

    @Override
    protected void beforeStart(@NotNull IMessage initialMessage)
    {
        final MkdirRequest request = (MkdirRequest) initialMessage;
        request.setDirectoryPath(directoryPath);
    }

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {

    }
}
