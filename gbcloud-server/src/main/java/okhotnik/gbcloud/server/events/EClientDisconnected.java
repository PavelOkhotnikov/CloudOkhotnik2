package okhotnik.gbcloud.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.server.conversations.ServerConversationManager;

@AllArgsConstructor
public class EClientDisconnected
{
    @Getter
    @Setter
    private ServerConversationManager conversationManager;
}
