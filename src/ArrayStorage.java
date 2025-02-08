import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    /**
     * Deletes all resumes in storage
     */
    void clear() {
        for (int i = 0; i < size; i++) storage[i] = null;
        size = 0;
    }

    /**
     * Appends resume to the end of Resumes storage
     */
    void save(Resume r) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, r.uuid)) {
                System.out.printf("Резюме с uuid = '%s' уже имеется в storage. Введите уникальное значение uuid!\n", storage[i].uuid);
                return;
            }
        }
        storage[size] = r;
        size++;
    }

    /**
     * @return resume by uuid
     */
    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) return storage[i];
        }
        return null;
    }

    /**
     * Deletes resume by its uuid by replacing it by the last one
     */
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    /**
     * @return Resumes' count in storage (without null)
     */
    int size() {
        return size;
    }
}
