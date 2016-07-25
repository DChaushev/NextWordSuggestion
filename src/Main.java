
import com.wordpredictor.NextWordPredictor;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dimitar
 */
public class Main {

    private static final String FILE_NAME = "smallText.txt";
    private static final List<String> words = Arrays.asList(new String[]{"that", "are"});

    public static void main(String[] args) {
        // we assume that the text file already contains all lowercase filtered text
        NextWordPredictor predictor = new NextWordPredictor(FILE_NAME); // pass the text file
        System.out.println(predictor.nextWord(words)); // predict next word
    }

}
