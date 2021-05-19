package okhotnik.gbcloud.common.messages.mkdir;

import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.AbstractMessage;

public class MkdirRequest extends AbstractMessage
{
    @Getter
    @Setter
    private String directoryPath;
}
