package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final ObjectStreamSerializer serializer;

    protected PathStorage(String directory, ObjectStreamSerializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = Paths.get(directory);
        this.serializer = serializer;
        if (!Files.isDirectory(this.directory)) {
            throw new IllegalArgumentException(directory + " is not а directory");
        }
        if (!Files.isReadable(this.directory)) {
            throw new IllegalArgumentException(directory + "  is not readable");
        }
        if (!Files.isWritable(this.directory)) {
            throw new IllegalArgumentException(directory + " is not writable");
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Path file, Resume resume) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file, resume.getUuid(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(Path file, Resume resume) {
        try {
            serializer.doWrite(new BufferedOutputStream(Files.newOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", file.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File delete error", file.toString(), e);
        }
    }

    @Override
    public int size() {
        return (int) getFiles().count();
    }

    @Override
    public void clear() {
        getFiles().forEach(this::doDelete);
    }

    private Stream<Path> getFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }

    @Override
    protected List<Resume> getAsList() {
        return getFiles().map(this::doGet).collect(Collectors.toList());
    }
}