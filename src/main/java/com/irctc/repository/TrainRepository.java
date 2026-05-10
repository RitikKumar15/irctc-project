package com.irctc.repository;

import com.irctc.entities.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TrainRepository extends JpaRepository<TrainEntity, String> {

    @Query("SELECT t FROM train t WHERE t.source = :source AND t.destination = :destination")
    Optional<TrainEntity> searchTrain(String source, String destination);
}
