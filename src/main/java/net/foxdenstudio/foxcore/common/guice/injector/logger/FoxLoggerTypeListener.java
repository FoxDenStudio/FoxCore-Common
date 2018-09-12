package net.foxdenstudio.foxcore.common.guice.injector.logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import net.foxdenstudio.foxcore.common.annotation.guice.InjectFoxLogger;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class FoxLoggerTypeListener implements TypeListener {
    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        Class<?> clazz = type.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class &&
                        field.isAnnotationPresent(InjectFoxLogger.class)) {
                    encounter.register(new FoxLoggerMembersInjector<>(field, clazz));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
