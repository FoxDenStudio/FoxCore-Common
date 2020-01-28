package net.foxdenstudio.foxcore.impl.path;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.section.FoxPathSection;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class FoxPathFactoryImpl implements FoxPathFactory {

    private final Provider<FoxPathExtImpl.Builder> builderProvider;

    private FoxPath empty = null;

    @FoxLogger
    private Logger logger;

    @Inject
    private FoxPathFactoryImpl(Provider<FoxPathExtImpl.Builder> builderProvider) {
        this.builderProvider = builderProvider;
    }

    @Override
    public FoxPath empty() {
        if(this.empty == null){
            this.empty = builderProvider.get().build();
        }
        return this.empty;
    }

    @Override
    public FoxPath from(@Nonnull FoxPathSection first, FoxPathSection... next) {
        Preconditions.checkNotNull(first);
        FoxPathExtImpl.Builder builder = builderProvider.get();
        builder.addSection(first);
        if (next != null) {
            for (FoxPathSection comp : next) {
                if (comp != null) {
                    builder.addSection(comp);
                }
            }
        }

        return builder.build();
    }

    @Override
    public FoxPath from(String input) {
        try {
            return fromChecked(input);
        } catch (FoxCommandException e) {
            logger.warn("Could not parse input \"" + input + "\". Code needs fixing.", e);
            return null;
        }
    }

    @Override
    @Nonnull
    public FoxPath fromChecked(String input) throws FoxCommandException {
        if (input.isEmpty()) throw new FoxCommandException("Input cannot be empty!");
        String[] parts = input.split(":");
        if (parts.length < 1) throw new FoxCommandException("Input must contain at least one element!");
        String indexPathStr = null;
        String objectPathStr = null;
        List<String> linkPathStrs = new ArrayList<>();
        int objectIndex = 0;
        if (parts[0].startsWith("@")) {
            objectIndex = 1;
            indexPathStr = parts[0].substring(1);
        }
        if (parts.length > objectIndex) {
            objectPathStr = parts[objectIndex];
        }
        for (int i = objectIndex + 1; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) linkPathStrs.add(part);
        }

        if ((indexPathStr == null || indexPathStr.isEmpty()) && (objectPathStr == null || objectPathStr.isEmpty()) && linkPathStrs.isEmpty())
            return this.empty;

        IndexPathSection indexPathSection = null;
        if (indexPathStr != null && !indexPathStr.isEmpty()) {
            String[] indexParts = indexPathStr.split("/+");
            if (indexParts.length > 0) {
                int indexIndex = 0;
                if (indexParts[0].isEmpty()) indexIndex = 1;
                String indexStr = null;
                if (indexIndex < indexParts.length) {
                    indexStr = indexParts[indexIndex];
                    List<String> namespaceParts = new ArrayList<>(Arrays.asList(indexParts).subList(indexIndex + 1, indexParts.length));
                    StandardPathComponent namespace = null;
                    if (!namespaceParts.isEmpty()) namespace = StandardPathComponent.from(namespaceParts);
                    indexPathSection = new IndexPathSection(indexStr, namespace);
                }
            }
        }

        ObjectPathSection objectPathSection = null;
        FoxPathExt.Mode mode = null;
        int parentOffset = 0;
        if (objectPathStr != null && !objectPathStr.isEmpty()) {


            String[] objectStrParts = objectPathStr.split("/+");
            List<String> objectParts = new ArrayList<>();

            for (String part : objectStrParts) {
                if (part.isEmpty() || part.equals(".")) continue;
                else if (part.equals("..")) {
                    if (objectParts.isEmpty()) {
                        parentOffset++;
                    } else {
                        objectParts.remove(objectParts.size() - 1);
                    }
                } else {
                    objectParts.add(part);
                }
            }
            if (!objectParts.isEmpty()) {
                objectPathSection = ObjectPathSection.from(objectParts);
                mode = FoxPathExt.Mode.DEFAULT;
                if (objectPathStr.startsWith("/")) mode = FoxPathExt.Mode.ABSOLUTE;
                else if (objectPathStr.startsWith(".")) mode = FoxPathExt.Mode.RELATIVE;
            }
        }

        LinkPathSection linkPathSection = null;
        if (!linkPathStrs.isEmpty()) {
            if(mode == null) mode = FoxPathExt.Mode.LINK;
            List<StandardPathComponent> links = new ArrayList<>();
            for (String linkPathStr : linkPathStrs){
                String[] linkStrParts = linkPathStr.split("/+");
                List<String> linkParts = new ArrayList<>();
                for (String linkStrPart : linkStrParts){
                    if(linkStrPart.isEmpty()) continue;
                    else linkParts.add(linkStrPart);
                }
                if(!linkParts.isEmpty()) links.add(StandardPathComponent.from(linkParts));
            }
            if(!links.isEmpty()) linkPathSection = LinkPathSection.of(links);
        }

        if(indexPathSection == null && objectPathSection == null && linkPathSection == null)
            return this.empty();

        if(mode == null) mode = FoxPathExt.Mode.DEFAULT;

        FoxPathExtImpl.Builder builder = builderProvider.get();
        builder
                .indexSection(indexPathSection)
                .objectSection(objectPathSection)
                .linkSection(linkPathSection)
                .mode(mode)
                .parentOffset(parentOffset);

        return builder.build();
    }
}
