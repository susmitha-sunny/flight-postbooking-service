package com.gotravel.flightpostbookingservice.controller;

import com.gotravel.flightpostbookingservice.entity.Reservation;
import com.gotravel.flightpostbookingservice.exception.ValueNotFoundException;
import com.gotravel.flightpostbookingservice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

//    @GetMapping(value = "/triphistory/{emailId}")
//    public List<ReservationDetails> execute(@PathVariable final String emailId) throws ValueNotFoundException{
//        return tripService.getTripHistory(emailId);
//    }

    @CrossOrigin
    @GetMapping(value = "/triphistory")
    public List<Reservation> execute(@RequestParam(value = "email") final String email) throws ValueNotFoundException {
        return tripService.getTripHistory(email);
    }

//    @GetMapping(value = "/retrievepnr/{pnr}")
//    public ReservationDetails executeRetrievePnr(@PathVariable final String pnr) throws ValueNotFoundException {
//        return tripService.retrievePnr(pnr);
//    }

    @CrossOrigin
    @GetMapping(value = "/retrievepnr")
    public Reservation executeRetrievePnr(@RequestParam(value = "pnr") final String pnr) throws ValueNotFoundException {
        return tripService.retrievePnr(pnr);
    }

    @CrossOrigin
    @PutMapping(value = "/cancelreservation")
    public boolean executeCancelReservation(@RequestBody final Reservation reservation) throws ValueNotFoundException {
        return tripService.cancelReservation(reservation.getPnr());
    }

    @CrossOrigin
    @GetMapping(value = "/deleteAirline")
    public Boolean executeDelete() throws ValueNotFoundException {
        return tripService.deleteInactiveAirline();
    }
}
