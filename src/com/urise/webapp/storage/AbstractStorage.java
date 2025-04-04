package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

//    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume resume) {
//        LOG.info("Save " + resume);
        doSave(getNotExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public void update(Resume resume) {
//        LOG.info("Update " + resume);
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public Resume get(String uuid) {
//        LOG.info("Get " + uuid);
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void delete(String uuid) {
//        LOG.info("Delete " + uuid);
        doDelete(getExistingSearchKey(uuid));
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
//            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
//            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
//        LOG.info("getAllSorted");
        List<Resume> resumeList = getAsList();
        Collections.sort(resumeList);
        return resumeList;
    }

    protected abstract List<Resume> getAsList();

    protected abstract SK findSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doDelete(SK searchKey);
}