package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.annotation.Nonnull;
import javax.inject.Provider;


/**
 * Interface for singleton attribute classes that are used to define properties.
 * Implementors should be Guice injectable singletons for consistency.
 * <p>
 * Splish splash smash cronch, dive zoom rudder-butt.
 *
 * @param <V> The corresponding AttrValue class.
 */
public interface FoxAttribute<V extends FoxAttrValue<?, ? extends FoxAttribute<V>>> {

    @Nonnull
    Provider<V> getValueProvider();

    @Nonnull
    String getSerializedName();

    @Nonnull
    InheritanceMode getInheritanceMode();

//    @Nonnull
//    DataMode getDataMode();

    @Nonnull
    String getDisplayName();

    /**
     * The inheritance mode of the attribute. See elements for behaviour.
     */
    enum InheritanceMode {
        /**
         * Lookup inheritance.
         * <p>
         * If included in object schema, on construction:
         * Looks for value in parents, and initializes default value if none are found.
         * <p>
         * When queried:
         * If a value is not found, look for a value from a parent and return the value.
         * <p>
         * Effectively, unless an object has its own value set for the attribute, it will always defer to a parent.
         * Attributes that behave this way allow for archetype level shared configuration.
         * <p>
         * An example attribute that could benefit from this is display name color,
         * which would let you color entire archetypes but override the setting for individual objects at will.
         */
        INHERIT,

        /**
         * Provision inheritance.
         * <p>
         * If included in object schema, on construction:
         * Looks for value in parents, and initializes default value if none are found.
         * <p>
         * When queried:
         * If a value is not found, look for a value from a parent.
         * If a value is found in a parent, it is written to extra attributes and the value is returned.
         * Otherwise, nothing is returned.
         * <p>
         * Effectively, the parent lookup only happens once, unless the extra attribute is deleted and re-queried.
         * <p>
         * This mode should be used carefully. It may be useful as a way of setting defaults for new objects.
         */
        PROVISION,

        /**
         * Eager provisioning inheritance.
         *
         * Does everything that {@link InheritanceMode#PROVISION} mode does, but additionally newly constructed objects
         * will immediately attempt to provision a value from a parent if it exists.
         */
        EAGER_PROVISION,

        /**
         * No inheritance.
         * <p>
         * If included in object schema, on construction:
         * Initializes default value.
         * <p>
         * If a value is not found for an attribute, no further search is necessary.
         */
        NONE;
    }

    enum DataMode {
        /**
         * Most common attribute data mode. Represents a field that can be changed.
         */
        VARIABLE,

        /**
         * Represents a constant value that cannot be changed.
         */
        VALUE,

        /**
         * Represents some data that is computed by a function,
         * and while it cannot be set, it can change based on other data.
         */
        DERIVED,

        /**
         * Represents a function similar to {@link #DERIVED}, but it can also accept an assignment.
         */
        FUNCTION;
    }
}
