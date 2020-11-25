package com.company.matcher.classes;

import java.util.*;
import java.util.concurrent.Callable;

public class Matcher implements Callable<HashMap<String, List<StringLocation>>> {
    private final String text;
    private final HashSet<String> dictionary;
    private final Integer initialLineOffset;

    public Matcher(String text, HashSet<String> dictionary, Integer initialLineOffset) {
        this.text = text;
        this.dictionary = dictionary;
        this.initialLineOffset = initialLineOffset;
    }

    @Override
    public HashMap<String, List<StringLocation>> call() {
        HashMap<String, List<StringLocation>> word2locations = new HashMap<>();

        Scanner sc = new Scanner(text);
        int lineOffset = 0;
        // Iterate through each line
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            StringBuilder word = new StringBuilder();
            // Iterate through each char on the line and build a word char by char
            int charOffset;
            for (charOffset = 0; charOffset < line.length(); charOffset++) {
                char c = line.charAt(charOffset);

                // If char is a letter
                if (Character.isLetter(c)) {
                    // Append the char to the word
                    word.append(c);
                } else {
                    collectWordIfMatches(word, word2locations, lineOffset, charOffset);

                    // Empty the word
                    word.setLength(0);
                }
            }

            collectWordIfMatches(word, word2locations, lineOffset, charOffset);

            lineOffset++;
        }

        return word2locations;
    }

    /**
     * Collects locations for a word.
     */
    private void collect(HashMap<String, List<StringLocation>> word2locations, String word, int lineOffset, int charOffset) {
        // Add the word location to the list of locations
        if (!word2locations.containsKey(word)) {
            List<StringLocation> locations = new ArrayList<>();

            word2locations.put(word, locations);
        }

        List<StringLocation> locations = word2locations.get(word);

        locations.add(new StringLocation(initialLineOffset + lineOffset + 1, charOffset + 1 - word.length()));
    }

    /**
     * Checks if a word matches.
     */
    private boolean wordMatches(String word) {
        // Matches case sensitive
        return word.length() > 0 && dictionary.contains(word);
    }

    /**
     * Collects locations for a word if the word matches.
     */
    private void collectWordIfMatches(StringBuilder word, HashMap<String, List<StringLocation>> word2locations, int lineOffset, int charOffset) {
        String wordString = word.toString();
        // If the word is not empty and the dictionary contains the word
        if (wordMatches(wordString)) {
            collect(word2locations, wordString, lineOffset, charOffset);
        }
    }
}
