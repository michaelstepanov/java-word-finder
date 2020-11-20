package com.company.matcher.classes;

import com.company.matcher.interfaces.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileDataProvider implements DataProvider {
    private final String path;

    public FileDataProvider(String path) {
        this.path = path;
    }

    public InputStream getData() throws FileNotFoundException {
        File file = new File(path);
        return new FileInputStream(file);
    }
}