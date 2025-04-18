package com.urise.webapp.model;

import com.google.gson.annotations.JsonAdapter;
import com.urise.webapp.util.GsonLocalDateAdapter;
import com.urise.webapp.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String name, String url, Period... periods) {
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Organization(String name, String url, List<Period> periods) {
        this(new Link(name, url), periods);
    }

    public Organization(Link homePage, List<Period> periods) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.homePage = homePage;
        this.periods = periods;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Organization that)) return false;

        return getHomePage().equals(that.getHomePage()) && getPeriods().equals(that.getPeriods());
    }

    @Override
    public int hashCode() {
        int result = getHomePage().hashCode();
        result = 31 * result + getPeriods().hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder orgString = new StringBuilder("%s%s".formatted(" ".repeat(20), homePage));
        for (Period period : periods) {
            orgString.append(period.toString());
        }
        return orgString.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {

        @JsonAdapter(GsonLocalDateAdapter.class)
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @JsonAdapter(GsonLocalDateAdapter.class)
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Period() {
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public final boolean equals(Object object) {
            if (!(object instanceof Period period)) return false;

            return startDate.equals(period.startDate) && endDate.equals(period.endDate) &&
                    title.equals(period.title) && Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + Objects.hashCode(description);
            return result;
        }

        @Override
        public String toString() {
            return " %s - %s: %s%n%s".formatted(
                    startDate.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                    endDate.format(DateTimeFormatter.ofPattern("MM/yyyy")),
                    title,
                    description.isEmpty() ? "" : "%s%s%n".formatted(" ".repeat(20), description));
        }
    }
}