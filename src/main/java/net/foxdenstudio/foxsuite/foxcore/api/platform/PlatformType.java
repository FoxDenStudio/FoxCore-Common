package net.foxdenstudio.foxsuite.foxcore.api.platform;

public final class PlatformType {

    private final String name;
    private final String id;

    public PlatformType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }
}
