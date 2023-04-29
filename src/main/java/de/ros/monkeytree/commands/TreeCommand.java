package de.ros.monkeytree.commands;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ros.monkeytree.subcomands.TreeToFile;
import lombok.extern.log4j.Log4j2;
import de.ros.monkeytree.service.IFileService;
import de.ros.monkeytree.subcomands.TreeCompareTreeFiles;
import de.ros.monkeytree.subcomands.TreeCompareTreeToFile;
import de.ros.monkeytree.subcomands.TreeCompareTrees;
import de.ros.monkeytree.subcomands.TreeFromFile;
import de.ros.monkeytree.subcomands.TreeShow;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Main Command tree.
 */
@Component
@Log4j2
@Command(name = "tree", mixinStandardHelpOptions = true, subcommands = {
        TreeShow.class, TreeToFile.class, TreeFromFile.class,
        TreeCompareTreeFiles.class, TreeCompareTrees.class,
        TreeCompareTreeToFile.class }, exitCodeOnExecutionException = 256)
public class TreeCommand implements Callable<Integer> {

    @Option(names = "-f", description = "Root folder to show")
    private String rootFolder;

    @Autowired
    private IFileService fileService;

    /**
     * Main method. Contains logic that scans current or indicated folder and shows
     * the structure.
     *
     * @return 0 if everything is fine.
     */
    @Override
    public Integer call() throws Exception {
        if (null == rootFolder || rootFolder.isEmpty()) {
            rootFolder = ".";
        }
        fileService.showSubfoldersList(rootFolder);
        log.debug("Command tree was called");
        return 0;
    }
}
