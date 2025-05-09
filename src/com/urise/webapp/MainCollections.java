package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollections {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Sam Cooke");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Ray Charles");
    private static final Resume RESUME_3 = new Resume(UUID_3, "James Brown");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Otis Redding");

    public static void main(String[] args) {
        Collection<Resume> collection = new ArrayList<>();
        collection.add(RESUME_1);
        collection.add(RESUME_2);
        collection.add(RESUME_3);

        for (Resume resume : collection) {
            System.out.println(resume);
            if (Objects.equals(resume.getUuid(), UUID_1)) {
//                collection.remove(resume);
            }
        }

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            System.out.println(resume);
            if (Objects.equals(resume.getUuid(), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection);

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1, RESUME_1);
        map.put(UUID_2, RESUME_2);
        map.put(UUID_3, RESUME_3);

        // wrong: extra work to get Value
        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        // right: at once get both Key & Value
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
        List<Resume> resumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        System.out.println(resumes);
//        resumes.remove(1); // UnsupportedOperationException
    }
}
