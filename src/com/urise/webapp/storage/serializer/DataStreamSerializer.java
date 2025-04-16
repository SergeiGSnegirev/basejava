package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeItems(dos, contacts.entrySet(), contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());

            });
            Map<SectionType, Section> sections = resume.getSections();
            writeItems(dos, sections.entrySet(), sectionItem -> {
                SectionType sectionType = sectionItem.getKey();
                Section section = sectionItem.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) section).getBody());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> items = ((ListSection) section).getItems();
                        writeItems(dos, items, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                        writeItems(dos, organizations, organization -> {
                            Link link = organization.getHomePage();
                            dos.writeUTF(link.getName());
                            dos.writeUTF(link.getUrl());
                            List<Organization.Period> periods = organization.getPeriods();
                            writeItems(dos, periods, period -> {
                                writeLocalDate(dos, period.getStartDate());
                                writeLocalDate(dos, period.getEndDate());
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                    }
                }
            });
        }
    }

    private interface ItemWriter<E> {
        void accept(E item) throws IOException;
    }

    private <E> void writeItems(DataOutputStream dos, Collection<E> items, ItemWriter<E> writer) throws IOException {
        dos.writeInt(items.size());
        for (E item : items) {
            writer.accept(item);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonthValue());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section = null;
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> section = new TextSection(dis.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> section = new ListSection(
                            readItems(dis, dis::readUTF)
                    );
                    case EXPERIENCE, EDUCATION -> section = new OrganizationSection(
                            readItems(dis, () -> new Organization(
                                    new Link(dis.readUTF(), dis.readUTF()),
                                    readItems(dis, () -> new Organization.Period(
                                            readLocalDate(dis),
                                            readLocalDate(dis),
                                            dis.readUTF(),
                                            dis.readUTF())))));
                }
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

    private interface ItemReader<E> {
        E get() throws IOException;
    }

    private <E> List<E> readItems(DataInputStream dis, ItemReader<E> reader) throws IOException {
        int size = dis.readInt();
        List<E> items = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            items.add(reader.get());
        }
        return items;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}
