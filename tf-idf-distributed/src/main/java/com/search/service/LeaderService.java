package com.search.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.model.LeaderResult;
import com.search.model.Task;
import com.search.model.WorkerResult;
import com.search.network.WebClient;
import com.search.search.TFIDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class LeaderService {

    @Autowired
    Environment env;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    WebClient client;

    public static String PATH = "./resources/files/";

    public LeaderResult search(String term){

        List<String> workers = getWorkers();

        String[] fileNames = getTitles();

        List<Task>tasks = buildWorkerTasks(fileNames,workers,term);

        List<CompletableFuture<String >> futures = sendTasks(tasks);

        List<WorkerResult> results = callBack(futures);

        Map<String, Double> scores = tfIdfScores(term, results);

        System.out.println(String.format("Received %d/%d results", results.size(), workers.size()));
        LeaderResult result = new LeaderResult(term,scores);
        return result;
    }

    private Map<String, Double> tfIdfScores(String term, List<WorkerResult> results) {
        Map<String, Double> tfs = aggregateResults(results, term);
        double idf = TFIDF.getInverseDocumentFrequency(term,tfs);
        return tfs.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() * idf));
    }

    private List<WorkerResult> callBack(List<CompletableFuture<String>> futures) {
        List<WorkerResult> results = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (CompletableFuture<String> future : futures) {
            try {
                String response  = future.get();
                WorkerResult result = mapper.readValue(response, WorkerResult.class);
                results.add(result);
            } catch (InterruptedException | ExecutionException | IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private List<CompletableFuture<String>> sendTasks(List<Task> tasks) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for(Task task:tasks){
            futures.add(client.sendTask(task.getAddress() + "/frequencies", task));
        }
        return futures;
    }

    private List<Task> buildWorkerTasks(String[] fileNames,List<String> workers,String term) {

        List<String[]> fileNamesChunks = splitStringArray(fileNames,workers.size());
        List<Task> tasks = new ArrayList<>();
        for(int i=0;i<workers.size();i++){
            Task task = new Task(term,fileNamesChunks.get(i),workers.get(i));
            tasks.add(task);
        }
        return tasks;
    }

    private Map<String, Double> aggregateResults(List<WorkerResult> results, String query) {
        Map<String,Double> all = results.stream()
                .map(result -> result.getTermFrequency())
                .flatMap(freq -> freq.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return all;
    }

    private List<String[]> splitStringArray(String[] fileNames, int size) {
        int chunk=fileNames.length / size;//4
        List<String[]> partitions = new ArrayList<>();

        for(int i=0 ; i < size - 1 ; i+=chunk){
            partitions.add(Arrays.copyOfRange(fileNames, i, i + chunk));
        }

        partitions.add(Arrays.copyOfRange(fileNames, chunk * (size -1), fileNames.length));

        return partitions;
    }

    private String[] getTitles() {
        File file = new File(PATH);
        return file.list();
    }

    private List<String> getWorkers() {
        List<String> seviceAddreses = new ArrayList<>();
        List<ServiceInstance> list = discoveryClient.getInstances("application");
        if(list == null) return seviceAddreses;
        seviceAddreses = list
                .stream()
                .map(instance -> instance.getUri().toString())
                .filter(address -> !address.contains(env.getProperty("server.port")))
                .collect(Collectors.toList());

        return seviceAddreses;

    }
}
