package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageDb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid = request.getParameter("uuid");
        List<Resume> resumes = new ArrayList<>();
        if (uuid == null) {
            resumes = storage.getAllSorted();
        } else {
            try {
                resumes.add(storage.get(uuid));
            } catch (NotExistStorageException ignored) {
            }
        }
        Writer writer = response.getWriter();
        writer.write("""
                <html>
                <head>
                    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
                    <link href='css/table.css' rel='stylesheet' type='text/css'/>
                    <title>Список всех резюме</title>
                </head>
                <body>
                <h1>Список резюме</h1>
                <section>
                <table>
                    <tr>
                        <th>Имя</th>
                        <th>Email</th>
                    </tr>
                """);
        for (Resume resume : resumes) {
            writer.write("    <tr>\n" +
                         "        <td><a href='resume?uuid=" + resume.getUuid() + "'>" + resume.getFullName() + "</a></td>\n" +
                         "        <td>" + resume.getContact(ContactType.EMAIL) + "</td>\n" +
                         "    </tr>\n");
        }
        writer.write("""
                </table>
                </section>
                </body>
                </html>
                """);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
