package okhotnik.gbcloud.common.events;

import okhotnik.gbcloud.common.conversations.IConversation;

public class EConversationComplete extends EConversationEvent
{
    public EConversationComplete(IConversation conversation)
    {
        super(conversation);
    }
}
