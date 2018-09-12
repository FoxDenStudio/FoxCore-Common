package net.foxdenstudio.foxcore.common.guice.injector.logger;

import com.google.inject.MembersInjector;
import net.foxdenstudio.foxcore.common.annotation.guice.InjectFoxLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class FoxLoggerMembersInjector<T> implements MembersInjector<T> {
    private final Field field;
    private final Logger logger;

    FoxLoggerMembersInjector(Field field, Class<?> clazz) {
        this.field = field;
        String loggername = "fox";
        InjectFoxLogger annotation = field.getAnnotation(InjectFoxLogger.class);
        String value = annotation.value();
        if(!value.isEmpty()){
            loggername += "." + value;
        } else {
            String className = clazz.getCanonicalName();
            if (className == null) className = "unknown";
            loggername += ".class." + className;
        }
        this.logger = LoggerFactory.getLogger(loggername);
        field.setAccessible(true);
    }

    public void injectMembers(T t) {
        try {
            field.set(t, logger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
