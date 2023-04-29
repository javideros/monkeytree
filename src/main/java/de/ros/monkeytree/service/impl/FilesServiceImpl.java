package de.ros.monkeytree.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import de.ros.monkeytree.model.Folder;
import de.ros.monkeytree.service.IFileService;
import lombok.extern.log4j.Log4j2;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service class for handling files and folders.
 */
@Log4j2
@Service("FileService")
public class FilesServiceImpl implements IFileService {

    /**
     * Method for displaying folder contents.
     *
     * @param path The path to the root folder.
     */
    @Override
    public void showSubfoldersList(String path) {
        File maindir = new File(path);
        if (maindir.exists() && maindir.isDirectory()) {
            System.out.println(displayTree(maindir, new ArrayList<>()));
        }

    }

    /**
     * Recursive method for loading subfolders of a giving folder.
     *
     * @param folder
     * @param lastFolders
     * @return
     */
    private String displayTree(File folder, List<Boolean> lastFolders) {
        String directory = "";

        FileFilter directoryfilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };

        if (lastFolders.size() != 0)
            directory += (!(lastFolders.get(lastFolders.size() - 1)) ? "├─ " : "└─ ");
        directory += folder.getName() + " ";

        File[] files = folder.listFiles(directoryfilter);
        int count = files.length;
        files = sortFiles(files);
        for (int i = 0; i < count; i++) {
            directory += "\n";
            for (Boolean lastFolder : lastFolders) {
                if (lastFolder) {
                    directory += "   ";
                } else {
                    directory += "│  ";
                }
            }
            if (files[i].isDirectory()) {
                List<Boolean> list = new ArrayList<>(lastFolders);
                list.add(i + 1 == count);
                directory += displayTree(files[i], list);
            }
        }
        return directory;
    }

    /**
     * Sorted files and subfolders of a folder.
     *
     * @param folder
     * @return list of sorted files and subfolders.
     */
    private File[] sortFiles(File[] folder) {

        Arrays.sort(folder);
        List<File> sorted = new ArrayList<>();

        for (int i = 0; i < folder.length; i++) {
            if (folder[i].isDirectory())
                sorted.add(folder[i]);
        }

        return sorted.toArray(new File[sorted.size()]);
    }

    /**
     * Method for write subfolders of a giving folder.
     *
     * @param folder the folder to write
     * @param out    the output file to write the folder tree.
     */
    @Override
    public void writeSubfoldersList(String path, String outputFile) {
        File maindir = new File(path);
        if (maindir.exists() && maindir.isDirectory()) {
            Folder mainfolder = loadSubfoldersList(maindir, "");
            writeFolderListIntoFile(mainfolder, outputFile);
        }
    }

    /**
     * Write the folder tree from the given file.
     *
     * @param the folder where to write the tree.
     * @param the input file to read the tree.
     */
    @Override
    public void readAndCreateSubfoldersList(String path, String inputFile) {
        File readFile = new File(inputFile);
        File file = new File(path);
        log.log(Level.getLevel("Show"), "Creating subfolders of {}", path);
        if (readFile.exists() && readFile.isFile()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Folder mainfolder = mapper.readValue(readFile, Folder.class);
                createSubfoldersList(mainfolder, path);
            } catch (IOException e) {
                log.error("Error reading input file: {} : {}", inputFile, e.getMessage());
            }
        }
    }

    /**
     * Shows the tree of two given folder.
     *
     * @param sourceFolder the source folder.
     * @param targetFolder the target folder.
     */
    @Override
    public void compareFoldersTree(String sourceFolder, String targetFolder) {
        int level = 1;
        File sourceDir = new File(sourceFolder);
        // String firstTree = "";
        // String secondTree = "";
        if (sourceDir.exists() && sourceDir.isDirectory()) {
            // firstTree =
            System.out.println(
                    displayTree(sourceDir, new ArrayList<>()));
            loadSubfoldersListWithLevel(sourceDir, "", level);
        }
        File targetDir = new File(targetFolder);
        if (targetDir.exists() && targetDir.isDirectory()) {
            // secondTree =
            System.out.println(
                    displayTree(targetDir, new ArrayList<>()));
            loadSubfoldersListWithLevel(sourceDir, "", level);
        }
        // printTrees(firstTree, secondTree);
    }

    /**
     *
     * @param firstTree
     * @param secondTree
     */
    private void printTrees(String firstTree, String secondTree) {
        String[] firstTreeLines = firstTree.split("\n");
        String[] secondTreeLines = secondTree.split("\n");
        int firstTreeLinesCount = firstTreeLines.length;
        int secondTreeLinesCount = secondTreeLines.length;
        int maxLines = Math.max(firstTreeLinesCount, secondTreeLinesCount);
        for (int i = 0; i < maxLines; i++) {
            if (i < firstTreeLinesCount) {
                System.out.print(firstTreeLines[i]);
            }
            System.out.print("                              ");
            if (i < secondTreeLinesCount) {
                System.out.println(secondTreeLines[i] + "\n");
            }
        }

    }

    /**
     *
     * @param mainfolder
     * @param path
     */
    private void createSubfoldersList(Folder mainfolder, String path) {
        log.log(Level.getLevel("Show"), "Creating subfolders of {}", path);
        List<Folder> folders = mainfolder.getSubfolders();
        folders.forEach(folder -> {
            String folderPath = path + File.separator + folder.getName();
            File folderFile = new File(folderPath);
            if (!folderFile.exists()) {
                folderFile.mkdirs();
            }
            createSubfoldersList(folder, folderPath);
        });

    }

    /**
     *
     * @param mainfolder
     * @param outputFile
     */
    private void writeFolderListIntoFile(Folder mainfolder, String outputFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(outputFile), mainfolder);
        } catch (IOException e) {
            log.error("Error writing output file: {} : {}", outputFile, e.getMessage());
        }
    }

    /**
     *
     * @param dir
     * @param separator
     * @return
     */
    private Folder loadSubfoldersList(File dir, String separator) {
        Folder folder = new Folder();
        folder.setName(dir.getName());
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(separator + file.getName());
                Folder subFolder = loadSubfoldersList(file, separator + "\t");
                folder.addFolder(subFolder);
            }
        }
        return folder;
    }

    /**
     *
     * @param dir
     * @param separator
     * @param level
     * @return
     */
    private Folder loadSubfoldersListWithLevel(File dir, String separator, int level) {
        Folder folder = new Folder();
        folder.setName(dir.getName());
        folder.setLevel(level);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // System.out.println(" |" + separator + "-" + file.getName());
                Folder subFolder = loadSubfoldersListWithLevel(file, separator + "-", level + 1);
                folder.addFolder(subFolder);
            }
        }
        return folder;
    }

    /**
     *
     * @param dir
     * @param separator
     * @param level
     */
    private void displayDirectoryContents(File dir, String separator, int level) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("  |" + separator + "-" + file.getName());
                displayDirectoryContents(file, separator + "-", level);
            }
        }
    }

}