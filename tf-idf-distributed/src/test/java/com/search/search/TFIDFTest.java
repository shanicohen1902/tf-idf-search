package com.search.search;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TFIDFTest extends TestCase {

    @Test
    public void testCalculateTermFrequency() {
        List<String> words = Arrays.asList("art", "love", "yoga");

        double frequency = TFIDF.calculateTermFrequency(words, "art");
        assertEquals((double)1/3,frequency);
    }

    @Test
    public void testGetInverseDocumentFrequency() {
        Map<String, Double> map = Map.of("title",2.8, "title2", 3.0,"title3",0.0);
        double idf = TFIDF.getInverseDocumentFrequency(map);
        double myIdf =  Math.log10((double)3 / 2);
        assertEquals(myIdf,idf);
    }

    public void testTfIdfScores() {
        Map<String, Double> map = Map.of("title",2.8, "title2", 3.0,"title3",0.0);
        double idf = TFIDF.getInverseDocumentFrequency(map);
        Map<String, Double> scores = TFIDF.tfIdfScores(idf, map);
        assertEquals(scores.get("title"),map.get("title") * idf);
    }
}