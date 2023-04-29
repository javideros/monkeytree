package de.ros.monkeytree.commands;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

@Component
public class Runner implements CommandLineRunner, ExitCodeGenerator {

    // auto-configured to inject PicocliSpringFactory
    private final IFactory factory;

    private final TreeCommand treeCommand;

    private int exitCode;

    public Runner(IFactory factory, TreeCommand treeCommand) {
        this.factory = factory;
        this.treeCommand = treeCommand;
    }

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(treeCommand, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
