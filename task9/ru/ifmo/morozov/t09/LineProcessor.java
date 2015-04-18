package ru.ifmo.morozov.t09;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vks on 18/04/15.
 */
public class LineProcessor {
    public static void main(String[] args) {
        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);

        try {
            Stream<String> stream = Files.readAllLines(input).stream();
            for (int i = 2; i < args.length; i++) {
                switch (args[i]) {
                    case "sort": {
                        stream = stream.sorted();
                        break;
                    }
                    case "skip": {
                        int count = Integer.parseInt(args[i + 1]);
                        stream = stream.skip(count);
                        break;
                    }
                    case "limit": {
                        int count = Integer.parseInt(args[i + 1]);
                        stream = stream.limit(count);
                        break;
                    }
                    case "distinct": {
                        stream = stream.distinct();
                        break;
                    }
                    case "shuffle": {
                        List<String> list = stream.collect(Collectors.toList());
                        Collections.shuffle(list);
                        stream = list.stream();
                        break;
                    }
                    case "filter": {
                        String regex = args[i + 1];
                        Pattern pattern = Pattern.compile(regex);
                        stream = stream.filter(s -> pattern.matcher(s).find());
                        break;
                    }
                }
            }
            Files.write(output, stream.collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}