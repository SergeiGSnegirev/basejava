package com.urise.webapp.model;

import java.time.Month;
import java.util.UUID;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.Organization.Period;
import static com.urise.webapp.model.SectionType.*;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");

        R1.addContact(EMAIL, "mail1@ya.ru");
        R1.addContact(MOBILE_PHONE, "11111");

        R4.addContact(MOBILE_PHONE, "44444");
        R4.addContact(SKYPE, "Skype");

        R1.addSection(OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(PERSONAL, new TextSection("Personal data"));
        R1.addSection(ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        R1.addSection(QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R1.addSection(EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Period(2005, Month.JANUARY, "position1", "content1"),
                                new Period(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Period(2015, Month.JANUARY, "position1", "content1"))));
        R1.addSection(EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Period(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Period(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));

        R2.addContact(SKYPE, "skype2");
        R2.addContact(MOBILE_PHONE, "22222");
    }
}
