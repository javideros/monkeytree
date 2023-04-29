package de.ros.monkeytree.subcomands;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Class handling the subcommand compare-tree-to-file.
 */
@Component
@Log4j2
@Command(name = "compare-tree-to-file", description = "Compare file with tree structure to a folder structure", exitCodeOnExecutionException = 356)
public class TreeCompareTreeToFile implements Callable<Integer> {

    @Option(names = { "-f", "--file" }, description = "File to compare", required = true)
    private String file;

    @Option(names = { "-t", "--tree" }, description = "Tree to compare", required = true)
    private String tree;

    /**
     * Handling the subcommand compare-tree-to-file.
     */
    @Override
    public Integer call() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'call'");
    }

}
