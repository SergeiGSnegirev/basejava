package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private static final Storage STORAGE = Config.get().getStorageDb();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');

        String uuid = request.getParameter("uuid");
        List<Resume> resumes = new ArrayList<>();
        if (uuid == null) {
            resumes = STORAGE.getAllSorted();
        } else {
            try {
                resumes.add(STORAGE.get(uuid));
            } catch (NotExistStorageException ignored) {
            }
        }
        response.getWriter().write(getResumePage(resumes));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private String getResumePage(List<Resume> resumes) {
        StringBuilder resumeTable = new StringBuilder("<html> <head> <title>Резюме</title>" +
                "<link href='css/table.css' rel='stylesheet' type='text/css'/>" +
                "</head> <body> <h1>Резюме</h1>" +
                "<table> <tr> <th>uuid</th> <th>ФИО</th> </tr>");
        for (Resume resume : resumes) {
            resumeTable.append("<tr> <td>")
                    .append(resume.getUuid())
                    .append("</td> <td>")
                    .append(resume.getFullName())
                    .append("</td> </tr>");
        }
        return resumeTable.append("</table> </body> </html>").toString();
    }
}
