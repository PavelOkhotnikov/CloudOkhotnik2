package okhotnik.gbcloud.common.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class EFilesystemEvent
{
    private String localAbsolutePath;

    private String localRelativePath;

    private String localRoot;
}
