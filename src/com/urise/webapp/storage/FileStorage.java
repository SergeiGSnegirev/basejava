package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final StreamSerializer serializer;

    protected FileStorage(File directory, StreamSerializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable");
        }
        if (!directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not writable");
        }
        this.directory = directory;
        this.serializer = serializer;
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getName(), resume.getUuid(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            serializer.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    @Override
    public void clear() {
        Arrays.stream(getFiles()).forEach(this::doDelete);
    }

    @Override
    protected List<Resume> getAsList() {
        return Arrays.stream(getFiles()).map(this::doGet).collect(Collectors.toList());
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        return files;
    }
}
