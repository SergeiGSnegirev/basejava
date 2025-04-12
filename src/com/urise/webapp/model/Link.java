package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "%s: %s%n".formatted(name, url);
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Link link)) return false;

        return getName().equals(link.getName()) && Objects.equals(getUrl(), link.getUrl());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + Objects.hashCode(getUrl());
        return result;
    }

}
