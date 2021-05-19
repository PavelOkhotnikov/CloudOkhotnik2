package okhotnik.gbcloud.common.messages.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhotnik.gbcloud.common.messages.AbstractMessage;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthMessage extends AbstractMessage
{
    private String login;

    private String passwordHash;
}
