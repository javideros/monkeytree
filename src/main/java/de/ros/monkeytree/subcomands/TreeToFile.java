package de.ros.monkeytree.subcomands;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ros.monkeytree.service.IFileService;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Log4j2
@Command(name = "to-file", description = "Writes a tree of folders to a file", exitCodeOnExecutionException = 456)
public class TreeToFile implements Callable<Integer> {

    @Option(names = "-f", required = true, description = "folder to process")
    private String rootFolder;

    @Option(names = "-o", required = true, description = "file to write")
    private String file;

    @Autowired
    private IFileService fileService;

    /**
     * Handler for the command
     */
    @Override
    public Integer call() throws Exception {
        fileService.writeSubfoldersList(rootFolder, file);
        log.debug("");
        log.debug("Command tree-to-file was called");
        return 0;
    }
}