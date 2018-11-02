package net.foxdenstudio.foxcore.api.util;

import javax.annotation.Nonnull;

public interface AliasGroup {

    @Nonnull String[] getAliases();

    boolean isAlias(String alias);

    @Nonnull String getMainAlias();

    @Nonnull String getShortAlias();

}
