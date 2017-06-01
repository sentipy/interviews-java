package com.broadridge.wordcounters;

import java.io.IOException;
import java.util.Map;

public interface WordCounterResultWriter {

    void write(Map<String, Long> countWords) throws IOException;
}
