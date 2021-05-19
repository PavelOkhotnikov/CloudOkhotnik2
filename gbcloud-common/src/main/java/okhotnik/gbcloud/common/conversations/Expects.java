package okhotnik.gbcloud.common.conversations;

import okhotnik.gbcloud.common.messages.IMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Expects
{
    Class<? extends IMessage>[] value();
}
