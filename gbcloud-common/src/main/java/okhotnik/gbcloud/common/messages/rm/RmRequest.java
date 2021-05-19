package okhotnik.gbcloud.common.messages.rm;

import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.AbstractMessage;

public class RmRequest extends AbstractMessage
{
    @Getter
    @Setter
    private String targetPath;
}
