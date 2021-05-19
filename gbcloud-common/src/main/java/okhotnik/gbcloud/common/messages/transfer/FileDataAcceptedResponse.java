package okhotnik.gbcloud.common.messages.transfer;

import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.AbstractMessage;

public class FileDataAcceptedResponse extends AbstractMessage
{
    @Getter
    @Setter
    private int cseq;
}
