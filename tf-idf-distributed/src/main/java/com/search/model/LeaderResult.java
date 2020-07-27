package com.search.model;

import java.util.Map;

public class LeaderResult {

    private String term;

    private Map<String,Double> scores;

    public LeaderResult() { }

    public LeaderResult(String term, Map<String, Double> scores) {
        this.term = term;
        this.scores = scores;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }


}
