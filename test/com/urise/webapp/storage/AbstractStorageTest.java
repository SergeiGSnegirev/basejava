package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";
    private static final String FULL_NAME_1 = "Sam Cooke";
    private static final String FULL_NAME_2 = "Ray Charles";
    private static final String FULL_NAME_3 = "James Brown";
    private static final String FULL_NAME_4 = "Otis Redding";
    private static final String FULL_NAME_DUMMY = "John Doe";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    }

    private final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAllSorted().toArray(new Resume[0]));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(DUMMY));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    @EnabledIf("overflowTestCondition")
    public void saveOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Premature storage overflow");
        }
        assertThrows(StorageException.class, () -> storage.save(RESUME_4));
    }

    @Test
    public void update() {
        Resume newResume1 = new Resume(UUID_1, "Marvin Gaye");
        storage.update(newResume1);
        assertSame(newResume1, storage.get(newResume1.getUuid()));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(DUMMY, FULL_NAME_DUMMY)));
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(DUMMY));
    }

    @Test
    public void getAllSorted() {
        Resume[] storageArray = storage.getAllSorted().toArray(new Resume[0]);
        assertArrayEquals(new Resume[]{RESUME_3, RESUME_2, RESUME_1}, storageArray);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private boolean overflowTestCondition() {
        return storage.getClass().getName().contains("ArrayStorage");
    }
}