package net.foxdenstudio.foxsuite.foxcore.api.storage;

import com.google.gson.GsonBuilder;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxStorageManager {

    private final FoxPathFactory pathFactory;

    @Inject
    private FoxStorageManager(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
    }


    public GsonBuilder getBaseGsonConfig() {
        GsonBuilder builder = new GsonBuilder();

        builder.setPrettyPrinting();
        builder.registerTypeAdapter(StandardPathComponent.class, StandardPathComponent.Adapter.INSTNACE);
        builder.registerTypeAdapterFactory(this.pathFactory.getTypeAdapterFactory());

        return builder;
    }

}
