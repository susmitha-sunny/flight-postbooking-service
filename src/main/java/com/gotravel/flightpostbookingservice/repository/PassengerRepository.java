package com.gotravel.flightpostbookingservice.repository;

import com.gotravel.flightpostbookingservice.entity.Passenger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    @Cacheable(value = "passengerCache", key = "#pnr")
    List<Passenger> findByPnr(String pnr);
}
