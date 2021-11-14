package com.gotravel.flightpostbookingservice.service;

import com.gotravel.flightpostbookingservice.entity.Reservation;
import com.gotravel.flightpostbookingservice.exception.ValueNotFoundException;
import com.gotravel.flightpostbookingservice.model.ReservationDetails;
import com.gotravel.flightpostbookingservice.repository.PassengerRepository;
import com.gotravel.flightpostbookingservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public List<ReservationDetails> getTripHistory(final String emailId) throws ValueNotFoundException{
        List<Reservation> reservationList = reservationRepository.findByEmailId(emailId);

        if(reservationList.isEmpty()) {
            throw new ValueNotFoundException("No reservations found for given email id");
        }
        return Optional.ofNullable(reservationList)
                .orElse(Collections.emptyList())
                .stream()
                .map(reservation -> buildReservationDetails(reservation))
                .collect(Collectors.toList());
    }

    private ReservationDetails buildReservationDetails(final Reservation reservation) {
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setReservation(reservation);
        reservationDetails.setPassengersList(passengerRepository.findByPnr(reservation.getPnr()));
        return reservationDetails;
    }

    public ReservationDetails retrievePnr(final String pnr) throws ValueNotFoundException {
        Optional<Reservation> reservation = reservationRepository.findByPnr(pnr);

        if (reservation.isPresent()) {
            ReservationDetails reservationDetails = new ReservationDetails();
            reservationDetails.setReservation(reservation.get());
            reservationDetails.setPassengersList(passengerRepository.findByPnr(pnr));
            return reservationDetails;
        }

        throw new ValueNotFoundException("No reservations found for given PNR.");
    }

    public String cancelReservation(final String pnr) throws ValueNotFoundException {
        if (reservationRepository.cancelReservation(pnr) >= 1) {
            return "Reservation cancelled successfully";
        }
        throw new ValueNotFoundException("Cancellation failed");
    }
}
