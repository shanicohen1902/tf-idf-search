package com.search.service;

import com.search.model.WorkerResult;
import com.search.search.TFIDF;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WorkerService {

    public  WorkerResult search(String[] titles, String term) {

        Map<String,Double> frequencies = new HashMap<>();

        for(String title:titles){
            List<String> words = parseWordsFromDocument(title);
            frequencies.put(title,TFIDF.calculateTermFrequency(words,term));
        }

        return  new WorkerResult(term,frequencies);
    }

    private List<String> parseWordsFromDocument(String document) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(LeaderService.PATH + document);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return Collections.emptyList();
        }

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        List<String> words = parseWordsFromLines(lines);
        return words;
    }

    public static List<String> parseWordsFromLines(List<String> lines) {

        return lines.stream()
                .map(line -> getWordsFromLine(line))
                .flatMap(line -> line.stream())
                .collect(Collectors.toList()) ;
    }

    public static List<String> getWordsFromLine(String line) {
        return Arrays.asList(line.split("(\\.)+|(,)+|( )+|(-)+|(\\?)+|(!)+|(;)+|(:)+|(/d)+|(/n)+"));
    }
}
