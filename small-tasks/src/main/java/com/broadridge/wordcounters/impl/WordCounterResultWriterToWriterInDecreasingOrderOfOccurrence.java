package com.broadridge.wordcounters.impl;

import com.broadridge.wordcounters.WordCounterResultWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.broadridge.wordcounters.config.WordCounterConstants.DEFAULT_LINE_DELIMITER;

public class WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence implements WordCounterResultWriter {

    private final Writer writer;
    private final String lineDelimiter;

    private TreeMap<Long, List<String>> getDataForOutput(Map<String, Long> countWords) throws IOException {
        TreeMap<Long, List<String>> treeMap = new TreeMap<>(
                (o1, o2) -> Long.compare(o2, o1)
        );
        for (Map.Entry<String, Long> entry : countWords.entrySet()) {
            List<String> strings = treeMap.computeIfAbsent(entry.getValue(), aLong -> new ArrayList<>());
            strings.add(entry.getKey());
        }
        return treeMap;
    }

    private void write(TreeMap<Long, List<String>> dataForOutput) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Map.Entry<Long, List<String>> entry : dataForOutput.entrySet()) {
                for (String string : entry.getValue()) {
                    bufferedWriter.write(string + "," + entry.getKey() + lineDelimiter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(4);
        }
    }

    public WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence(Writer writer) {
        this(writer, DEFAULT_LINE_DELIMITER);
    }

    public WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence(Writer writer, String lineDelimiter) {
        this.writer = writer;
        this.lineDelimiter = lineDelimiter;
    }

    @Override
    public void write(Map<String, Long> countWords) throws IOException {
        write(getDataForOutput(countWords));
    }
}
