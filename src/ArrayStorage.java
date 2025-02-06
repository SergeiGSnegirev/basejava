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
     * Appends resume to the end of Resumes storage (places it instead of the first null element)
     */
    void save(Resume r) {
        for (int i = 0; i <= size; i++) {
            if (i == size) {
                storage[i] = r;
                size++;
                break;
            } else if (Objects.equals(storage[i].uuid, r.uuid)) {
                System.out.printf("Резюме с uuid = '%s' уже имеется в storage. Введите уникальное значение uuid!\n", storage[i].uuid);
                break;
            }
        }
    }

    /**
     * @return resume by uuid
     */
    Resume get(String uuid) {
        int i;
        for (i = 0; i <= size; i++) {
            if (i == size) {
                return null;
            }
            if (Objects.equals(storage[i].uuid, uuid)) break;
        }
        return storage[i];
    }

    /**
     * Deletes resume by its uuid by shifting the rest of storage to the left
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
