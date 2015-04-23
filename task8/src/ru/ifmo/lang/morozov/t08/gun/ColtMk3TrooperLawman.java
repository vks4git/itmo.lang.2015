package ru.ifmo.lang.morozov.t08.gun;

import java.io.IOException;
import java.nio.file.*;
import java.util.Random;

/**
 * Created by vks on 3/18/15.
 */
public class ColtMk3TrooperLawman implements Gun {

    private int bullets;
    private Random random;
    private String path;

    public ColtMk3TrooperLawman(String path, int bullets) {
        this.bullets = bullets;
        this.path = path;
        random = new Random();
    }

    public boolean fire() {
        float probability = (float) bullets / 6;
        float shoot = random.nextFloat();
        boolean success = (probability >= shoot);

        Path files = Paths.get(path);
        Visitor visitor = new Visitor();
        try {
            Files.walkFileTree(files, visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (visitor.getVictim() != null) {
                Path victim = Paths.get(visitor.getVictim());
                if (success) {
                    System.out.println("Today the glorious " + victim.getFileName().toString() + " abandoned us... Who'll be the next?");
                    Files.delete(victim);
                } else {
                    System.out.println("It was a flawless victory of virtue! Innocent " + victim.getFileName().toString() + " wasn't shot.");
                }
            } else {
                if (success) {
                    System.out.println("The place seemed empty. The bullet didn't reach it's aim.");
                } else {
                    System.out.println("There wasn't a file. There wasn't a shot. Nothing was.");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public void reload(int bullets) {
        this.bullets = bullets;
    }
}
