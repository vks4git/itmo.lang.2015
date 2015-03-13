package ru.ifmo.morozov.t05;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vks on 3/18/15.
 */
public class Visitor extends SimpleFileVisitor<Path> {

    private List<String> fileList;
    private Random random;

    public Visitor() {
        fileList = new ArrayList<String>();
        random = new Random();
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        fileList.add(file.toString());
        return FileVisitResult.CONTINUE;
    }

    public String getVictim() {
        int index = random.nextInt(fileList.size());
        return fileList.get(index);
    }

    public List<String> getFileList() {
        return fileList;
    }

}