package okhotnik.client.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import okhotnik.client.config.ClientConfig;
import okhotnik.gbcloud.common.filesystem.PathDoesNotExistException;
import okhotnik.gbcloud.common.filesystem.PathIsNotADirectoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@ApplicationScoped
public class CLI
{
    private static final String UNRECOGNIZED_COMMAND_MESSAGE = "Unrecognized command";

    private static class EConsoleInputRequired {}

    @AllArgsConstructor
    private static class EConsoleInputReceived
    {
        @Getter
        @Setter
        private String line;
    }

    @Inject
    private Event<EConsoleInputRequired> consoleInputRequired;

    @Inject
    private Event<EConsoleInputReceived> consoleInputReceived;

    @Inject
    private ICommandParser parser;

    @Setter
    @Getter
    private boolean active = true;

    @Getter
    private Path currentDirectory;

    @Inject
    private ClientConfig config;

    @Setter
    private String remoteServer;

    @Setter
    private String remoteDirectory;

    private synchronized String getPrompt()
    {
        return "(" + remoteServer + ") [l: " + currentDirectory.getName(currentDirectory.getNameCount() - 1) +
                "|r: " + remoteDirectory + "] # ";
    }

    public synchronized void resetPrompt()
    {
        remoteServer = "not connected";
        remoteDirectory = "";
    }

    public synchronized void updatePrompt()
    {
        System.out.println();
        System.out.print(getPrompt());
    }

    private void handleConsoleInputRequired(@Observes final EConsoleInputRequired event)
    {
        updatePrompt();
        final Scanner scanner = new Scanner(System.in);
        consoleInputReceived.fire(new EConsoleInputReceived(scanner.nextLine()));
    }

    private void handleConsoleInputReceived(@Observes final EConsoleInputReceived event)
    {
        if (event.getLine() != null && event.getLine().length() != 0)
        {
            final ICommand command = parser.parse(event.getLine());
            try
            {
                if (command != null)
                {
                    command.run();
                }
                else
                {
                    System.out.println(UNRECOGNIZED_COMMAND_MESSAGE);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (active)
        {
            consoleInputRequired.fire(new EConsoleInputRequired());
        }
    }

    public void start()
    {
        resetPrompt();
        currentDirectory = Paths.get(config.getLocalStorage()).toAbsolutePath().normalize();
        consoleInputRequired.fire(new EConsoleInputRequired());
    }

    public synchronized String pwd()
    {
        return currentDirectory.toString();
    }

    public synchronized void cd(final @NotNull String path) throws PathDoesNotExistException, PathIsNotADirectoryException
    {
        if (path.length() == 0) return;
        Path newPath = Paths.get(path).normalize();
        if (!newPath.isAbsolute()) newPath = currentDirectory.resolve(newPath);
        if (!Files.exists(newPath)) throw new PathDoesNotExistException();
        if (!Files.isDirectory(newPath)) throw new PathIsNotADirectoryException();
        currentDirectory = newPath.normalize();
        //updatePrompt();
    }
}
