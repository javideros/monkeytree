package de.ros.monkeytree.subcomands;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ros.monkeytree.service.IFileService;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Class handling the subcommand compare-trees.
 */
@Component
@Log4j2
@Command(name = "compare-trees", description = "Compare to folders structure", exitCodeOnExecutionException = 356)
public class TreeCompareTrees implements Callable<Integer> {

    @Autowired
    private IFileService fileService;

    @Option(names = { "-s", "--folder1" }, required = true, description = "Source folder to compare")
    private String sourceFolder;

    @Option(names = { "-t", "--folder2" }, required = true, description = "Target folder to compare")
    private String targetFolder;

    /**
     *
     */
    @Override
    public Integer call() throws Exception {
        if (null == sourceFolder || sourceFolder.isEmpty()) {
            sourceFolder = ".";
        }

        if (!sourceFolder.isEmpty() && (null == targetFolder || targetFolder.isEmpty())) {
            targetFolder = ".";
        }

        fileService.compareFoldersTree(sourceFolder, targetFolder);
        log.debug("Command show was called");
        return 0;

    }

}