package com.pag.unit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkdownSplitter {

    public static List<String> splitMarkdownToParagraphs(String markdownText) {
        List<String> paragraphs = new ArrayList<>();
        String[] lines = markdownText.split("\n");
        StringBuilder paragraphBuilder = new StringBuilder();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                if (paragraphBuilder.length() > 0) {
                    paragraphs.add(paragraphBuilder.toString());
                    paragraphBuilder = new StringBuilder();
                }
            } else {
                paragraphBuilder.append(line).append("\n");
            }
        }

        if (paragraphBuilder.length() > 0) {
            paragraphs.add(paragraphBuilder.toString());
        }

        return paragraphs;
    }

    public static List<String> splitMarkdown(String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<String> paragraphs = new ArrayList<>();
        StringBuilder currentParagraph = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                if (currentParagraph.length() > 0) {
                    paragraphs.add(currentParagraph.toString());
                    currentParagraph.setLength(0);
                }
            } else {
                currentParagraph.append(line).append(System.lineSeparator());
            }
        }
        reader.close();

        if (currentParagraph.length() > 0) {
            paragraphs.add(currentParagraph.toString());
        }

        return paragraphs;
    }

}
