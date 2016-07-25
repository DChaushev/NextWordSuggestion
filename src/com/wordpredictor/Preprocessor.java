package com.wordpredictor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitar
 */
class Preprocessor {

    private static final String SPACE = " ";
    private static final int DEFAULT_N_GRAMS = 4;

    static List<Map<String, WordsHolder>> createNgrams(String fileName) {
        return createNgrams(new File(fileName), DEFAULT_N_GRAMS);
    }

    static List<Map<String, WordsHolder>> createNgrams(File file) {
        return createNgrams(file, DEFAULT_N_GRAMS);
    }

    static List<Map<String, WordsHolder>> createNgrams(String fileName, int ngrams) {
        return createNgrams(new File(fileName), ngrams);
    }

    /**
     * Receives a text file as a parameter and number of n-gram types that it
     * should create.
     *
     * Creates list of maps. Each map maps string(n-gram) to a structure,
     * holding the next occurring words with the count of their appearance.
     *
     * <pre>
     * Examples:
     *  1-grams:
     *      without
     *          the: 2
     *          his: 1
     *      hall
     *          of: 1
     *
     *  2-grams:
     *      on the
     *          other: 1
     *          streets: 2
     *          street: 2
     *          wall: 1
     *      without the
     *          luxuries: 2
     *      was a:
     *          bit: 2
     *          good: 4
     *          total: 1
     *
     *  3-grams:
     *      plot is very:
     *          engaging: 2
     *      and the scene
     *          where: 3
     *      i wish i:
     *          had: 2
     * </pre>
     *
     * @param file
     * @param ngrams
     * @return
     */
    static List<Map<String, WordsHolder>> createNgrams(File file, int ngrams) {
        List<Map<String, WordsHolder>> ngramsList = initNgramsList(ngrams - 1);

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            String[] wordsHolder = new String[ngrams];
            int wordIndex = 0;
            boolean start = false;
            int counter = 1;

            while (scanner.hasNext()) {
                String word = scanner.next();
                wordsHolder[wordIndex] = word;

                if (start) {
                    addNgram(ngramsList, wordsHolder, wordIndex);
                }

                wordIndex++;
                if (wordIndex == ngrams) {
                    wordIndex = 0;
                }
                if (!start) {
                    counter++;
                    if (counter == ngrams) {
                        start = true;
                    }
                }
            }

            print(ngramsList);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ngramsList;
    }

    private static int mod(int n, int m) {
        return (((n % m) + m) % m);
    }

    private static List<Map<String, WordsHolder>> initNgramsList(int ngrams) {
        List<Map<String, WordsHolder>> ngramsList = new ArrayList<>();
        for (int i = 0; i < ngrams; i++) {
            ngramsList.add(new HashMap<>());
        }
        return ngramsList;
    }

    private static void addNgram(List<Map<String, WordsHolder>> ngramsList, String[] wordsHolder, int wordIndex) {
        StringBuilder currentWord = new StringBuilder();
        for (int j = wordsHolder.length - 1; j >= 0; j--) {
            if (currentWord.length() != 0) {
                addNgram(ngramsList.get(wordsHolder.length - j - 2), currentWord.toString().trim(), wordsHolder[mod(wordIndex - j, wordsHolder.length)]);
            }
            currentWord.append(SPACE).append(wordsHolder[mod(wordIndex - j, wordsHolder.length)]);
        }
    }

    private static void addNgram(Map<String, WordsHolder> ngramMap, String currentWord, String lastWord) {
        if (!ngramMap.containsKey(currentWord)) {
            ngramMap.put(currentWord, new WordsHolder());
        }
        ngramMap.get(currentWord).add(lastWord);
    }

    private static void print(List<Map<String, WordsHolder>> ngramsList) {
        for (Map<String, WordsHolder> map : ngramsList) {
            for (String key : map.keySet()) {
                System.out.println(key);
                for (String m : map.get(key).getWords().keySet()) {
                    System.out.println("    " + m + ": " + map.get(key).getWords().get(m));
                }
            }
        }
    }

}
