package com.search.model;

import java.util.Map;

public class WorkerResult {

    public WorkerResult(String term, Map<String, Double> termFrequency) {
        this.term = term;
        this.termFrequency = termFrequency;
    }

    public WorkerResult() {
    }

    private String term;
    private Map<String,Double> termFrequency;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Map<String, Double> getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(Map<String, Double> termFrequency) {
        this.termFrequency = termFrequency;
    }

}
