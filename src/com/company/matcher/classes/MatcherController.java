package com.company.matcher.classes;

import com.company.matcher.exceptions.ValidationException;
import com.company.matcher.interfaces.DataProvider;
import com.company.matcher.interfaces.Printer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class MatcherController {
    private final Integer chunkSize;
    private final HashSet<String> dictionary;
    private final DataProvider dataProvider;
    private final Printer printer;

    public MatcherController(DataProvider dataProvider, HashSet<String> dictionary, Printer printer, Integer chunkSize) throws ValidationException {
        this.dataProvider = dataProvider;
        this.dictionary = dictionary;
        this.printer = printer;
        this.chunkSize = chunkSize;

        // Validate the entity
        validate();
    }

    /**
     * Validates the instance.
     */
    private void validate() throws ValidationException {
        validateChunkSize();
    }

    /**
     * Validates chunkSize property.
     */
    private void validateChunkSize() throws ValidationException {
        if (chunkSize <= 0) {
            throw new ValidationException();
        }
    }

    /**
     * Runs matching.
     */
    public void process() throws FileNotFoundException, InterruptedException, ExecutionException {
        // Create a thread pool
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(processors);

        // Match words
        List<Future<HashMap<String, List<StringLocation>>>> futures = matchWords(service, dataProvider);

        // Initiate shutdown
        service.shutdown();

        // Block until all tasks are completed or timeout occurs
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        // Aggregate the data
        List<HashMap<String, List<StringLocation>>> allChunkWord2locations = getAllChunkWord2locations(futures);
        Aggregator aggregator = new Aggregator(allChunkWord2locations);
        HashMap<String, List<StringLocation>> word2locations = aggregator.aggregate();

        // Print
        printer.print(word2locations);
    }

    /**
     * Gets locations for all chunks' words.
     */
    private List<HashMap<String, List<StringLocation>>> getAllChunkWord2locations(List<Future<HashMap<String, List<StringLocation>>>> futures) throws ExecutionException, InterruptedException {
        List<HashMap<String, List<StringLocation>>> allChunkWord2locations = new ArrayList<>();

        for (Future<HashMap<String, List<StringLocation>>> future : futures) {
            allChunkWord2locations.add(future.get());
        }

        return allChunkWord2locations;
    }

    /**
     * Matches words.
     */
    private List<Future<HashMap<String, List<StringLocation>>>> matchWords(ExecutorService service, DataProvider dataProvider) {
        InputStream inputStream = dataProvider.getData();
        Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);

        int chunkNumber = 0;
        int lineInChunk = 0;
        StringBuilder chunk = new StringBuilder();
        List<Future<HashMap<String, List<StringLocation>>>> futures = new ArrayList<>();
        while (sc.hasNextLine()) {
            lineInChunk++;

            String line = sc.nextLine();

            chunk.append(line);
            chunk.append(System.lineSeparator());

            if (lineInChunk == chunkSize) {
                Matcher matcher = new Matcher(chunk.toString(), dictionary, chunkNumber * chunkSize);

                Future<HashMap<String, List<StringLocation>>> future = service.submit(matcher);
                futures.add(future);

                lineInChunk = 0;
                chunkNumber++;

                // Empty the chunk
                chunk.setLength(0);
            }
        }

        return futures;
    }
}
