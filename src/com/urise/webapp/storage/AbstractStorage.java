package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        saveBySearchKey(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public void update(Resume resume) {
        updateBySearchKey(getExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public Resume get(String uuid) {
        return getBySearchKey(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteBySearchKey(getExistingSearchKey(uuid));
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveBySearchKey(Object searchKey, Resume resume);

    protected abstract void updateBySearchKey(Object searchKey, Resume resume);

    protected abstract Resume getBySearchKey(Object searchKey);

    protected abstract void deleteBySearchKey(Object searchKey);
}