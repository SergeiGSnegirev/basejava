package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContactsAndSections(conn, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE resume r SET full_name = ? WHERE r.uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM section s WHERE s.resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContactsAndSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume r WHERE r.uuid = ?", psResume -> {
                    psResume.setString(1, uuid);
                    ResultSet rsResume = psResume.executeQuery();
                    if (!rsResume.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rsResume.getString("full_name"));
                    addContactsAndSections(resume);
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(
                "SELECT * FROM resume ORDER BY full_name, uuid", psResumes -> {
                    ResultSet rs = psResumes.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (rs.next()) {
                        Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        addContactsAndSections(resume);
                        resumes.add(resume);
                    }
                    return resumes;
                });
    }

    private void insertContactsAndSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> section : resume.getSections().entrySet()) {
                SectionType sectionType = section.getKey();
                String sectionValue;
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sectionValue = ((TextSection) section.getValue()).getBody();
                    case ACHIEVEMENT, QUALIFICATIONS -> sectionValue =
                            String.join("\n", ((ListSection) section.getValue()).getItems());
//                    case EXPERIENCE, EDUCATION -> {
//                        continue;
//                    }
                    default -> {
                        continue;
                    }
                }
                ps.setString(1, resume.getUuid());
                ps.setString(2, section.getKey().name());
                ps.setString(3, sectionValue);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContactsAndSections(Resume resume) {
        sqlHelper.execute(
                "SELECT * FROM contact c WHERE c.resume_uuid = ?", psContacts -> {
                    psContacts.setString(1, resume.getUuid());
                    ResultSet rsContacts = psContacts.executeQuery();
                    while (rsContacts.next()) {
                        String type = rsContacts.getString("type");
                        resume.addContact(ContactType.valueOf(type), rsContacts.getString("value"));
                    }
                    return null;
                });
        sqlHelper.execute(
                "SELECT * FROM section s WHERE s.resume_uuid = ?", psSections -> {
                    psSections.setString(1, resume.getUuid());
                    ResultSet rsSections = psSections.executeQuery();
                    while (rsSections.next()) {
                        SectionType sectionType = SectionType.valueOf(rsSections.getString("type"));
                        Section section = null;
                        switch (sectionType) {
                            case PERSONAL, OBJECTIVE -> section = new TextSection(rsSections.getString("value"));
                            case ACHIEVEMENT, QUALIFICATIONS -> section =
                                    new ListSection(rsSections.getString("value").split("\n"));
//                            case EXPERIENCE, EDUCATION -> {
//                            }
                            default -> {
                            }
                        }
                        resume.addSection(sectionType, section);
                    }
                    return null;
                });
    }
}