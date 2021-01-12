package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findAllByUserId(Long userId);
}
