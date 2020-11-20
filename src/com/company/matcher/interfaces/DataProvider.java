package com.company.matcher.interfaces;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface DataProvider {
    InputStream getData() throws FileNotFoundException;
}
