package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {

    private ArrayList<String> words;
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }
    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix == null || prefix.trim().equals("")){
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        }
        else{
            Comparator<String> comparator = new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    return lhs.substring(0, Math.min(rhs.length(),lhs.length())).compareToIgnoreCase(rhs);
                }
            };
            int index = Collections.binarySearch(words,prefix,comparator);
            if(index >= 0){
                return words.get(index);
            }
            else{
                return null;
            }
        }
    }
    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
