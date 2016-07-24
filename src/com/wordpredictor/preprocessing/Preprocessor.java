package com.wordpredictor.preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            String[] wordsHolder = new String[ngrams];
            int wordIndex = 0;
            
            while (scanner.hasNext()) {
                String word = scanner.next();
                wordsHolder[wordIndex % ngrams] = word;
                
                System.out.println(wordIndex);
                for (int i = 0; i < ngrams; i++) {
                    System.out.println(Math.abs((wordIndex - i) % ngrams) + ": " + wordsHolder[Math.abs((wordIndex - i) % ngrams)]);
                }
                System.out.println();
                
                wordIndex++;
                if(wordIndex == ngrams) wordIndex = 0;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        createNgrams("smallerText.txt");
    }

}
