package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

//    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    /**
     * @return resume's position in storage by its uuid or (-(insertion point) - 1) if it is not found
     */
    @Override
    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    /**
     * Store resume keeping storage sorted - shifting the rest to the right on one position
     */
    @Override
    protected void storeByIndex(Resume resume, int index) {
        int insertPosition = Math.abs(index) - 1;
        System.arraycopy(storage, insertPosition, storage, insertPosition + 1, size - insertPosition);
        storage[insertPosition] = resume;
    }

    /**
     * Remove resume by shifting the rest to the left on its index
     */
    @Override
    protected void relocateResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}