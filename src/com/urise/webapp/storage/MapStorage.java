package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapStorage extends MapUuidStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doUpdate(Object resume, Resume updateResume) {
        storage.put(((Resume) resume).getUuid(), updateResume);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }
}