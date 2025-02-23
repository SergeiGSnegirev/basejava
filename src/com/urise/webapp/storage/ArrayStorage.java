package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_SIZE = 10000;
    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    /**
     * @return resume's position in storage by its uuid or -1 if it is not found
     */
    private int getResumePosition(String uuid, boolean warning) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        if (warning) {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", uuid);
        }
        return -1;
    }

    /**
     * Deletes all resumes in storage
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * Updates resume in storage
     */
    public void update(Resume r) {
        int i = getResumePosition(r.getUuid(), true);
        if (i != -1) {
            // stub, TBD what to update
            storage[i] = r;
        }
    }

    /**
     * Appends resume to the end of Resumes storage
     */
    public void save(Resume r) {
        int i = getResumePosition(r.getUuid(), false);
        if (i != -1) {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' уже имеется в storage. ", storage[i].getUuid());
            System.out.println("Введите уникальное значение uuid!");
            return;
        }
        if (size == STORAGE_SIZE) {
            System.out.printf("ОШИБКА: storage полностью заполнено, хранит %s резюме.\n", STORAGE_SIZE);
            return;
        }
        storage[size] = r;
        size++;
    }

    /**
     * @return resume by uuid
     */
    public Resume get(String uuid) {
        int i = getResumePosition(uuid, true);
        if (i != -1) {
            return storage[i];
        }
        return null;
    }

    /**
     * Deletes resume by its uuid by replacing it by the last one
     */
    public void delete(String uuid) {
        int i = getResumePosition(uuid, true);
        if (i != -1) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return Resumes' count in storage (without null)
     */
    public int size() {
        return size;
    }
}
