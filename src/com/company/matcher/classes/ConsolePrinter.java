package com.company.matcher.classes;

import com.company.matcher.interfaces.Printer;

import java.util.*;

public class ConsolePrinter implements Printer {
    /**
     * Prints word locations to console.
     */
    public void print(HashMap<String, List<StringLocation>> word2locations) {
        for (Map.Entry<String, List<StringLocation>> entry : word2locations.entrySet()) {
            String word = entry.getKey();
            List<StringLocation> locations = entry.getValue();

            System.out.print(word + " --> [");

            String joinedLocations = String.join(", ", StringLocation.castLocationsToStringList(locations));
            System.out.print(joinedLocations);

            System.out.print("]");
            System.out.println();
        }
    }
}
