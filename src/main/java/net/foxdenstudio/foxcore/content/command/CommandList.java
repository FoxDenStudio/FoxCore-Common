package net.foxdenstudio.foxcore.content.command;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.standard.FoxStandardCommandBase;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
import net.foxdenstudio.foxcore.content.object.StubObject;
import net.foxdenstudio.foxcore.platform.command.source.CommandSource;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;

public class CommandList extends FoxStandardCommandBase {

    private final Provider<StubObject> stubObjectProvider;
    private final FoxObjectPathFactory objectPathFactory;
    private final FoxMainIndex mainIndex;

    private int id = 1;

    @Inject
    private CommandList(Provider<StubObject> stubObjectProvider, FoxObjectPathFactory objectPathFactory, FoxMainIndex mainIndex){
        this.stubObjectProvider = stubObjectProvider;
        this.objectPathFactory = objectPathFactory;
        this.mainIndex = mainIndex;
    }

    @Override
    public FoxCommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws FoxCommandException {
        StubObject object = this.stubObjectProvider.get();
        String objectName = "awoo-stub-" + (id++);
        FoxObjectPath newPath = this.objectPathFactory.getPath(objectName);
        FoxObjectIndex objectIndex = mainIndex.getDefaultObjectIndex();
        if(objectIndex instanceof MemoryIndex){
            MemoryIndex memoryIndex = (MemoryIndex) objectIndex;
            memoryIndex.addObject(object, newPath);
        } else {
            source.sendMessage(this.textFactory.getText("wtf!?"));
        }

        Collection<FoxObjectPath> allPaths = objectIndex.getAllObjectPaths();
        StringBuilder builder = new StringBuilder("The following objects exist:");
        for(FoxObjectPath path : allPaths){
            builder.append('\n').append(path.toString());
        }
        source.sendMessage(this.textFactory.getText(builder.toString()));

        //source.sendMessage(this.textFactory.getText("Here's a list: yip, bark, awoo!"));
        return this.resultFactory.empty();
    }

}
