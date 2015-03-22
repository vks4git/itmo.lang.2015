import ru.ifmo.lang.morozov.t06.Grep;
import ru.ifmo.lang.morozov.t06.GrepAnalog;

import java.io.*;
import java.util.List;


/**
 * Created by vks on 3/22/15.
 */
public class SimpleGrepUtility {

    public static void main(String[] args) {

        int length = args.length;
        length--;
        String path = args[length];
        String regex = args[length - 1];
        length -= 2;

        boolean oKey = false;
        boolean vKey = false;

        for (int i = length; i >= 0; i--) {
            if (args[i].equals("-o")) {
                oKey = true;
            }
            if (args[i].equals("-v")) {
                vKey = true;
            }
        }


        List<String> result = null;

        try {
            InputStream stream = new FileInputStream(path);
            Grep grep = new GrepAnalog(stream, regex);
            if (!oKey && !vKey) {
                result = grep.findLines(regex);
            } else if (oKey && !vKey) {
                result = grep.findParts(regex);
            } else if (!oKey) {
                result = grep.findInvertMatch(regex);
            } else {
                System.out.println("Incorrect argument combination.");
            }
            if (result != null) {
                for (String string : result) {
                    System.out.println(string);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " not found!");
        }
    }

}
