package okhotnik.client.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhotnik.gbcloud.common.conversations.IConversationManager;

@NoArgsConstructor
@AllArgsConstructor
public class EAuthSuccess
{
    @Getter
    @Setter
    private IConversationManager conversationManager;
}
