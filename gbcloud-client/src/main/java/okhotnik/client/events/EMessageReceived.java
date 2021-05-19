package okhotnik.client.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.IMessage;

@Getter
@Setter
@AllArgsConstructor
public class EMessageReceived
{
    private IMessage message;
}
