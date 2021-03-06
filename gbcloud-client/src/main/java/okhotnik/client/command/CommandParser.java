package okhotnik.client.command;

import org.jetbrains.annotations.Nullable;
import okhotnik.gbcloud.common.utils.Util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CommandParser implements ICommandParser
{
    private static final AnnotationLiteral<Command> COMMAND_ANNOTATION = new AnnotationLiteral<Command>() {};

    private final Map<String, Class<? extends ICommand>> commandMap = new HashMap<>();

    @PostConstruct
    public void init()
    {
        CDI.current().select(COMMAND_ANNOTATION).forEach(bean ->
        {
            final Class<? extends ICommand> commandClass = (Class<? extends ICommand>) bean.getClass();
            final Util.AnnotatedClass annotatedClass = Util.getAnnotation(Keyword.class, commandClass);
            if (annotatedClass == null) return;
            commandMap.put(((Keyword) annotatedClass.getAnnotation()).value(), annotatedClass.getAnnotatedClass());
        });
    }

    @Override
    @Nullable
    public ICommand parse(@Nullable String command)
    {
        if (command == null) return null;
        if (command.length() <= 0) return null;
        final String[] words = command.split("\\s");
        if (words.length <= 0) return null;
        final String keyword = words[0];
        if (commandMap.isEmpty()) init();
        if (!commandMap.containsKey(keyword)) return null;

        final ICommand commandObject = CDI.current().select(commandMap.get(keyword), COMMAND_ANNOTATION).get();
        final Util.AnnotatedClass annotatedClass = Util.getAnnotation(Arguments.class, commandObject.getClass());
        if (annotatedClass != null)
        {
            final Arguments argumentsAnnotation = (Arguments) annotatedClass.getAnnotation();
            final String[] argumentNames = argumentsAnnotation.value();
            for (final String argumentName : argumentNames)
                commandObject.getArguments().add(new Argument(argumentName, null));
        }
        commandObject.collectArguments(words);
        return commandObject;
    }
}
