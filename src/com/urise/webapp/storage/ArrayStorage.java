package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * @return resume's position in storage by its uuid or -1 if it is not found
     */
    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Appends resume to the end of Resumes storage
     */
    @Override
    protected void storeResume(Resume resume, int index) {
        storage[size] = resume;
    }

    /**
     * Remove resume by its index by replacing it on the last one
     */
    @Override
    protected void relocateResume(int index) {
        storage[index] = storage[size - 1];
    }
}
