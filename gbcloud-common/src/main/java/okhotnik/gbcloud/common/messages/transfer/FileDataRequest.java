package okhotnik.gbcloud.common.messages.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.AbstractMessage;

@NoArgsConstructor
@AllArgsConstructor
public class FileDataRequest extends AbstractMessage
{
    @Getter
    @Setter
    private byte[] data;

    @Getter
    @Setter
    private boolean last;

    @Getter
    @Setter
    private int cseq;

    @Getter
    @Setter
    private int percentComplete;
}
