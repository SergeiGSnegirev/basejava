package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof ListSection that)) return false;

        return items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder listSection = new StringBuilder();
        for (String item : items) {
            listSection.append("• %s%n".formatted(item));
        }
        return listSection.toString();
    }
}