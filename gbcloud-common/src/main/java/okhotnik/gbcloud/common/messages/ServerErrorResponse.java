package okhotnik.gbcloud.common.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ServerErrorResponse extends AbstractMessage
{
    @Getter
    @Setter
    private String reason;
}
