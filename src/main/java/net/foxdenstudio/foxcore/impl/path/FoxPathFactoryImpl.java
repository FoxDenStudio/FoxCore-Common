package net.foxdenstudio.foxcore.impl.path;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.annotation.guice.FoxLogger;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.FoxPathComponent;
import net.foxdenstudio.foxcore.api.path.component.IndexPathComponent;
import net.foxdenstudio.foxcore.api.path.component.LinkPathComponent;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoxPathFactoryImpl implements FoxPathFactory {

    @FoxLogger
    Logger logger;

    @SuppressWarnings("ConstantConditions")
    @Override
    public FoxPath from(@Nonnull FoxPathComponent first, FoxPathComponent... next) {
        boolean added = false;
        ImmutableList.Builder<FoxPathComponent> builder = ImmutableList.builder();
        if (first != null) {
            builder.add(first);
            added = true;
        }
        if (next != null) {
            for (FoxPathComponent comp : next) {
                if (comp != null) {
                    builder.add(comp);
                    added = true;
                }
            }
        }
        Preconditions.checkArgument(added, "Must supply at least one non-null element!");
        return new FoxPathExtImpl(builder.build());
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
            throw new FoxCommandException("Input must contain at least one element!");

        IndexPathComponent indexPathComponent = null;
        if (indexPathStr != null && !indexPathStr.isEmpty()) {
            String[] indexParts = indexPathStr.split("/+");
            if (indexParts.length > 0) {
                int indexIndex = 0;
                if (indexParts[0].isEmpty()) indexIndex = 1;
                String indexStr = null;
                if (indexIndex < indexParts.length) {
                    indexStr = indexParts[indexIndex];
                    List<String> namespaceParts = new ArrayList<>(Arrays.asList(indexParts).subList(indexIndex + 1, parts.length));
                    StandardPathComponent namespace = null;
                    if (!namespaceParts.isEmpty()) namespace = StandardPathComponent.from(namespaceParts);
                    indexPathComponent = new IndexPathComponent(indexStr, namespace);
                }
            }
        }

        StandardPathComponent objectPathComponent = null;
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
                objectPathComponent = StandardPathComponent.from(objectParts);
                mode = FoxPathExt.Mode.DEFAULT;
                if (objectPathStr.startsWith("/")) mode = FoxPathExt.Mode.ABSOLUTE;
                else if (objectPathStr.startsWith(".")) mode = FoxPathExt.Mode.RELATIVE;
            }
        }

        LinkPathComponent linkPathComponent = null;
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
            if(!links.isEmpty()) linkPathComponent = LinkPathComponent.of(links);
        }

        if(indexPathComponent == null && objectPathComponent == null && linkPathComponent == null)
            throw new FoxCommandException("Input must contain at least one element!");

        if(mode == null) mode = FoxPathExt.Mode.DEFAULT;

        FoxPathExtImpl.Builder builder = new FoxPathExtImpl.Builder();
        builder
                .indexComponent(indexPathComponent)
                .objectComponent(objectPathComponent)
                .linkComponent(linkPathComponent)
                .mode(mode)
                .parentOffset(parentOffset);

        return builder.build();
    }
}
