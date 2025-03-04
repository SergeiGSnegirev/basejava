package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * @return Resumes' count in storage (without null)
     */
    public int size() {
        return size;
    }

    /**
     * @return resume by uuid
     */
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("ОШИБКА: Резюме с uuid = '%s' нет в storage!\n", uuid);
        return null;
    }

    protected abstract int findIndex(String uuid);
}
