package com.rhaut.tfidf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TFIDF {

    public static void main(String[] args) {

        List<String> document1 = new ArrayList<>();
        document1.add("this");
        document1.add("is");
        document1.add("a");
        document1.add("a");
        document1.add("sample");
        document1.add("another");

        List<String> document2 = new ArrayList<>();
        document2.add("this");
        document2.add("is");
        document2.add("another");
        document2.add("another");
        document2.add("example");
        document2.add("example");
        document2.add("example");

        List<List<String>> documents = new ArrayList<>();
        documents.add(document1);
        documents.add(document2);

        printTable(documents);
    }

    public static double termFrequency(List<String> document, String term) {
        double termFrequency = 0;
        if (document != null && document.size() > 0) {
            double occurrences = document.stream().filter(word -> word.equals(term)).count();
            termFrequency = occurrences / document.size();
        }
        return termFrequency;
    }

    public static double inverseDocumentFrequency(List<List<String>> documents, String term) {
        double inverseDocumentFrequency = 0;
        if (documents != null) {
            double documentsContainingTerm = documents.stream().filter(document -> document != null && document.contains(term)).count();
            if (documentsContainingTerm != 0) {
                inverseDocumentFrequency = Math.log10(documents.size() / documentsContainingTerm);
            }
        }
        return inverseDocumentFrequency;
    }

    public static double calculate(List<List<String>> documents, List<String> document, String term) {
        return termFrequency(document, term) * inverseDocumentFrequency(documents, term);
    }

    public static void printTable(List<List<String>> documents) {
        if (documents != null) {
            List<String> allWords = documents.stream().flatMap(List::stream).distinct().collect(Collectors.toList());
            for (String term : allWords) {
                System.out.printf("%-12.12s", term);
                for (List<String> document : documents) {
                    System.out.printf("%-8.8f  ", TFIDF.calculate(documents, document, term));
                }
                System.out.println();
            }
        }
    }
}
