package com.gotravel.flightpostbookingservice.repository;

import com.gotravel.flightpostbookingservice.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM airline a WHERE a.airline_id = ?1", nativeQuery = true)
    int deleteAirline(int airlineId);

    @Query(value = "SELECT * FROM airline a WHERE a.airline_status = 'INACTIVE'", nativeQuery = true)
    List<Airline> getInactiveAirlines();
}
