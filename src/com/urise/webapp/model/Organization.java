package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization {

    private final String name;
    private final String website;
    private final List<Period> periods;

    public Organization(String name, String website, List<Period> periods) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(periods, "periods must not be null");
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Organization that)) return false;

        return name.equals(that.name) && website.equals(that.website) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + website.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder orgString = new StringBuilder("%s%s: %s%n".formatted(" ".repeat(20), name, website));
        for (Period period : periods) {
            orgString.append(period.toString());
        }
        return orgString.toString();
    }
}