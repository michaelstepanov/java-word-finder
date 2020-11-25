package com.company.matcher.classes;

import java.util.ArrayList;
import java.util.List;

public class StringLocation {
    private final int lineOffset;
    private final int charOffset;

    public StringLocation(int lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }

    /**
     * Casts a list of locations to a list of strings.
     */
    public static List<String> castLocationsToStringList(List<StringLocation> locations) {
        List<String> locationsStrings = new ArrayList<>();
        for (StringLocation location : locations) {
            locationsStrings.add(location.toString());
        }

        return locationsStrings;
    }
}
