package okhotnik.client.conversations;

import org.jetbrains.annotations.NotNull;
import okhotnik.client.command.CLI;
import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.ActiveAgent;
import okhotnik.gbcloud.common.conversations.Expects;
import okhotnik.gbcloud.common.conversations.StartsWith;
import okhotnik.gbcloud.common.messages.IMessage;
import okhotnik.gbcloud.common.messages.ls.LsRequest;
import okhotnik.gbcloud.common.messages.ls.LsResponse;

import javax.inject.Inject;

@ActiveAgent
@StartsWith(LsRequest.class)
@Expects(LsResponse.class)
public class LsClientAgent extends AbstractConversation
{
    @Inject
    private CLI cli;

    @Override
    public void processMessageFromPeer(@NotNull IMessage message)
    {
        System.out.println();
        final LsResponse response = (LsResponse) message;
        response.getElements().stream()
                .filter(LsResponse.FilesystemElement::isDirectory)
                .forEach(directory -> System.out.println("<DIR>\t\t\t\t\t" + directory.getName()));

        response.getElements().stream()
                .filter(filesystemElement -> !filesystemElement.isDirectory())
                .forEach(file -> System.out.println("\t\t" + file.getSize() + "\t\t\t" + file.getName()));
        cli.updatePrompt();
    }
}
