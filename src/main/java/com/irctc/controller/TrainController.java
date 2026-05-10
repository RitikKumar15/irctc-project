package com.irctc.controller;

import com.irctc.response.GenericResponse;
import com.irctc.service.TrainService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/train")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping(value = "/loadData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> loadTrainData() {
        return ResponseEntity.ok(trainService.loadTrainData());
    }

    @GetMapping(value = "/getAllTrainData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> findAllTrainData(@RequestParam int pageNo, @RequestParam int pageSize) {
        return ResponseEntity.ok(trainService.findAllTrainData(pageNo, pageSize));
    }

    @GetMapping(value = "/searchTrain", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> searchTrain(@RequestParam String source, @RequestParam String destination) {
        return ResponseEntity.ok(trainService.searchTrain(source, destination));
    }
}
