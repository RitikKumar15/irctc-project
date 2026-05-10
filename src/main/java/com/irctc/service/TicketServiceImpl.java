package com.irctc.service;

import com.irctc.entities.TicketEntity;
import com.irctc.entities.TrainEntity;
import com.irctc.entities.UserEntity;
import com.irctc.repository.TicketRepository;
import com.irctc.repository.TrainRepository;
import com.irctc.repository.UserRepository;
import com.irctc.request.BookTicketRequest;
import com.irctc.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TrainRepository trainRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
        this.userRepository = userRepository;
    }

    @Override
    public GenericResponse bookTicket(BookTicketRequest bookTicketRequest) {
        Optional<TrainEntity> optionalTrainEntity = trainRepository.findById(bookTicketRequest.getTrainNo());
        if (optionalTrainEntity.isEmpty()) {
            return new GenericResponse(HttpStatus.OK.value(), "success", "Train doesn't exist!!", null);
        }
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailId(bookTicketRequest.getEmailId());
        if (optionalUserEntity.isEmpty()) {
            return new GenericResponse(HttpStatus.OK.value(), "success", "User doesn't exist!!", null);
        }
        TrainEntity train = optionalTrainEntity.get();
        if (train.getAvailableSeats() <= 0) {
            return new GenericResponse(HttpStatus.OK.value(), "success", "Selected train doesn't have available seats!!", null);
        }
        UserEntity user = optionalUserEntity.get();
        TicketEntity ticket = ticketRepository.saveAndFlush(TicketEntity.builder()
                .validFromDate(new Date())
                .validToDate(new Date())
                .train(train)
                .user(user)
                .build());
        List<TicketEntity> ticketsBooked = new ArrayList<>(Objects.nonNull(user.getBookedTickets())
                ? user.getBookedTickets() : new ArrayList<>());
        ticketsBooked.add(ticket);
        user.setBookedTickets(ticketsBooked);
        train.setAvailableSeats(train.getAvailableSeats() - 1L);
        return new GenericResponse(HttpStatus.OK.value(), "success", "Ticket booked successfully!!", ticket);
    }

    @Override
    public GenericResponse cancelTicket(String ticketNo) {
        Optional<TicketEntity> optionalTicketEntity = ticketRepository.findById(ticketNo);
        if (optionalTicketEntity.isEmpty()) {
            return new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "No such ticket available!!", null);
        }
        ticketRepository.delete(optionalTicketEntity.get());
        UserEntity userEntity = userRepository.findByEmailId(optionalTicketEntity.get().getUser()
                .getEmailId()).orElseThrow();
        List<TicketEntity> bookedTickets = userEntity.getBookedTickets();
        bookedTickets.remove(optionalTicketEntity.get());
        userEntity.setBookedTickets(bookedTickets);
        TrainEntity trainEntity = trainRepository.findById(optionalTicketEntity.get().getTrain()
                .getTrainNo()).orElseThrow();
        trainEntity.setAvailableSeats(trainEntity.getAvailableSeats() + 1L);
        return new GenericResponse(HttpStatus.OK.value(), "success", "ticket has cancelled successfully!!",
                optionalTicketEntity.get());
    }
}
