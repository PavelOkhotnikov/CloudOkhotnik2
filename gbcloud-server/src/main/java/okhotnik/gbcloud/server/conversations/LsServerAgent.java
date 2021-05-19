package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerErrorResponse;
import okhotnik.gbcloud.common.messages.ls.LsRequest;
import okhotnik.gbcloud.common.messages.ls.LsResponse;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.nio.file.Files;

@RespondsTo(LsRequest.class)
public class LsServerAgent extends ServerAgent
{
    @Inject
    private LsResponse response;

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        try
        {
            Files.newDirectoryStream(getCurrentDirectory()).forEach(element ->
                    response.getElements().add(new LsResponse.FilesystemElement(element)));
            sendMessageToPeer(response);
        }
        catch (Exception e)
        {
            sendMessageToPeer(new ServerErrorResponse());
        }
    }
}
