package com.irctc.service;

import com.irctc.response.GenericResponse;

public interface TrainService {
    GenericResponse loadTrainData();
    GenericResponse findAllTrainData(int pageNo, int pageSize);
    GenericResponse searchTrain(String source, String destination);
}
