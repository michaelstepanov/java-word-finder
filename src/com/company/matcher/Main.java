package com.company.matcher;

import com.company.matcher.classes.*;
import com.company.matcher.exceptions.ValidationException;
import com.company.matcher.interfaces.Printer;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        try {
            String fileName = "big.txt";
            FileDataProvider dataProvider = new FileDataProvider(fileName);
            HashSet<String> names = getNames();
            Printer printer = new ConsolePrinter();

            MatcherController controller = new MatcherController(dataProvider, names, printer, 1000);
            controller.process();
        } catch (FileNotFoundException | InterruptedException | ExecutionException | ValidationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a set of names.
     */
    private static HashSet<String> getNames() {
        String[] names = {
            "James",
            "John",
            "Robert",
            "Michael",
            "William",
            "David",
            "Richard",
            "Charles",
            "Joseph",
            "Thomas",
            "Christopher",
            "Daniel",
            "Paul",
            "Mark",
            "Donald",
            "George",
            "Kenneth",
            "Steven",
            "Edward",
            "Brian",
            "Ronald",
            "Anthony",
            "Kevin",
            "Jason",
            "Matthew",
            "Gary",
            "Timothy",
            "Jose",
            "Larry",
            "Jeffrey",
            "Frank",
            "Scott",
            "Eric",
            "Stephen",
            "Andrew",
            "Raymond",
            "Gregory",
            "Joshua",
            "Jerry",
            "Dennis",
            "Walter",
            "Patrick",
            "Peter",
            "Harold",
            "Douglas",
            "Henry",
            "Carl",
            "Arthur",
            "Ryan",
            "Roger"
        };

        return new HashSet<>(Arrays.asList(names));
    }
}
