package com.broadridge.wordcounters;

import java.util.Map;
import java.util.stream.Stream;

public interface WordCounter {

    Map<String, Long> countWords(Stream<String> streamOfLines);
}
