package com.search.search;

import com.search.model.WorkerResult;

import java.util.*;
import java.util.stream.Collectors;

public class TFIDF {

    // TF(t) = (Number of times term t appears in a document) / (Total number of terms in the document).
    public static double calculateTermFrequency(List<String> words, String term) {
        long count = 0;
        for (String word : words) {
            if (term.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return (double) count / words.size();
    }

    // IDF(t) = log_e(Total number of documents / Number of documents with term t in it).
    public static double getInverseDocumentFrequency(Map<String, Double> frequencies) {
        double n = 0;
        for (String title : frequencies.keySet()) {
            double termFrequency = frequencies.get(title);
            if (termFrequency > 0.0) {
                n++;
            }
        }
        return n == 0 ? 0 : Math.log10(frequencies.size() / n);
    }

    public static Map<String, Double> tfIdfScores(double idf, Map<String, Double> tfs) {
        return tfs.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * idf));
    }

}
