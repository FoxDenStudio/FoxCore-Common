package net.foxdenstudio.foxsuite.foxcore.standalone.fox.text;

import net.foxdenstudio.foxsuite.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColors;
import net.foxdenstudio.foxsuite.foxcore.standalone.text.SimpleText;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class SimpleTextFactory implements TextFactory {

    private final TextColors textColors;
    private final Provider<SimpleTextBuilder> builderProvider;

    @Inject
    private SimpleTextFactory(TextColors textColors, Provider<SimpleTextBuilder> builderProvider) {
        this.textColors = textColors;
        this.builderProvider = builderProvider;
    }

    @Override
    public Text of(String text) {
        return SimpleText.of(text);
    }

    @Override
    public Text of(Object... objects) {
        return SimpleText.of(objects);
    }

    @Override
    public Text.Builder builder() {
        return builderProvider.get();
    }

}
