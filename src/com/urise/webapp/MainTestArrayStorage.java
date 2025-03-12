package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage/SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage(); // ArrayStorage()

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid4");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r2);

        Resume r2n = new Resume("uuid2");
        System.out.println("\nUpdating r2...");
        ARRAY_STORAGE.update(r2n);

        Resume rd = new Resume("dummy");
        System.out.println("\nUpdating dummy...");
        ARRAY_STORAGE.update(rd);

        System.out.println("\nGet r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("\nSize: " + ARRAY_STORAGE.size());

        System.out.println("\nGetting dummy...");
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.println("\nDeleting dummy...");
        ARRAY_STORAGE.delete("dummy");

        printAll();
        System.out.println("\nDeleting r2...");
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();
        System.out.println("\nDeleting r4...");
        ARRAY_STORAGE.delete(r4.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}