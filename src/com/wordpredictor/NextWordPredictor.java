package com.wordpredictor;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dimitar
 */
public class NextWordPredictor {

    private static final String SPACE = " ";
    private List<Map<String, WordsHolder>> ngramsList;

    public NextWordPredictor(String fileName) {
        this(new File(fileName));
    }

    /**
     * Receives a training file for preprocessing
     *
     * @param file
     */
    public NextWordPredictor(File file) {
        ngramsList = Preprocessor.createNgrams(file);
    }

    /**
     * Predicts the next word based on a chain of words and the preprocessed
     * text.
     *
     * Uses only the last N words, N being the biggest n from the preprocessed
     * n-grams.
     *
     * @param words
     * @return
     */
    public String nextWord(List<String> words) {
        if (words == null || words.isEmpty()) {
            return null;
        }

        List<String> wordsList = words;
        if (words.size() > ngramsList.size()) {
            wordsList = words.subList(words.size() - ngramsList.size(), words.size());
        }

        int numberOfWords = wordsList.size();
        String lastWords = getLastNwords(numberOfWords, wordsList);

        WordsHolder nextWords = ngramsList.get(numberOfWords - 1).get(lastWords);
        while (nextWords == null && numberOfWords > 1) {
            lastWords = getLastNwords(--numberOfWords, wordsList);
            nextWords = ngramsList.get(numberOfWords - 1).get(lastWords);
        }
        if (nextWords == null) {
            return null;
        }

        return nextWords.getMostCommon();
    }

    /**
     * Given a list of words and number n, returns the last n words from the
     * list as a string, appended with a " ".
     *
     * If n > list.size, returns all words.
     *
     * @param numberOfWords
     * @param words
     * @return
     */
    private String getLastNwords(int numberOfWords, List<String> words) {
        if (words == null || words.isEmpty()) {
            return null;
        }
        if (numberOfWords > words.size()) {
            numberOfWords = words.size(); // return all words;
        }

        StringBuilder result = new StringBuilder();
        for (int i = words.size() - numberOfWords; i < words.size(); i++) {
            result.append(words.get(i)).append(SPACE);
        }
        return result.toString().trim();
    }
}
