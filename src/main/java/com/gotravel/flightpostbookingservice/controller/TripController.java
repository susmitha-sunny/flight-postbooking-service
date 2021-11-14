package com.gotravel.flightpostbookingservice.controller;

import com.gotravel.flightpostbookingservice.exception.ValueNotFoundException;
import com.gotravel.flightpostbookingservice.model.ReservationDetails;
import com.gotravel.flightpostbookingservice.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @GetMapping(value = "/flight-postbooking-service/triphistory/{emailId}")
    public List<ReservationDetails> execute(@PathVariable final String emailId) throws ValueNotFoundException{
        return tripService.getTripHistory(emailId);
    }

    @GetMapping(value = "/flight-postbooking-service/retrievepnr/{pnr}")
    public ReservationDetails executeRetrievePnr(@PathVariable final String pnr) throws ValueNotFoundException {
        return tripService.retrievePnr(pnr);
    }

    @PutMapping(value = "/flight-postbooking-service/cancelreservation/{pnr}")
    public String executeCancelReservation(@PathVariable final String pnr) throws ValueNotFoundException {
        return tripService.cancelReservation(pnr);
    }
}
