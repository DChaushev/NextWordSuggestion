package com.wordpredictor.preprocessing;

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
public class Preprocessor {

    private static final int DEFAULT_N_GRAMS = 3;

    public static void createNgrams(String fileName) {
        createNgrams(new File(fileName), DEFAULT_N_GRAMS);
    }

    public static void createNgrams(File file) {
        createNgrams(file, DEFAULT_N_GRAMS);
    }

    public static void createNgrams(String fileName, int ngrams) {
        createNgrams(new File(fileName), ngrams);
    }

    public static void createNgrams(File file, int ngrams) {
        List<Map<String, Integer>> ngramsList = initNgramsList(ngrams);

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            String[] wordsHolder = new String[ngrams];
            int wordIndex = 0;

            while (scanner.hasNext()) {
                String word = scanner.next();
                wordsHolder[wordIndex] = word;

                for (int i = 0; i < ngrams; i++) {
                    addNgram(ngramsList, wordsHolder, wordIndex, i);
                }

//                System.out.println(wordIndex);
//                for (int i = 0; i < ngrams; i++) {
//                    System.out.println(mod(wordIndex - i, ngrams) + ": " + wordsHolder[mod(wordIndex - i, ngrams)]);
//                }
//                System.out.println();
                wordIndex++;
                if (wordIndex == ngrams) {
                    wordIndex = 0;
                }
            }
            
            print(ngramsList);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int mod(int n, int m) {
        return (((n % m) + m) % m);
    }

    private static List<Map<String, Integer>> initNgramsList(int ngrams) {
        List<Map<String, Integer>> ngramsList = new ArrayList<>();
        for (int i = 0; i < ngrams; i++) {
            ngramsList.add(new HashMap<>());
        }
        return ngramsList;
    }

    public static void main(String[] args) {
        createNgrams("smallerText.txt");
    }

    private static void addNgram(List<Map<String, Integer>> ngramsList, String[] wordsHolder, int wordIndex, int i) {
        String currentWord = "";
        for (int j = 0; j < wordsHolder.length; j++) {
            currentWord = wordsHolder[mod(wordIndex - j, wordsHolder.length)] + " " + currentWord;
            addNgram(ngramsList.get(i), currentWord.trim());
        }
    }

    private static void addNgram(Map<String, Integer> ngramMap, String currentWord) {
        if (ngramMap.containsKey(currentWord)) {
            ngramMap.put(currentWord, ngramMap.get(currentWord) + 1);
        } else {
            ngramMap.put(currentWord, 1);
        }
    }

    private static void print(List<Map<String, Integer>> ngramsList) {
        for (Map<String, Integer> map : ngramsList) {
            for (String key : map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }
        }
    }

}
