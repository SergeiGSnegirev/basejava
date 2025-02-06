import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    /**
     * Deletes all resumes in storage
     */
    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            }
            storage[i] = null;
        }
    }

    /**
     * Appends resume to the end of Resumes storage (places it instead of the first null element)
     */
    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
            else if (Objects.equals(storage[i].uuid, r.uuid)) {
                System.out.printf("Резюме с uuid = '%s' уже имеется в storage. Введите уникальное значение uuid!\n", storage[i].uuid);
                break;
            }
        }
    }

    /**
     * @return resume by uuid
     */
    Resume get(String uuid) {
        Resume r = null;
        for (Resume resume : storage) {
            if (resume == null) break;
            if (Objects.equals(resume.uuid, uuid)) {
                r = resume;
            }
        }
        return r;
    }

    /**
     * Deletes resume by its uuid by shifting the rest of storage to the left
     */
    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
            else {
                if (Objects.equals(storage[i].uuid, uuid)) {
                    storage[i] = null; // clear found resume
                    /* shift the rest resumes on one position to the left */
                    for (i = i + 1; i < storage.length; i++) {
                        if (storage[i] == null) {
                            break;
                        } else {
                            storage[i-1] = storage[i];
                            storage[i] = null;
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int i;
        for (i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
        }
        Resume[] resumes = new Resume[i];
        System.arraycopy(storage, 0, resumes, 0, resumes.length);

        return resumes;
    }

    /**
     * @return Resumes' count in storage (without null)
     */
    int size() {
        int i;
        for (i = 0; i < storage.length; i++) {
            if (storage[i] == null) break;
        }
        return i;
    }
}
