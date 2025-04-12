package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.ContactType.SKYPE;
import static com.urise.webapp.model.ResumeTestData.fillTestResume;
import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("./storage");
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";
    private static final String FULL_NAME_1 = "James Brown";
    private static final String FULL_NAME_2 = "Ray Charles";
    private static final String FULL_NAME_3 = "Sam Cooke";
    private static final String FULL_NAME_4 = "Otis Redding";
    private static final String FULL_NAME_DUMMY = "John Doe";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
//        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
//        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
//        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
//        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
        RESUME_1 = fillTestResume(UUID_1, FULL_NAME_1);
        RESUME_2 = fillTestResume(UUID_2, FULL_NAME_2);
        RESUME_3 = fillTestResume(UUID_3, FULL_NAME_3);
        RESUME_4 = fillTestResume(UUID_4, FULL_NAME_4);
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
                storage.save(new Resume("Dummy"));
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
        assertEquals(newResume1, storage.get(UUID_1));
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
        List<Resume> listSorted = storage.getAllSorted();
        assertEquals(listSorted, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void fillResume() {
        Resume filledResume = fillTestResume("fully-filled", "David Bowie");
        storage.save(filledResume);
        Resume checkResume = storage.get("fully-filled");
        assertEquals(7, checkResume.getContacts().size());
        assertEquals("skype:alex.ivanov", checkResume.getContacts().get(SKYPE));
        assertEquals(6, checkResume.getSections().size());
        TextSection personal = (TextSection) checkResume.getSections().get(PERSONAL);
        assertTrue(personal.getBody().startsWith("Аналитический"));
        TextSection objective = (TextSection) checkResume.getSections().get(OBJECTIVE);
        assertTrue(objective.getBody().endsWith("технологиям"));
        ListSection achievement = (ListSection) checkResume.getSections().get(ACHIEVEMENT);
        assertEquals(4, achievement.getItems().size());
        ListSection qualifications = (ListSection) checkResume.getSections().get(QUALIFICATIONS);
        assertEquals(11, qualifications.getItems().size());
        assertTrue(qualifications.getItems().get(5).startsWith("Java"));
        OrganizationSection experience = (OrganizationSection) checkResume.getSections().get(EXPERIENCE);
        assertEquals(4, experience.getOrganizations().size());
        assertEquals("Wrike", experience.getOrganizations().get(1).getHomePage().getName());
        assertEquals("Java архитектор", experience.getOrganizations().get(2).getPeriods().get(0).getTitle());
        assertEquals("01/2016", experience.getOrganizations().get(1).getPeriods().get(0).
                getEndDate().format(DateTimeFormatter.ofPattern("MM/yyyy")));
        OrganizationSection education = (OrganizationSection) checkResume.getSections().get(EDUCATION);
        assertEquals(4, education.getOrganizations().size());
        assertEquals("http://www.siemens.ru/", education.getOrganizations().get(2).getHomePage().getUrl());
        assertEquals("09/1997", education.getOrganizations().get(3).getPeriods().get(0).
                getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy")));
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