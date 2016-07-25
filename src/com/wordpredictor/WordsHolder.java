package com.wordpredictor;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a map of words to the number of their occurrences.
 *
 * @author Dimitar
 */
public class WordsHolder {

    private final Map<String, Integer> words;

    public WordsHolder() {
        words = new HashMap<>();
    }

    public void add(String word) {
        if (!words.containsKey(word)) {
            words.put(word, 1);
        } else {
            words.put(word, words.get(word) + 1);
        }
    }

    public String getMostCommon() {
        if (words == null) {
            return null;
        }
        int max = 0;
        String result = null;
        for (String key : words.keySet()) {
            if (words.get(key) > max) {
                result = key;
                max = words.get(key);
            }
        }
        return result;
    }

    // I am keeping this only for the print method in the preprocessor
    Map<String, Integer> getWords() {
        return words;
    }

}
