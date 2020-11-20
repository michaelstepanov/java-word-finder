# Java - Word Finder

A simple Java program to find specific strings in a large text.
The program is composed of the following modules:

* The main module - reads a large text file in parts (e.g. 1000 lines in each part) and sends each part (as string) to a matcher . After all matchers completed, it calls the aggregator to combine and print the results
* The matcher - gets a text string as input and searches for matches of a given set of strings. The result is a map from a word to its location(s) in the text
* The aggregator - aggregates the results from all the matchers and prints the results

The following requirements are met:

* There is several concurrent matchers (i.e each matcher runs in a separate thread)
* The results are printed (in no particular order) after all text pieces have been processed

Example of one line from the program output:
	
    Timothy --> [[lineOffset=13000, charOffset=19775], [lineOffset=13000, charOffset=42023]]