package de.ros.monkeytree.service;

public interface IFileService {

    public void showSubfoldersList(String path);

    /*
     * Writes the list of subfolders to the file.
     */
    public void writeSubfoldersList(String path, String outputFile);

    /*
     * Reads the file and creates the list of subfolders indicated in the file.
     */
    public void readAndCreateSubfoldersList(String inputFile, String path);

    /*
     * Compares two folders and prints the differences.
     */
    public void compareFoldersTree(String sourceFolder, String targetFolder);
}
