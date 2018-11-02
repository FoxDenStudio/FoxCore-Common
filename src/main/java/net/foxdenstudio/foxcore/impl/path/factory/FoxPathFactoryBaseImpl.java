package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.FoxExceptionFactory;
import net.foxdenstudio.foxcore.api.util.NameChecker;

import javax.inject.Inject;

public abstract class FoxPathFactoryBaseImpl {

    @Inject
    protected NameChecker nameChecker;

    @Inject
    protected FoxExceptionFactory exceptionFactory;
}
