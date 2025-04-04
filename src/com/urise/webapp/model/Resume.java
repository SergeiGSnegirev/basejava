package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    // Full name of resume owner
    private final String fullName;

    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(Map<SectionType, Section> sections) {
        this.sections = sections;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Resume resume)) return false;

        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) &&
                contacts.equals(resume.contacts) && sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String resumeString = "%n              uuid: %s%n          fullName: %s%n".formatted(uuid, fullName);
        resumeString += contactsToString().isEmpty() ? "" : "%n%s".formatted(contactsToString());
        resumeString += sectionsToString().isEmpty() ? "" : "%n%s".formatted(sectionsToString());
        return resumeString;
//        return ("%n              uuid: %s%n          fullName: %s%n%n%s%n%s").formatted(
//                uuid, fullName, contactsToString(), sectionsToString());
    }

    private String contactsToString() {
        StringBuilder contactsString = new StringBuilder();
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            contactsString.append("%18s: %s%n".formatted(entry.getKey().getTitle(), entry.getValue()));
        }
        return contactsString.toString();
    }

    private String sectionsToString() {
        StringBuilder sectionsString = new StringBuilder();
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            sectionsString.append("%18s:%n%s%n".formatted(entry.getKey().getTitle().toUpperCase(),
                    entry.getValue()));
        }
        return sectionsString.toString();
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}