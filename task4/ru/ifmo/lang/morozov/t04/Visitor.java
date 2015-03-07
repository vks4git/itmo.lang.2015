package ru.ifmo.lang.morozov.t04;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by vks on 3/7/15.
 */
public class Visitor implements FileVisitor<Path> {

    private String template;

    public Visitor(String template) {
        this.template = template;
    }

    private boolean matches(final String filename, final String template) {
        int length = template.length() - 1;
        if ((template.charAt(0) == '*') && (template.charAt(length) == '*')) {
            String substring = template.substring(1, length - 1);
            return filename.contains(substring);
        } else if (template.charAt(0) == '*') {
            String substring = template.substring(1, length + 1);
            return filename.endsWith(substring);
        } else if (template.charAt(length) == '*') {
            String substring = template.substring(0, length - 1);
            return filename.startsWith(substring);
        }
        return filename.compareTo(template) == 0;
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }


    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (matches(file.getFileName().toString(), template)) {
            Counter.size += Files.size(file);
        }
        return FileVisitResult.CONTINUE;
    }


    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return null;
    }


    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }
}
