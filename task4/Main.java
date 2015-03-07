import ru.ifmo.lang.morozov.t04.FileSizeCalculator;
import ru.ifmo.lang.morozov.t04.SizeCalculator;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by vks on 3/7/15.
 */
public class Main {

    public static void main(String[] args) {
        SizeCalculator calculator = new SizeCalculator();
        System.out.println(calculator.getSize(args[0], args[1]));
    }
}
