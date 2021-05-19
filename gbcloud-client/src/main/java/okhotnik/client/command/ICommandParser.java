package okhotnik.client.command;


import org.jetbrains.annotations.Nullable;

public interface ICommandParser
{
    @Nullable
    ICommand parse(@Nullable String command);
}
