package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * Deletes all resumes in storage
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * Save resume to storage
     */
    public final void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' уже имеется в storage.\n", resume.getUuid());
        } else if (size == STORAGE_LIMIT) {
            System.out.printf("ОШИБКА: storage полностью заполнено, хранит %s резюме.\n", STORAGE_LIMIT);
        } else {
            storeResume(resume, index);
            size++;
        }
    }

    /**
     * Updates resume in storage
     */
    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", resume.getUuid());
        }
    }

    /**
     * @return resume by uuid
     */
    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", uuid);
        return null;
    }

    /**
     * Deletes resume from storage
     */
    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            relocateResume(index);
            storage[size - 1] = null; // clear the last stored resume
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
     * @return Resumes' count in storage (without null)
     */
    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void storeResume(Resume resume, int index);

    protected abstract void relocateResume(int index);
}
