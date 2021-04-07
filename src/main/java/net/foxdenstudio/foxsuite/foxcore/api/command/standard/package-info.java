/**
 * The command classes in this package fit the Minecraft sense for what a "normal" command looks like.
 * That is, a command that is simply run from the command line as either a player or some other command source.
 *
 * The reason these commands are especially noteworthy to distinguish is because these commands,
 * while not the only commands in this hierarchy, are the only ones that implement the platform command API,
 * which would effectively be the Sponge command API.
 *
 * While compliance with an API like the Sponge command API is not strictly necessary for operation,
 * it is more "correct" especially for plugins that might want to snoop the command structure.
 */
package net.foxdenstudio.foxsuite.foxcore.api.command.standard;