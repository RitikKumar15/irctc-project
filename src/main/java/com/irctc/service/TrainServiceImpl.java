package com.irctc.service;

import com.irctc.entities.TrainEntity;
import com.irctc.repository.TrainRepository;
import com.irctc.response.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public GenericResponse findAllTrainData(int pageNo, int pageSize) {
        Page<TrainEntity> trainData = trainRepository.findAll(PageRequest.of(pageNo, pageSize,
                Sort.by("trainName")));
        return new GenericResponse(HttpStatus.OK.value(), "success", "train data fetched successfully!!",
                trainData.getContent());
    }

    @Override
    public GenericResponse searchTrain(String source, String destination) {
        Optional<TrainEntity> optionalTrainEntity = trainRepository.searchTrain(source, destination);
        return optionalTrainEntity.map(trainEntity -> new GenericResponse(HttpStatus.OK.value(),
                "success", "data retrieved successfully!!", trainEntity))
                .orElseGet(() -> new GenericResponse(HttpStatus.OK.value(), "success",
                        "No match found!!", null));
    }

    @Override
    public GenericResponse loadTrainData() {
        TrainEntity delhiExpress = TrainEntity.builder()
                .trainName("Delhi Express")
                .availableSeats(5L)
                .totalSeats(5L)
                .source("Pune")
                .destination("Delhi")
                .startTime(new Date())
                .endTime(new Date())
                .build();

        TrainEntity puneExpress = TrainEntity.builder()
                .trainName("Pune Express")
                .availableSeats(3L)
                .totalSeats(5L)
                .source("Delhi")
                .destination("Pune")
                .startTime(new Date())
                .endTime(new Date())
                .build();

        Iterable<?> data = trainRepository.saveAll(List.of(delhiExpress, puneExpress));

        return new GenericResponse(HttpStatus.OK.value(), "success", "train Data loaded successfully", data);
    }
}
