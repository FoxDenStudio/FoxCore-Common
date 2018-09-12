package net.foxdenstudio.foxcore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FoxPlugin {

    String id();

    String name() default "";

    String version() default "";

    FoxDependency[] dependencies() default {};

    String description() default "";

    String url() default "";

    String[] authors() default {};
}
