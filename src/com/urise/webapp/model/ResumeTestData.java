package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Александр Иванов");

        // fill CONTACTS
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(MOBILE_PHONE, "+7 (916) 987-65-43");
        contacts.put(EMAIL, "alex.ivanov@gmail.com");
        contacts.put(SKYPE, "skype:alex.ivanov");
        contacts.put(TELEGRAM, "https://t.me/AlexIvanov");
        contacts.put(LINKEDIN, "https://www.linkedin.com/in/alexivanov");
        contacts.put(GITHUB, "https://github.com/alex.ivanov");
        contacts.put(HOME_PAGE, "http://alexivanov.ru/");
        resume.setContacts(contacts);

        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

        // fill PERSONAL
        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность," +
                " инициативность. Пурист кода и архитектуры.");
        sections.put(PERSONAL, personal);

        // fill OBJECTIVE
        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по" +
                " Java Web и Enterprise технологиям");
        sections.put(OBJECTIVE, objective);

        // fill ACHIEVEMENT
        ListSection achievement = new ListSection(List.of("Организация команды и успешная реализация Java проектов" +
                        " для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система" +
                        " мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2," +
                        " многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный" +
                        " maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие" +
                        " (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция" +
                        " с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С," +
                        " Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:" +
                        " Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP" +
                        " модулей, интеграция CIFS/SMB java сервера."
        ));
        sections.put(ACHIEVEMENT, achievement);

        // fill QUALIFICATIONS
        ListSection qualifications = new ListSection(List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat," +
                        " Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite," +
                        " MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security," +
                        " Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin," +
                        " Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT," +
                        " MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP," +
                        " OAuth1, OAuth2, JWT.",
                "Родной русский, английский \"upper intermediate\""
        ));
        sections.put(QUALIFICATIONS, qualifications);

        // fill EXPERIENCE
        OrganizationSection experience = new OrganizationSection(
                new ArrayList<>(List.of(new Organization("Java Online Projects", "http://javaops.ru/",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2013, 10, 1), LocalDate.now(),
                                                "Автор проекта.",
                                                "Создание, организация и проведение Java онлайн проектов и стажировок.")))),
                        new Organization("Wrike", "https://www.wrike.com/",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2014, 10, 1),
                                                LocalDate.of(2016, 1, 1),
                                                "Старший разработчик (backend)",
                                                "Проектирование и разработка онлайн платформы управления" +
                                                        " проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava," +
                                                        " Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация," +
                                                        " авторизация по OAuth1, OAuth2, JWT SSO.")))),
                        new Organization("RIT Center", "",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2012, 4, 1),
                                                LocalDate.of(2014, 10, 1),
                                                "Java архитектор",
                                                "Организация процесса разработки системы ERP для разных окружений:" +
                                                        " релизная политика, версионирование, ведение CI (Jenkins), миграция базы" +
                                                        " (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx)," +
                                                        " AAA via SSO. Архитектура БД и серверной части системы." +
                                                        " Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices)," +
                                                        " сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                                                        " Интеграция Alfresco JLAN для online редактирование из браузера" +
                                                        " документов MS Office. Maven + plugin development, Ant, Apache Commons," +
                                                        " Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita," +
                                                        " Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")))),
                        new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2010, 12, 1),
                                                LocalDate.of(2012, 4, 1),
                                                "Ведущий программист",
                                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring," +
                                                        " Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и" +
                                                        " серверной части CRM. Реализация RIA-приложения для администрирования," +
                                                        " мониторинга и анализа результатов в области алгоритмического трейдинга." +
                                                        " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."))))
                )));
        sections.put(EXPERIENCE, experience);

        // fill EDUCATION
        OrganizationSection education = new OrganizationSection(
                new ArrayList<>(List.of(new Organization("Coursera", "https://www.coursera.org/course/progfun",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2013, 3, 1),
                                                LocalDate.of(2013, 5, 1),
                                                "'Functional Programming Principles in Scala' by Martin Odersky",
                                                "")))),
                        new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2011, 3, 1),
                                                LocalDate.of(2011, 4, 1),
                                                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                                                "")))),
                        new Organization("Siemens AG", "http://www.siemens.ru/",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(2005, 1, 1),
                                                LocalDate.of(2005, 4, 1),
                                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                                "")))),
                        new Organization("Alcatel", "http://www.alcatel.ru/",
                                new ArrayList<>(List.of(
                                        new Period(LocalDate.of(1997, 9, 1),
                                                LocalDate.of(1998, 3, 1),
                                                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                                ""))))
                )));
        sections.put(EDUCATION, education);

        resume.setSections(sections);
        System.out.println(resume);
    }
}