package net.foxdenstudio.foxcore.standalone;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.foxdenstudio.foxcore.FoxCore;
import net.foxdenstudio.foxcore.standalone.guice.module.FoxCoreStandaloneModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoxStart {

    private final FoxCore foxCore;

    @Inject
    public FoxStart(FoxCore foxCore) {
        this.foxCore = foxCore;
    }

    public void start(String[] args) {
        foxCore.awoo();
        foxCore.registerCommands();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) break;
            foxCore.getCommandManager().process(foxCore.getConsoleSource(), line);
        }
    }

    public static void main(String[] args) {
        List<Module> modules = new ArrayList<>();
        modules.add(new FoxCoreStandaloneModule());
        Injector injector = Guice.createInjector(modules);

        FoxStart foxStart = injector.getInstance(FoxStart.class);
        foxStart.start(args);
    }


}
