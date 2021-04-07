package net.foxdenstudio.foxsuite.foxcore.api.attribute;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.value.BaseAttrValue;
import net.foxdenstudio.foxsuite.foxcore.api.text.FoxTextRepresentable;
import net.foxdenstudio.foxsuite.foxcore.platform.fox.text.TextFactory;
import net.foxdenstudio.foxsuite.foxcore.platform.text.Text;
import net.foxdenstudio.foxsuite.foxcore.platform.text.format.TextColors;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;

public abstract class BaseAttribute<V extends BaseAttrValue<?, ? extends BaseAttribute<V>>>
        implements FoxAttribute<V> , FoxTextRepresentable {

    private final Provider<V> attrValueProvider;
    private final String serializedName;
    private final InheritanceMode inheritanceMode;

    @Inject
    protected TextFactory tf;

    @Inject
    protected TextColors tc;

    protected BaseAttribute(@Nonnull Provider<V> attrValueProvider,
                            @Nonnull String serializedName,
                            @Nonnull InheritanceMode inheritanceMode) {
        this.attrValueProvider = attrValueProvider;
        this.serializedName = serializedName;
        this.inheritanceMode = inheritanceMode;
    }

    @Override
    public @Nonnull
    Provider<V> getValueProvider() {
        return this.attrValueProvider;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.serializedName;
    }

    @Nonnull
    @Override
    public InheritanceMode getInheritanceMode() {
        return this.inheritanceMode;
    }

    @Nonnull
    @Override
    public String getDisplayName() {
        return this.getSerializedName();
    }

    @Override
    public Text toText() {
        return tf.of(tc.GREEN, this.getDisplayName());
    }

    @Override
    public String toString() {
        return "BaseAttribute{name=" + this.getSerializedName() + ", mode=" + this.getInheritanceMode() + "}";
    }
}
