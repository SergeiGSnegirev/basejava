package com.urise.webapp;

import com.urise.webapp.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) {

        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // check if empty dir throw exception on list() -> NO
        File emptyDir = new File("./storage");
        System.out.println("-".repeat(60));
        String[] fList = emptyDir.list();
        if (fList == null) {
            throw new StorageException("Directory read error", null);
        }
        System.out.println(fList.length);
        System.out.println(Arrays.toString(fList));

        printDirectory(dir);

    }

    public static void printDirectory(File dir) {
        System.out.printf("%s%n>> Print dir '%s' with subdirectories:%n".
                formatted("-".repeat(60), dir.getPath()));
        printDirectory(dir, 0);
    }

    public static void printDirectory(File dir, int level) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.print("\t".repeat(level));
                if (file.isDirectory()) {
                    System.out.printf("[%s]%n", file.getName());
                    printDirectory(file, level + 1);
                } else {
                    System.out.printf("%s%n", file.getName());
                }
            }
        }
    }
}
