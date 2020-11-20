package com.company.matcher.interfaces;

import com.company.matcher.classes.StringLocation;

import java.util.HashMap;
import java.util.List;

public interface Printer {
    void print(HashMap<String, List<StringLocation>> word2locations);
}
