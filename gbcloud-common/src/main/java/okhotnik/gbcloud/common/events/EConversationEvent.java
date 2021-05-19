package okhotnik.gbcloud.common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.conversations.IConversation;

@AllArgsConstructor
public abstract class EConversationEvent
{
    @Getter
    @Setter
    private IConversation conversation;
}
