package com.urise.webapp.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.Organization.Period;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestData {

    public static void main(String[] args) {

        System.out.println(fillTestResume("uuid1", "Александр Иванов"));
    }

    public static Resume fillTestResume(String uuid, String fullName) {

        Resume resume = new Resume(uuid, fullName);

        // fill CONTACTS
        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(MOBILE_PHONE, "+7 (911) 123-45-67");
        contacts.put(EMAIL, fullName.toLowerCase().replace(" ", ".") + "@gmail.com");
        contacts.put(SKYPE, fullName.toLowerCase().replace(" ", "."));
        contacts.put(TELEGRAM, "https://t.me/" + fullName.replace(" ", ""));
        contacts.put(LINKEDIN, "https://www.linkedin.com/in/" + fullName.toLowerCase().replace(" ", ""));
        contacts.put(GITHUB, "https://github.com/" + fullName.toLowerCase().replace(" ", "."));
        contacts.put(HOME_PAGE, "http://alexivanov.ru/");
        resume.setContacts(contacts);

        // fill SECTIONS
        Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

        // fill PERSONAL
        sections.put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность," +
                                               " инициативность. Пурист кода и архитектуры."));

        // fill OBJECTIVE
        sections.put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по" +
                                                " Java Web и Enterprise технологиям"));

        // fill ACHIEVEMENT
        sections.put(ACHIEVEMENT, new ListSection(List.of("Организация команды и успешная реализация Java проектов" +
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
        )));

        // fill QUALIFICATIONS
        sections.put(QUALIFICATIONS, new ListSection(List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat," +
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
        )));

        // fill EXPERIENCE
        sections.put(EXPERIENCE, new OrganizationSection(
                new ArrayList<>(List.of(new Organization("Java Online Projects", "http://javaops.ru/",
                                new ArrayList<>(List.of(
                                        new Period(2013, Month.OCTOBER,
                                                "Автор проекта.",
                                                "Создание, организация и проведение Java онлайн проектов и стажировок.")))),
                        new Organization("Wrike", "https://www.wrike.com/",
                                new ArrayList<>(List.of(
                                        new Period(2014, Month.OCTOBER,
                                                2016, Month.JANUARY,
                                                "Старший разработчик (backend)",
                                                "Проектирование и разработка онлайн платформы управления" +
                                                " проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava," +
                                                " Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация," +
                                                " авторизация по OAuth1, OAuth2, JWT SSO.")))),
                        new Organization("RIT Center", "",
                                new ArrayList<>(List.of(
                                        new Period(2012, Month.APRIL,
                                                2014, Month.OCTOBER,
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
                                        new Period(2010, Month.DECEMBER,
                                                2012, Month.APRIL,
                                                "Ведущий программист",
                                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring," +
                                                " Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и" +
                                                " серверной части CRM. Реализация RIA-приложения для администрирования," +
                                                " мониторинга и анализа результатов в области алгоритмического трейдинга." +
                                                " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."))))
                ))));

        // fill EDUCATION
        sections.put(EDUCATION, new OrganizationSection(
                new ArrayList<>(List.of(new Organization("Coursera", "https://www.coursera.org/course/progfun",
                                new ArrayList<>(List.of(
                                        new Period(2013, Month.MARCH,
                                                2013, Month.MAY,
                                                "'Functional Programming Principles in Scala' by Martin Odersky",
                                                "")))),
                        new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                                new ArrayList<>(List.of(
                                        new Period(2011, Month.MARCH,
                                                2011, Month.APRIL,
                                                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                                                "")))),
                        new Organization("Siemens AG", "http://www.siemens.ru/",
                                new ArrayList<>(List.of(
                                        new Period(2005, Month.JANUARY,
                                                2005, Month.APRIL,
                                                "3 месяца обучения мобильным IN сетям (Берлин)",
                                                "")))),
                        new Organization("Alcatel", "http://www.alcatel.ru/",
                                new ArrayList<>(List.of(
                                        new Period(1997, Month.SEPTEMBER,
                                                1998, Month.MARCH,
                                                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                                                ""))))
                ))));

        resume.setSections(sections);
        return resume;
    }
}