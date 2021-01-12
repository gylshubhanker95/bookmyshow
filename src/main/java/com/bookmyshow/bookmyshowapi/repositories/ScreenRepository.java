package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen,Long> {

    List<Screen> findAllByTheatreId(Long theatreId);

    Optional<Screen> findByTheatreIdAndScreenNumber(Long theatreId,Long screenNumber);
}
