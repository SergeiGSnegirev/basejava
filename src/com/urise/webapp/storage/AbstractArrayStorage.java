package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {//implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    /**
     * Deletes all resumes in storage
     */
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return Resumes' count in storage (without null)
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    protected void updateByIndex(int index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected void store(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            storeByIndex(resume, index);
            size++;
        }
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage[index];
    }

    @Override
    protected void removeByIndex(int index) {
        relocateResume(index);
        storage[size - 1] = null; // clear the last stored resume
        size--;
    }

    @Override
    protected abstract int findIndex(String uuid);

    protected abstract void storeByIndex(Resume resume, int index);

    protected abstract void relocateResume(int index);
}