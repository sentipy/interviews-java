package com.broadridge.wordcounters.impl;

import com.broadridge.wordcounters.WordCounter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class SimpleWordCounter implements WordCounter {

    @Override
    public Map<String, Long> countWords(Stream<String> streamOfLines) {
        if (streamOfLines == null) {
            return Collections.emptyMap();
        }
        Stream<String> words = streamOfLines.parallel().flatMap(s -> Arrays.stream(s.split(" ")));
        return words.collect(groupingBy(Function.identity(), counting()));
    }
}
