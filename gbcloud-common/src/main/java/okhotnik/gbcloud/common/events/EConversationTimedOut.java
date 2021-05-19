package okhotnik.gbcloud.common.events;

import okhotnik.gbcloud.common.conversations.IConversation;

public class EConversationTimedOut extends EConversationFailed
{
    public EConversationTimedOut(final IConversation conversation)
    {
        super(conversation);
    }
}
