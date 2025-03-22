package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    /**
     * @return Resumes' count in storage (without null)
     */
    int size();

    /**
     * Save resume to storage
     */
    void save(Resume resume);

    /**
     * Updates resume in storage
     */
    void update(Resume resume);

    /**
     * @return resume by uuid
     */
    Resume get(String uuid);

    /**
     * Deletes resume from storage
     */
    void delete(String uuid);

    /**
     * Deletes all resumes in storage
     */
    void clear();

    /**
     * @return array, contains only Resumes in storage
     */
    Resume[] getAll();
}
