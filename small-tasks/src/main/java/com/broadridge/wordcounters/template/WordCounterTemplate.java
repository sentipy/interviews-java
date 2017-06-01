package com.broadridge.wordcounters.template;

import com.broadridge.wordcounters.WordCounter;
import com.broadridge.wordcounters.WordCounterResultWriter;

import java.io.IOException;
import java.util.stream.Stream;

public class WordCounterTemplate {

    private final WordCounter wordCounter;
    private final WordCounterResultWriter wordCounterResultWriter;

    public WordCounterTemplate(WordCounter wordCounter, WordCounterResultWriter wordCounterResultWriter) {
        this.wordCounter = wordCounter;
        this.wordCounterResultWriter = wordCounterResultWriter;
    }

    public void process(Stream<String> stringStream) throws IOException {
        wordCounterResultWriter
                .write(
                        wordCounter.countWords(stringStream)
                );
    }
}
