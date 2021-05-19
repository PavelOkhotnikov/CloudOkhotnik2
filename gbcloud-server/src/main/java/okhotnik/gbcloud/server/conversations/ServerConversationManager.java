package okhotnik.gbcloud.server.conversations;

import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.conversations.AbstractConversationManager;
import okhotnik.gbcloud.server.filesystem.ServerDirectory;
import okhotnik.gbcloud.server.persistence.entitites.User;

public class ServerConversationManager extends AbstractConversationManager
{
    @Getter
    @Setter
    private User user;

    public boolean isAuthenticated()
    {
        return user != null;
    }

    @Getter
    private ServerDirectory serverDirectory = new ServerDirectory();
}
