package net.foxdenstudio.foxsuite.foxcore.standalone.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColor;
import net.foxdenstudio.foxsuite.foxcore.standalone.text.SimpleTextColor;

import static org.jline.utils.AttributedStyle.*;

public class StandaloneTextStyleModule extends AbstractModule {
    protected void configure() {
        //add configuration logic here

        bind(TextColor.class).annotatedWith(Names.named("black")).toInstance(new SimpleTextColor("black", "Black", BLACK));
        bind(TextColor.class).annotatedWith(Names.named("dark_blue")).toInstance(new SimpleTextColor("dark_blue", "Dark Blue", BLUE));
        bind(TextColor.class).annotatedWith(Names.named("dark_green")).toInstance(new SimpleTextColor("dark_green", "Dark Green", GREEN));
        bind(TextColor.class).annotatedWith(Names.named("dark_aqua")).toInstance(new SimpleTextColor("dark_aqua", "Dark Aqua", CYAN));
        bind(TextColor.class).annotatedWith(Names.named("dark_red")).toInstance(new SimpleTextColor("dark_red", "Dark Red", RED));
        bind(TextColor.class).annotatedWith(Names.named("dark_purple")).toInstance(new SimpleTextColor("dark_purple", "Dark Purple", MAGENTA));
        bind(TextColor.class).annotatedWith(Names.named("gold")).toInstance(new SimpleTextColor("gold", "Gold", YELLOW));
        bind(TextColor.class).annotatedWith(Names.named("gray")).toInstance(new SimpleTextColor("gray", "Gray", WHITE));
        bind(TextColor.class).annotatedWith(Names.named("dark_gray")).toInstance(new SimpleTextColor("dark_gray", "Dark Gray", BLACK | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("blue")).toInstance(new SimpleTextColor("blue", "Blue", BLUE | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("green")).toInstance(new SimpleTextColor("green", "Green", GREEN | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("aqua")).toInstance(new SimpleTextColor("aqua", "Aqua", CYAN | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("red")).toInstance(new SimpleTextColor("red", "Red", RED | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("light_purple")).toInstance(new SimpleTextColor("light_purple", "Light Purple", MAGENTA | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("yellow")).toInstance(new SimpleTextColor("yellow", "Yellow", YELLOW | BRIGHT));
        bind(TextColor.class).annotatedWith(Names.named("white")).toInstance(new SimpleTextColor("white", "White", WHITE | BRIGHT));

        bind(TextColor.class).annotatedWith(Names.named("reset")).toInstance(new SimpleTextColor("reset", "Reset", true));
        bind(TextColor.class).annotatedWith(Names.named("none")).toInstance(new SimpleTextColor("none", "None", false));
    }
}
