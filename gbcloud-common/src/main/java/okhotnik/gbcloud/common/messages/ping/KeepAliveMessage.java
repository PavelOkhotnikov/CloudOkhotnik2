package okhotnik.gbcloud.common.messages.ping;

import okhotnik.gbcloud.common.messages.AbstractMessage;

public class KeepAliveMessage extends AbstractMessage
{
    byte[] buffer = new byte[1024 * 1024];
}
