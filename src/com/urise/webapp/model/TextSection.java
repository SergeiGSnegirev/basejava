package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    private final String body;

    public TextSection(String body) {
        Objects.requireNonNull(body, "content must not be null");
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof TextSection that)) return false;

        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return body.hashCode();
    }

    @Override
    public String toString() {
        return body + "\n";
    }
}