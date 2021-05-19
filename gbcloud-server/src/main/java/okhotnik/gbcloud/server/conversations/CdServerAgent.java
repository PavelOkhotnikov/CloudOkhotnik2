package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.RespondsTo;
import okhotnik.gbcloud.common.filesystem.PathDoesNotExistException;
import okhotnik.gbcloud.common.filesystem.PathIsNotADirectoryException;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ServerOkResponse;
import okhotnik.gbcloud.common.messages.cd.CdFailResponse;
import okhotnik.gbcloud.common.messages.cd.CdRequest;
import okhotnik.gbcloud.server.filesystem.ServerDirectory;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@RespondsTo(CdRequest.class)
public class CdServerAgent extends ServerAgent
{
    private static final String PATH_IS_NOT_A_DIR_MESSAGE = "Specified path is not a directory";
    private static final String PATH_DOES_NOT_EXIST_MESSAGE = "Specified path does not exist";
    private static final String PATH_ABSOLUTE_MESSAGE = "Absolute paths are not allowed";

    @Inject
    private ServerOkResponse okResponse;

    @Inject
    private CdFailResponse failResponse;

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        final CdRequest request = (CdRequest) message;
        String failureReason = null;
        try
        {
            getServerDirectory().cd(request.getTargetDirectory());
        }
        catch (PathIsNotADirectoryException e)
        {
            failureReason = PATH_IS_NOT_A_DIR_MESSAGE;
        }
        catch (PathDoesNotExistException e)
        {
            failureReason = PATH_DOES_NOT_EXIST_MESSAGE;
        }
        catch (ServerDirectory.AbsolutePathException e)
        {
            failureReason = PATH_ABSOLUTE_MESSAGE;
        }

        if (failureReason != null)
        {
            failResponse.setReason(failureReason);
            sendMessageToPeer(failResponse);
        }
        else
        {
            sendMessageToPeer(okResponse);
        }
    }
}
