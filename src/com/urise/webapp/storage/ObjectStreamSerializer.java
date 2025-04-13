package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectStreamSerializer {

    void doWrite(OutputStream outputStream, Resume resume) throws IOException;

    Resume doRead(InputStream inputStream) throws IOException;
}
