package net.foxdenstudio.foxcore.impl.command.result;

import net.foxdenstudio.foxcore.api.command.result.FoxCommandResult;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResultFactoryImpl implements ResultFactory {

    @Inject
    private ResultFactoryImpl(){}

    @Override
    public FoxCommandResult counts(int successes, int failures) {
        return new FoxCommandResultImpl(successes, failures);
    }

    @Override
    public FoxCommandResult success() {
        return new FoxCommandResultImpl(1, 0);
    }

    @Override
    public FoxCommandResult empty() {
        return new FoxCommandResultImpl(0, 0);
    }

    @Override
    public FoxCommandResult failure() {
        return new FoxCommandResultImpl(0, 1);
    }
}
