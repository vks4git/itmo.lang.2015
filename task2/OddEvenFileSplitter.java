import java.io.*;

/**
 * Created by vks on 2/21/15.
 */
public class OddEvenFileSplitter implements FileSplitter {

    public static void main(String[] args) {
        SplitConfig config = new Configurator(args[0], args[1], args[2]);
        FileSplitter splitter = new OddEvenFileSplitter();
        splitter.splitFile(config);
    }

    public void splitFile(SplitConfig config) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(config.getSourceFilePath()));
            BufferedWriter odd = new BufferedWriter(new FileWriter(config.getOddLinesFilePath()));
            BufferedWriter even = new BufferedWriter(new FileWriter(config.getEvenLinesFilePath()));
            String line = reader.readLine();
            int i = 0;
            while (line != null) {
                if (i % 2 == 1) {
                    even.write(line);
                    even.newLine();
                } else {
                    odd.write(line);
                    odd.newLine();
                }
                i++;
                line = reader.readLine();
            }
            reader.close();
            odd.close();
            even.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + config.getSourceFilePath() + " затерялся в недрах файловой системы либо не существует.");
        } catch (IOException e) {
            System.out.println("Случилось нечто непредвиденное во время работы с файлами. Используйте бубен." + e);
        }
    }

}
