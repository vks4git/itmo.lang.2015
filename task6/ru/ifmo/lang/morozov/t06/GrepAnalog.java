package ru.ifmo.lang.morozov.t06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vks on 3/22/15.
 */
public class GrepAnalog implements Grep {

    private BufferedReader reader;
    private Pattern pattern;
    private String string;
    private Matcher matcher;
    private List<String> strings;


    public GrepAnalog(InputStream stream, String regex) {
        reader = new BufferedReader(new InputStreamReader(stream));
        pattern = Pattern.compile(regex);
        strings = new ArrayList<String>();
    }

    public List<String> findLines(String regex) {
        try {
            while ((string = reader.readLine()) != null) {
                matcher = pattern.matcher(string);
                if (matcher.find()) {
                    strings.add(string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    public List<String> findParts(String regex) {
        try {
            while ((string = reader.readLine()) != null) {
                matcher = pattern.matcher(string);
                while (matcher.find()) {
                    strings.add(matcher.group());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings;
    }

    public List<String> findInvertMatch(String regex) {
        try {
            while ((string = reader.readLine()) != null) {
                matcher = pattern.matcher(string);
                if (!matcher.find()) {
                    strings.add(string);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
