package com.irctc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity(name = "train")
@Table(name = "train")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TrainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "TRAIN_NO")
    private String trainNo;

    @Column(name = "TRAIN_NAME")
    private String trainName;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "TOTAL_SEATS")
    private Long totalSeats;

    @Column(name = "AVAILABLE_SEATS")
    private Long availableSeats;
}
