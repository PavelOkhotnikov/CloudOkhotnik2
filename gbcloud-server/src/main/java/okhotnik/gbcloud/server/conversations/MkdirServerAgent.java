package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerErrorResponse;
import okhotnik.gbcloud.common.messages.ServerOkResponse;
import okhotnik.gbcloud.common.messages.mkdir.MkdirRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RespondsTo(MkdirRequest.class)
public class MkdirServerAgent extends ServerAgent
{
    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        final MkdirRequest request = (MkdirRequest) message;
        final Path newPath = getCurrentDirectory().resolve(request.getDirectoryPath());
        try
        {
            Files.createDirectories(newPath);
        }
        catch (IOException e)
        {
            sendMessageToPeer(new ServerErrorResponse(e.getMessage()));
            return;
        }

        sendMessageToPeer(new ServerOkResponse());
    }
}
