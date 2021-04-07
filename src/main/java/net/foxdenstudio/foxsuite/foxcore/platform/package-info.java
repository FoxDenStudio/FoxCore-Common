/**
 * This package contains interfaces that represent elements of the underlying platform.
 *
 * As it happens, I really like SpongeAPI and prefer working with it,
 * so these interfaces are just partial shadow clone of api classes and interfaces in SpongeAPI.
 *
 * These interfaces only contain a subset of the methods in their real SpongeAPI counterparts,
 * and are intended to be facades for real SpongeAPI instances.
 *
 * This is done by simply mixing-in the interfaces at runtime,
 * which allows free casting and eliminates the overhead of having a bunch of wrapper classes.
 *
 * Because I want to keep the mixins dead simple, I'm keeping all method signatures the same wherever I can
 * in order to keep mixins as bare as possible.
 *
 * Sometimes this won't be completely possible to do elegantly,
 * especially when method signatures of platform code accept platform type parameters,
 * or when plugin code needs to implement a platform interface that returns a platform type.
 *
 * In both these cases, they can be solved with wrappers or mixins with proxy methods,
 * with the latter being obviously preferable.
 */
package net.foxdenstudio.foxsuite.foxcore.platform;