package com.broadridge.wordcounters;

import com.broadridge.wordcounters.impl.SimpleWordCounter;
import com.broadridge.wordcounters.impl.WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence;
import com.broadridge.wordcounters.template.WordCounterTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static void printHelp() {
        System.out.println("Expected 2 arguments to this program:");
        System.out.println("1st: source file");
        System.out.println("2nd: output file");
        System.out.println("Example: java -cp wordcounter.jar " + Main.class.getCanonicalName() + " source.txt output.txt");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            printHelp();
            System.exit(1);
        }

        String sourceFileName = args[0];
        if (Files.notExists(Paths.get(sourceFileName))) {
            System.out.println("Source file (" + sourceFileName + ") does not exists");
            System.exit(2);
        }

        String outputFileName = args[1];
        try(FileWriter fileWriter = new FileWriter(outputFileName)) {
            new WordCounterTemplate(
                    new SimpleWordCounter(),
                    new WordCounterResultWriterToWriterInDecreasingOrderOfOccurrence(fileWriter)
            ).process(Files.lines(Paths.get(sourceFileName)));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }

    }
}
