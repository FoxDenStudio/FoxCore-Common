package net.foxdenstudio.foxsuite.foxcore.junit;

import com.google.inject.Module;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class GuiceRule implements MethodRule {

    public GuiceRule(Module... modules){

    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return null;
    }
}
