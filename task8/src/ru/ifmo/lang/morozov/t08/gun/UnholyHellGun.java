package ru.ifmo.lang.morozov.t08.gun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vks on 3/18/15.
 */
public class UnholyHellGun  implements Gun {

    //■ Если сегодня пятница тринадцатое, то Пистолет наполняется тёмной энергией, расстреливая все файлы в директории.
    //■ Если сегодня тринадцатое число, но не пятница, то энергии у Пистолета хватает только на половину файлов.
    //■ Если сегодня первое апреля, то Пистолет приобщается к празднику и не стреляет, а только заменяет
    // имена всех файлов на "pew-pew-pew, file N!",где N -- порядковый номер, под которым пистолету встретился файл.
    //■ Иначе Пистолет расстреливает все файлы, в размере которых нет цифр 1 и 3 одновременно.

    private Mode mode;
    private List<String> fileList;

    public UnholyHellGun(String path) {

        Calendar date = Calendar.getInstance();
        mode = Mode.STANDARD;
        if (date.get(Calendar.DAY_OF_MONTH) == 13) {
            if (date.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                mode = Mode.DARK;
            } else {
                mode = Mode.PARTIALLY_DARK;
            }
        } else if (date.get(Calendar.MONTH) == Calendar.APRIL) {
            if (date.get(Calendar.DAY_OF_MONTH) == 1) {
                mode = Mode.HOLIDAY;
            }
        }

        Path files = Paths.get(path);
        Visitor visitor = new Visitor();
        try {
            Files.walkFileTree(files, visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileList = visitor.getFileList();
    }

    public boolean fire() {
        switch (mode) {
            case DARK: {
                System.out.println("Friday 13th! DARK mode on!");
                for (int i = 0; i < fileList.size(); i++) {
                    Path file = Paths.get(fileList.get(i));
                    System.out.println(fileList.get(i) + " exterminated!");
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case PARTIALLY_DARK: {
                System.out.println("Though it's 13th day, it's not Friday...");
                for (int i = 0; i < (fileList.size() / 2); i++) {
                    Path file = Paths.get(fileList.get(i));
                    System.out.println(fileList.get(i) + " exterminated!");
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case HOLIDAY: {
                System.out.println("It's holiday now!");
                for (int i = 0; i < fileList.size(); i++) {
                    Path file = Paths.get(fileList.get(i));
                    System.out.println("pew-pew, " + fileList.get(i) + "!");
                    System.out.println(file.getParent());
                    String name = file.getParent().toString() + "/pew-pew-pew, file " + (i + 1) + "!";
                    try {
                        Files.move(file, Paths.get(name));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case STANDARD: {
                System.out.println("Beware, files without 1 and 3!");
                for (int i = 0; i < fileList.size(); i++) {
                    Path file = Paths.get(fileList.get(i));
                    try {
                        if (!passes(Files.size(file))) {
                            System.out.println("Found one! " + fileList.get(i));
                            Files.delete(file);
                        } else {
                            System.out.println("Now that's I call a good file! " + fileList.get(i));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return true;
    }

    private boolean passes(long n) {
        boolean one = false;
        boolean three = false;
        do {
            if (n % 10 == 1) {
                one = true;
            }
            if (n % 10 == 3) {
                three = true;
            }
            n /= 10;
        } while (n != 0);

        return (one && three);
    }
}
