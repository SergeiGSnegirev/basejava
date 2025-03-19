package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    /**
     * Deletes all resumes in storage
     */
    @Override
    public abstract void clear();

    /**
     * Updates resume in storage
     */
    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            updateByIndex(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    /**
     * Save resume to storage
     */
    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            store(resume, index);
        }
    }

    /**
     * @return resume by uuid
     */
    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getByIndex(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    /**
     * Deletes resume from storage
     */
    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            removeByIndex(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage
     */
    @Override
    public abstract Resume[] getAll();

    /**
     * @return Resumes' count in storage (without null)
     */
    @Override
    public abstract int size();

    protected abstract int findIndex(String uuid);

    protected abstract void updateByIndex(int index, Resume resume);

    protected abstract void store(Resume resume, int index);

    protected abstract Resume getByIndex(int index);

    protected abstract void removeByIndex(int index);
}