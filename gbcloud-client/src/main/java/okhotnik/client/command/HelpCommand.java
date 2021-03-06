package okhotnik.client.command;

import okhotnik.gbcloud.common.utils.Util;

import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Keyword(Const.HELP_COMMAND_KEYWORD)
@Description(Const.HELP_COMMAND_DESCRIPTION)
public class HelpCommand extends AbstractCommand
{
    private static final AnnotationLiteral<Command> COMMAND_ANNOTATION = new AnnotationLiteral<Command>() {};

    @Override
    public void run()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Supported commands are: ");
        builder.append(System.getProperty("line.separator"));

        final List<Object> supportedCommands = CDI.current().select(COMMAND_ANNOTATION).stream().collect(toList());
        supportedCommands.stream().sorted((thisCommandBean, otherCommandBean) ->
        {
            final Util.AnnotatedClass thisAnnotatedWithImportance = Util.getAnnotation(Importance.class, thisCommandBean.getClass());
            final Util.AnnotatedClass otherAnnotatedWithImportance = Util.getAnnotation(Importance.class, otherCommandBean.getClass());

            final int thisImportance = thisAnnotatedWithImportance == null ? 0 : ((Importance) thisAnnotatedWithImportance.getAnnotation()).value();
            final int otherImportance = otherAnnotatedWithImportance == null ? 0 : ((Importance) otherAnnotatedWithImportance.getAnnotation()).value();

            return otherImportance - thisImportance;
        }).forEach(commandBean ->
        {
            final Util.AnnotatedClass annotatedWithKeyword = Util.getAnnotation(Keyword.class, commandBean.getClass());
            final Util.AnnotatedClass annotatedWithDescription = Util.getAnnotation(Description.class, commandBean.getClass());
            final Util.AnnotatedClass annotatedWithArguments = Util.getAnnotation(Arguments.class, commandBean.getClass());

            if (annotatedWithKeyword == null) return;
            final Keyword keywordAnnotation = (Keyword) annotatedWithKeyword.getAnnotation();
            builder.append(keywordAnnotation.value());
            builder.append(" ");
            if (annotatedWithArguments != null)
            {
                final Arguments argumentsAnnotation = (Arguments) annotatedWithArguments.getAnnotation();
                for (final String argumentName : argumentsAnnotation.value())
                {
                    builder.append("<");
                    builder.append(argumentName);
                    builder.append("> ");
                }
            }

            if (annotatedWithDescription != null)
            {
                final Description descriptionAnnotation = (Description) annotatedWithDescription.getAnnotation();
                builder.append("- ");
                builder.append(descriptionAnnotation.value());
            }

            builder.append(System.getProperty("line.separator"));
        });

        System.out.println(builder.toString());
    }
}
