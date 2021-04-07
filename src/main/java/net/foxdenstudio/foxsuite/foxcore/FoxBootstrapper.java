package net.foxdenstudio.foxsuite.foxcore;

import com.google.inject.Injector;
import com.google.inject.Module;

import javax.inject.Inject;
import java.util.*;

public class FoxBootstrapper {

    @Inject
    Injector injector;

    Set<Binding> bindings = new HashSet<>();

    Injector sharedInjector;

    public <T> void registerPluginModules(Plugin<T> plugin, Class<T> injectTarget, Module shared, Module... local) {
        this.registerPluginModules(plugin, injectTarget, shared, Arrays.asList(local));
    }

    public <T> void registerPluginModules(Plugin<T> plugin, Class<T> injectTarget, Module shared, Collection<Module> local) {
        this.bindings.add(new Binding(plugin, injectTarget, shared, local));
    }

    public Injector createSharedInjector() {
        List<Module> modules = new LinkedList<>();
        bindings.stream().map(binding -> binding.shared).forEach(modules::add);
        return sharedInjector = injector.createChildInjector(modules);
    }

    public Injector getSharedInjector() {
        return this.sharedInjector;
    }

    public FoxCore bootstrap() {
        Injector shared = this.createSharedInjector();
        FoxCore foxCore = shared.getInstance(FoxCore.class);
        for (Binding<?> binding : bindings) {
            binding.injector = shared.createChildInjector(binding.local);
            ((Binding) binding).instance = binding.injector.getInstance(binding.injectTarget);
        }
        return foxCore;
    }

    public void init() {
        for (Binding<?> binding : this.bindings) {
            ((Binding) binding).plugin.init(binding.instance);
        }
    }

    private static class Binding<T> {
        Plugin<T> plugin;
        Class<T> injectTarget;
        Module shared;
        Collection<? extends Module> local;

        transient Injector injector;
        transient T instance;

        public Binding(Plugin<T> plugin, Class<T> injectTarget, Module shared, Collection<Module> local) {
            this.plugin = plugin;
            this.injectTarget = injectTarget;
            this.shared = shared;
            this.local = local;
        }
    }

    public interface Plugin<T> {

        void configure(FoxBootstrapper bootstrapper);

        void init(T injectedPluginInstance);

    }
}
