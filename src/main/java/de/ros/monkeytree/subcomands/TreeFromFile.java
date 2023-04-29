package de.ros.monkeytree.subcomands;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ros.monkeytree.service.IFileService;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.apache.commons.lang3.StringUtils;

/**
 * Class handling the subcommand tree-from-file.
 */
@Component
@Log4j2
@Command(name = "from-file", description = "Prints the tree from the file", exitCodeOnExecutionException = 356)
public class TreeFromFile implements Callable<Integer> {

    @Option(names = "-f", required = true, description = "folder where to write")
    private String rootFolder;

    @Option(names = "-o", required = true, description = "file to read")
    private String file;

    @Autowired
    private IFileService fileService;

    /**
     * Handles the command tree-from-file.
     *
     * @param rootFolder the root folder to read.
     * @param file       the file to read.
     */
    @Override
    public Integer call() throws Exception {
        fileService.readAndCreateSubfoldersList(rootFolder, file);
        log.debug(StringUtils.EMPTY);
        log.debug("Command tree-from-file was called");
        return 0;
    }
}
