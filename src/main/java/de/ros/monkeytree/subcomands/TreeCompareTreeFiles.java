package de.ros.monkeytree.subcomands;

import java.util.concurrent.Callable;

/**
 * Class handling the subcommand compare-tree-files.
 */
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Log4j2
@Command(name = "compare-tree-files", mixinStandardHelpOptions = true, version = "1.0", description = "Compare to folders structure", exitCodeOnExecutionException = 356)
public class TreeCompareTreeFiles implements Callable<Integer> {

    @Option(names = { "-s", "--file1" }, description = "Source file to compare against target file")
    private String folder1;

    @Option(names = { "-t", "--file2" }, description = "Target file to compare against source file")
    private String folder2;

    /**
     * Handler compare-tree-files.
     */
    @Override
    public Integer call() throws Exception {
        log.info("Compare folder structure");
        return 0;
    }
}
