package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper();
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
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume r SET full_name = ? WHERE r.uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
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
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumeList = new ArrayList<>();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumeList;
        });
    }

    private interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    class SqlHelper {
        private <T> T execute(String sql, SqlExecutor<T> executor) {
            try (Connection conn = connectionFactory.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                return executor.execute(ps);
            } catch (SQLException e) {
                if (e.getSQLState().equals("23505")) {
                    throw new ExistStorageException(sql);
                }
//                System.out.println("getSQLState = " + e.getSQLState());
                throw new StorageException(e);
            }
        }
    }
}
