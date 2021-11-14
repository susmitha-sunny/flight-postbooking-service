package com.gotravel.flightpostbookingservice.model;

import com.gotravel.flightpostbookingservice.entity.Passenger;
import com.gotravel.flightpostbookingservice.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDetails {
    private Reservation reservation;
    private List<Passenger> passengersList;
}
