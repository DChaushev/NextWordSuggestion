
import com.wordpredictor.NextWordPredictor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dimitar
 */
public class WordPredictionTest {

    @Test
    public void testOneGramsWithSmallCustomText() {
        NextWordPredictor predictor = new NextWordPredictor("test/smallCustomText");
        List<List<String>> input = new ArrayList<>(Arrays.asList(
                Arrays.asList(new String[]{"hello"}),
                Arrays.asList(new String[]{"please"}),
                Arrays.asList(new String[]{"close"}),
                Arrays.asList(new String[]{"it"})
        )
        );

        List<String> output = Arrays.asList(new String[]{"from", "close", "the", "was"});

        for (int i = 0; i < input.size(); i++) {
            assertEquals(predictor.nextWord(input.get(i)), output.get(i));
        }
    }

    @Test
    public void testTwoGramsWithSmallCustomText() {
        NextWordPredictor predictor = new NextWordPredictor("test/smallCustomText");
        List<List<String>> input = new ArrayList<>(Arrays.asList(
                Arrays.asList(new String[]{"hello", "my"}),
                Arrays.asList(new String[]{"please", "close"}),
                Arrays.asList(new String[]{"close", "the"}),
                Arrays.asList(new String[]{"it", "was"})
        )
        );

        List<String> output = Arrays.asList(new String[]{"name", "the", "door", "a"});

        for (int i = 0; i < input.size(); i++) {
            assertEquals(predictor.nextWord(input.get(i)), output.get(i));
        }
    }

    @Test
    public void testThreeGramsWithSmallCustomText() {
        NextWordPredictor predictor = new NextWordPredictor("test/smallCustomText");
        List<List<String>> input = new ArrayList<>(Arrays.asList(
                Arrays.asList(new String[]{"hello", "my", "name"}),
                Arrays.asList(new String[]{"please", "close", "the"}),
                Arrays.asList(new String[]{"close", "the", "door"}),
                Arrays.asList(new String[]{"it", "was", "a"})
        )
        );

        List<String> output = Arrays.asList(new String[]{"is", "door", "please", "good"});

        for (int i = 0; i < input.size(); i++) {
            assertEquals(predictor.nextWord(input.get(i)), output.get(i));
        }
    }

}
