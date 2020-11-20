package com.company.matcher.classes;

import java.util.*;

public class Aggregator {
    private final List<HashMap<String, List<StringLocation>>> allChunkWord2locations;

    public Aggregator(List<HashMap<String, List<StringLocation>>> allChunkWord2locations) {
        this.allChunkWord2locations = allChunkWord2locations;
    }

    /**
     * Aggregates locations for a word.
     */
    public HashMap<String, List<StringLocation>> aggregate() {
        HashMap<String, List<StringLocation>> word2locations = new HashMap<>();
        // Iterate the allChunkWord2locations
        for (HashMap<String, List<StringLocation>> chunkWord2locations : allChunkWord2locations) {
            for (Map.Entry<String, List<StringLocation>> entry : chunkWord2locations.entrySet()) {
                String word = entry.getKey();
                List<StringLocation> locations = entry.getValue();

                // Aggregate data
                if (!word2locations.containsKey(word)) {
                    word2locations.put(word, locations);
                } else {
                    word2locations.get(word).addAll(locations);
                }
            }
        }

        return word2locations;
    }
}
