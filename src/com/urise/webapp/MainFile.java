package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {

        printDirectory("src\\com\\urise\\webapp");
    }

    public static void printDirectory(String directory) {
        printDirectory(directory, 0);
    }

    public static void printDirectory(String directory, int level) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.print("\t".repeat(level));
                if (file.isDirectory()) {
                    System.out.printf("[%s]%n", file.getName());
                    printDirectory(file.getAbsolutePath(), level + 1);
                } else {
                    System.out.printf("%s%n", file.getName());
                }
            }
        }
    }
}
