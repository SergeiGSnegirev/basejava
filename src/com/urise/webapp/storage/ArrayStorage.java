package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    /**
     * Deletes all resumes in storage
     */
    public void clear() {
        for (int i = 0; i < size; i++) storage[i] = null;
        size = 0;
    }

    /**
     * Appends resume to the end of Resumes storage
     */
    public void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), r.getUuid())) {
                System.out.printf("Резюме с uuid = '%s' уже имеется в storage. Введите уникальное значение uuid!\n", storage[i].getUuid());
                return;
            }
        }
        storage[size] = r;
        size++;
    }

    /**
     * @return resume by uuid
     */
    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) return storage[i];
        }
        return null;
    }

    /**
     * Deletes resume by its uuid by replacing it by the last one
     */
    public void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    /**
     * @return Resumes' count in storage (without null)
     */
    public int size() {
        return size;
    }
}
