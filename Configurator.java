/**
 * Created by vks on 2/21/15.
 */
public class Configurator implements FileSplitter.SplitConfig {

    private String input;
    private String odd;
    private String even;

    public Configurator(String input, String odd, String even) {
        this.input = input;
        this.odd = odd;
        this.even = even;
    }

    public String getSourceFilePath() {
        return input;
    }

    public String getOddLinesFilePath() {
        return odd;
    }

    public String getEvenLinesFilePath() {
        return even;
    }
}