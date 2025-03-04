package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

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
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", resume.getUuid());
        }
    }

    /**
     * Appends resume to the end of Resumes storage
     */
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' уже имеется в storage. ", resume.getUuid());
        } else if (size == STORAGE_LIMIT) {
            System.out.printf("ОШИБКА: storage полностью заполнено, хранит %s резюме.\n", STORAGE_LIMIT);
        } else {
            storage[size] = resume;
            size++;
        }
    }

    /**
     * Deletes resume by its uuid by replacing it by the last one
     */
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return resume's position in storage by its uuid or -1 if it is not found
     */
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }
}
