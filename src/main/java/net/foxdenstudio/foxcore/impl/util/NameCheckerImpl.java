package net.foxdenstudio.foxcore.impl.util;

import net.foxdenstudio.foxcore.api.util.NameChecker;
import net.foxdenstudio.foxcore.api.util.StaticUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NameCheckerImpl implements NameChecker {

    // private static final String[] ILLEGAL_NAMES = {};

    // List of file names that are not valid for Windows.
    // These are blacklisted to prevent issues for file-based storage.
    private static final String[] FS_ILLEGAL_NAMES = {"con", "prn", "aux", "nul",
            "com0", "com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9",
            "lpt0", "lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9"};

    @Inject
    private NameCheckerImpl(){}

    @Override
    public boolean isClean(String name) {
        if(!name.matches("[a-zA-Z$_][\\w\\-$]*")) return false;

        if(StaticUtils.arrayContainsIgnoreCase(FS_ILLEGAL_NAMES, name)) return false;

        return true;
    }

}
