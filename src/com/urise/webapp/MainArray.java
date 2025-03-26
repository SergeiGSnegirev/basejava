package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for com.urise.webapp.storage.ArrayStorage/SortedArrayStorage/ListStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private static Storage STORAGE;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Введите тип хранилища резюме: 1 - массив, 2 - отсортированный массив," +
                " 3 - список, 4 - хэш карта, 5 - хэш карта с поиском целиком резюме: ");
        String storageType = reader.readLine().trim();
        switch (storageType) {
            case "1" -> STORAGE = new ArrayStorage();
            case "2" -> STORAGE = new SortedArrayStorage();
            case "3" -> STORAGE = new ListStorage();
            case "4" -> STORAGE = new MapUuidStorage();
            case "5" -> STORAGE = new MapStorage();
            default -> {
                STORAGE = new ArrayStorage();
                System.out.println("Неверный тип хранилища. По умолчанию выбран массив.");
            }
        }

        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullname | update uuid fullname |" +
                    " delete uuid | get uuid | clear | exit):\n");
            String[] params = reader.readLine().trim().split(" ");
            if (params.length < 1 || params.length > 4) {
                System.out.println("Неверная команда.");
                continue;
            }
            params[0] = params[0].toLowerCase();
            String uuid = null;
            String fullUuid = null;
            if (params.length >= 2) {
                uuid = params[1].intern().toLowerCase();
            }
            if (params.length >= 3) {
                fullUuid = params[2].intern();
            }
            if (params.length == 4) {
                fullUuid += " " + params[3].intern();
            }
            switch (params[0]) {
                case "list":
                    printAllSortedList();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    if (fullUuid == null) {
                        System.out.println("Неверная команда.");
                        continue;
                    }
                    r = new Resume(uuid);
                    r.setFullName(fullUuid);
                    STORAGE.save(r);
                    printAllSortedList();
                    break;
                case "update":
                    if (fullUuid == null) {
                        System.out.println("Неверная команда.");
                        continue;
                    }
                    r = new Resume(uuid);
                    r.setFullName(fullUuid);
                    STORAGE.update(r);
                    printAllSortedList();
                    break;
                case "delete":
                    STORAGE.delete(uuid);
                    printAllSortedList();
                    break;
                case "get":
                    System.out.println(STORAGE.get(uuid));
                    break;
                case "clear":
                    STORAGE.clear();
                    printAllSortedList();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAllSortedList() {
        List<Resume> allSorted = STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (allSorted.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Resume r : allSorted) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
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