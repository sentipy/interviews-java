package com.broadridge.wordcounters.template;

import com.broadridge.wordcounters.config.WordCounterConstants;
import com.broadridge.wordcounters.impl.SimpleWordCounter;
import com.broadridge.wordcounters.impl.WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTemplateTest {

    private WordCounterTemplate wordCounterTemplate;
    private StringWriter stringWriter;

    @BeforeEach
    void setUp() {
        SimpleWordCounter simpleWordCounter = new SimpleWordCounter();
        stringWriter = new StringWriter();
        WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence resultWriter
                = new WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence(stringWriter);
        wordCounterTemplate = new WordCounterTemplate(simpleWordCounter, resultWriter);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void processNoData() throws IOException {
        wordCounterTemplate.process(null);
        String result = stringWriter.getBuffer().toString();
        assertEquals(0, result.length());
    }

    @Test
    void process1Line() throws IOException {
        wordCounterTemplate.process(Stream.of("a b a c c a"));
        String result = stringWriter.getBuffer().toString().trim();
        String[] split = result.split(WordCounterConstants.DEFAULT_LINE_DELIMITER);
        assertEquals(3, split.length);
        Set<String> set = new HashSet<>(Arrays.asList(split));
        assertEquals(3, set.size());
        assertTrue(set.contains("a,3"), "Expected to have \"a,3\" in the result");
        assertTrue(set.contains("c,2"), "Expected to have \"c,2\" in the result");
        assertTrue(set.contains("b,1"), "Expected to have \"b,1\" in the result");
    }

    @Test
    void process2Lines() throws IOException {
        wordCounterTemplate.process(Stream.of("a b a c c a", "a b a c d"));
        String dld = WordCounterConstants.DEFAULT_LINE_DELIMITER;
        String result = stringWriter.getBuffer().toString().trim();
        assertEquals(String.join(dld, "a,5", "c,3", "b,2", "d,1"), result);
        String[] split = result.split(dld);
        assertEquals(4, split.length);
        Set<String> set = new HashSet<>(Arrays.asList(split));
        assertEquals(4, set.size());
        assertTrue(set.contains("a,5"), "Expected to have \"a,5\" in the result");
        assertTrue(set.contains("c,3"), "Expected to have \"c,3\" in the result");
        assertTrue(set.contains("b,2"), "Expected to have \"b,2\" in the result");
        assertTrue(set.contains("d,1"), "Expected to have \"d,1\" in the result");
    }

    @Test
    void processComplex() throws IOException {
        wordCounterTemplate.process(
                Stream.of("I need to do this task", "I really need to do this task", "really really", "or maybe not")
        );
        String dld = WordCounterConstants.DEFAULT_LINE_DELIMITER;
        String result = stringWriter.getBuffer().toString().trim();
        String[] split = result.split(dld);
        assertEquals("really,3", split[0], "First element expected to be \"really,3\"");
        assertEquals(10, split.length);
        Set<String> set = new HashSet<>(Arrays.asList(split));
        assertEquals(10, set.size());

    }

}