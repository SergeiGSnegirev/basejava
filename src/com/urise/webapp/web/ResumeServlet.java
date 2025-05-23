package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorageDb();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> resume = storage.get(uuid);
            case "edit" -> resume = prepareResumeToEdit(storage.get(uuid));
            case "add" -> resume = prepareResumeToEdit(new Resume());
            default -> throw new IllegalArgumentException("Action " + action + " is unknown");
        }
        if (resume != null) {
            request.setAttribute("resume", resume);
            request.getRequestDispatcher("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        boolean newResume = uuid == null || uuid.isEmpty();

        Resume resume;
        if (newResume) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                switch (type) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value.trim()));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            resume.addSection(type, new ListSection(value.trim().split("\n")));
                    case EXPERIENCE, EDUCATION -> {
                    } // stub
                    default -> throw new IllegalArgumentException("Section type " + type + " is unknown");
                }
            }
        }
        if (newResume) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private static Resume prepareResumeToEdit(Resume resume) {
        for (SectionType sectionType : SectionType.values()) {
            Section section = resume.getSection(sectionType);
            if (section == null) {
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> section = new TextSection("");
                    case ACHIEVEMENT, QUALIFICATIONS -> section = new ListSection("");
                    case EXPERIENCE, EDUCATION -> section = new OrganizationSection(); // stub
                }
                resume.addSection(sectionType, section);
            }
        }
        return resume;
    }
}
