package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.PreBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreBookingRepository extends JpaRepository<PreBooking,Long> {
    Optional<PreBooking> findBySeatId(Long id);

    List<PreBooking> findAllByUserId(Long userId);
}
