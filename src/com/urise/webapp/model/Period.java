package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Period period)) return false;

        return startDate.equals(period.startDate) && endDate.equals(period.endDate) &&
                title.equals(period.title) && description.equals(period.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return " %s - %s: %s%n%s%s%n".formatted(
                startDate.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                endDate.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                title, " ".repeat(20), description);
    }
}