package com.urise.webapp.model;

import java.io.Serial;
import java.util.Objects;

public class TextSection extends Section {

    @Serial
    private static final long serialVersionUID = 1L;

    private String body;

    public TextSection() {
    }

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