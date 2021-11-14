package com.gotravel.flightpostbookingservice.repository;

import com.gotravel.flightpostbookingservice.entity.Reservation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    @Cacheable(value = "reservationCache", key = "#emailId")
    List<Reservation> findByEmailId(String emailId);

    @Cacheable(value = "reservationCache", key = "#pnr")
    Optional<Reservation> findByPnr(String pnr);

    @Modifying
    @Transactional
    @Query(value = "UPDATE reservation r SET r.booking_status = 'CANCELLED' WHERE r.pnr = ?1", nativeQuery = true)
    int cancelReservation(String pnr);
}
