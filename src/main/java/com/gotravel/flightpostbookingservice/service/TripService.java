package com.gotravel.flightpostbookingservice.service;

import com.gotravel.flightpostbookingservice.entity.Airline;
import com.gotravel.flightpostbookingservice.entity.Reservation;
import com.gotravel.flightpostbookingservice.exception.ValueNotFoundException;
import com.gotravel.flightpostbookingservice.model.ReservationDetails;
import com.gotravel.flightpostbookingservice.repository.AirlineRepository;
import com.gotravel.flightpostbookingservice.repository.PassengerRepository;
import com.gotravel.flightpostbookingservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Reservation> getTripHistory(final String emailId) throws ValueNotFoundException {
        List<Reservation> reservationList = reservationRepository.findByEmailId(emailId);

        if (reservationList.isEmpty()) {
            throw new ValueNotFoundException("No reservations found for given email id");
        }

        Collections.sort(reservationList, Comparator.comparing(Reservation::getDepartureDate));
//        return Optional.ofNullable(reservationList)
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(reservation -> buildReservationDetails(reservation))
//                .collect(Collectors.toList());

        return reservationList;
    }

    private ReservationDetails buildReservationDetails(final Reservation reservation) {
        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setReservation(reservation);
        reservationDetails.setPassengersList(passengerRepository.findByPnr(reservation.getPnr()));
        return reservationDetails;
    }

    public Reservation retrievePnr(final String pnr) throws ValueNotFoundException {
        Optional<Reservation> reservation = reservationRepository.findByPnr(pnr);

        if (reservation.isPresent()) {
            return reservation.get();
//            ReservationDetails reservationDetails = new ReservationDetails();
//            reservationDetails.setReservation(reservation.get());
//            reservationDetails.setPassengersList(passengerRepository.findByPnr(pnr));
//            return reservationDetails;
        }

        throw new ValueNotFoundException("No reservations found for given PNR.");
    }

    public boolean cancelReservation(final String pnr) throws ValueNotFoundException {
        if (reservationRepository.cancelReservation(pnr) >= 1) {
            return true;
//            return "Reservation cancelled successfully";
        }
        throw new ValueNotFoundException("Cancellation failed");
    }

    public Boolean deleteInactiveAirline() {

        System.out.println("Inside delete airline from lambda");
        List<Airline> airlines = airlineRepository.getInactiveAirlines();

        if (airlines == null || airlines.isEmpty()) {
            return Boolean.FALSE;
        }
        Optional.ofNullable(airlines)
                .orElse(Collections.emptyList())
                .stream()
                .forEach(airline -> airlineRepository.deleteAirline(airline.getAirlineId()));

        return Boolean.TRUE;
    }
}
