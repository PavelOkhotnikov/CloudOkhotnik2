package okhotnik.gbcloud.common.filesystem;

import java.io.IOException;

public interface IFileReader extends IFile
{
    byte[] read(int bytesToRead) throws IOException;
}
