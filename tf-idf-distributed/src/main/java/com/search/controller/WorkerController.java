package com.search.controller;

import com.search.model.Task;
import com.search.model.WorkerResult;
import com.search.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkerController {

    @Autowired
    WorkerService workerService;


    @PostMapping("/frequencies")
    public WorkerResult getFrequencies(@RequestBody Task task) {
        return workerService.search(task.getTitles(),task.getTerm());
    }
}
