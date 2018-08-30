package com.example.testcft;


import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;

public class FileXMLScanner {
    private HashSet<File> foundedFiles = new HashSet<File>();
    private HashSet<File> processedFiles = new HashSet<File>();
    private File findFolder;

    public FileXMLScanner(String folderPath) {
        findFolder = new File(folderPath);
        init();
    }

    private void init() {
        for (File foundedFile : findFolder.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".xml");
            }
        })) {
            if (!foundedFiles.contains(foundedFile)) {
                foundedFiles.add(foundedFile);
            }
        }
    }

    public boolean hasNext() {
        init();
        if (foundedFiles.size() > processedFiles.size()) {
            return true;
        }
        return false;
    }

    public File getNext() {
        init();
        for (File fileForProcess : foundedFiles) {
            if (!processedFiles.contains(fileForProcess)) {
                processedFiles.add(fileForProcess);
                return fileForProcess;
            }
        }


        return null;
    }

    public String getStat() {
        return "Founded files: " + foundedFiles.size();
    }
}
