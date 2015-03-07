package ru.ifmo.lang.morozov.t04;

import ru.ifmo.lang.morozov.t04.FileSizeCalculator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by vks on 3/7/15.
 */
public class SizeCalculator implements FileSizeCalculator {

    public long getSize(final String pathToDir, final String fileTemplate) {
        Path files = Paths.get(pathToDir);
        try {
            Files.walkFileTree(files, new Visitor(fileTemplate));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Counter.size;
    }
}