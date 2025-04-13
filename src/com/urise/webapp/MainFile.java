package com.urise.webapp;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            throw new StorageException("Directory read error");
        }
        System.out.println(fList.length);
        System.out.println(Arrays.toString(fList));

        // java.nio.file

        final String STORAGE = "storage";
        final String UUID1 = "uuid1";
        final String DUMMY_FILE = "dummy";

        Path storageDir = Paths.get(STORAGE);

        // check if file exists in parent dir
        Path fileDummy = storageDir.resolve(DUMMY_FILE);
        System.out.println("if dummy file exists = " + Files.exists(fileDummy));
        Path uuid1 = storageDir.resolve(UUID1);
        System.out.println("if uuid1 file exists = " + Files.exists(uuid1));

        // count files in dir
        int fileCount;
        try (Stream<Path> streamDir = Files.list(storageDir)) {
            fileCount = (int) streamDir.filter(p -> p.toFile().isFile()).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error");
        }
        System.out.println("files in storage dir = " + fileCount);

        // delete file
        try {
            Files.delete(uuid1);
        } catch (IOException e) {
//            throw new StorageException("File delete error", uuid1.toString(), e);
        }

        // get resume list from files
        List<Resume> resumes;
        try (Stream<Path> streamDir = Files.list(storageDir)) {
            resumes = streamDir
                    .map(f -> MainFile.doGet(f))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
        System.out.println(resumes);

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

    // temp static method for testing purpose
    public static Resume doGet(Path file) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.toString(), e);
        }
    }

    // temp static method for testing purpose
    public static Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", e);
        }
    }
}
