package okhotnik.gbcloud.server.conversations;

import okhotnik.gbcloud.common.conversations.AbstractConversation;
import okhotnik.gbcloud.common.conversations.PassiveAgent;
import okhotnik.gbcloud.server.filesystem.ServerDirectory;
import okhotnik.gbcloud.server.persistence.entitites.User;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

@PassiveAgent
public abstract class ServerAgent extends AbstractConversation
{
    @Nullable
    public User getUser()
    {
        final ServerConversationManager conversationManager = (ServerConversationManager) getConversationManager();
        return conversationManager.getUser();
    }

    public Path getCurrentDirectory()
    {
        final ServerConversationManager conversationManager = (ServerConversationManager) getConversationManager();
        return conversationManager.getServerDirectory().getCurrentDirectory();
    }

    public ServerDirectory getServerDirectory()
    {
        final ServerConversationManager conversationManager = (ServerConversationManager) getConversationManager();
        return conversationManager.getServerDirectory();
    }
}
