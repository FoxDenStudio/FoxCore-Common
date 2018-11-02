package net.foxdenstudio.foxcore.impl.command.result;

import net.foxdenstudio.foxcore.api.command.result.CommandResult;
import net.foxdenstudio.foxcore.api.command.result.ResultFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResultFactoryImpl implements ResultFactory {

    @Inject
    private ResultFactoryImpl(){}

    @Override
    public CommandResult counts(int successes, int failures) {
        return new CommandResultImpl(successes, failures);
    }

    @Override
    public CommandResult success() {
        return new CommandResultImpl(1, 0);
    }

    @Override
    public CommandResult empty() {
        return new CommandResultImpl(0, 0);
    }

    @Override
    public CommandResult failure() {
        return new CommandResultImpl(0, 1);
    }
}
