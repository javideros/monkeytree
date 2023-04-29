package de.ros.monkeytree.subcomands;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ros.monkeytree.service.IFileService;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Subcommand for showing the tree.
 */
@Component
@Log4j2
@Command(name = "show", description = "Prints the tree from the file", exitCodeOnExecutionException = 256)
public class TreeShow implements Callable<Integer> {

    @Option(names = "-f", description = "Folder tree to show")
    private String rootFolder;

    @Autowired
    private IFileService fileService;

    /**
     * Main method. Contains logic that scans current or indicated folder and shows
     * the structure.
     *
     * Requires rootFolder optio. It's the folder which will be used to show.
     * 
     * @return 0 if everything is fine.
     */
    @Override
    public Integer call() throws Exception {
        if (null == rootFolder || rootFolder.isEmpty()) {
            rootFolder = ".";
        }
        fileService.showSubfoldersList(rootFolder);
        log.debug("Command show was called");
        return 0;
    }

}
