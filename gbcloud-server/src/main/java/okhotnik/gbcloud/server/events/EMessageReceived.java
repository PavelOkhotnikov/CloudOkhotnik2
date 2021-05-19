package okhotnik.gbcloud.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.server.conversations.ServerConversationManager;
import okhotnik.gbcloud.common.messages.IMessage;

@Getter
@Setter
@AllArgsConstructor
public class EMessageReceived
{
    private ServerConversationManager conversationManager;

    private IMessage message;
}
