package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage/SortedArrayStorage/ListStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private static Storage STORAGE;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите тип хранилища резюме: 1 - массив, 2 - отсортированный массив, 3 - список, 4 - хэш карта: ");
        String storageType = reader.readLine().trim();
        switch (storageType) {
            case "1" -> STORAGE = new ArrayStorage();
            case "2" -> STORAGE = new SortedArrayStorage();
            case "3" -> STORAGE = new ListStorage();
            case "4" -> STORAGE = new MapStorage();
            default -> {
                STORAGE = new ArrayStorage();
                System.out.println("Неверный тип хранилища. По умолчанию выбран массив.");
            }
        }

        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid | update uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid);
                    STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid);
                    STORAGE.update(r);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(uuid));
                    break;
                case "clear":
                    STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        Resume[] all = STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}