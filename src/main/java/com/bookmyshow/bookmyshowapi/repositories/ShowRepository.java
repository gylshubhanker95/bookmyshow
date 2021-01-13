package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findAllByCityIdAndMovieMovieName(Long cityId,String movieName);
    List<Show> findAllByCityIdAndIsAvailableForBooking(Long cityId,Boolean flag);
    List<Show> findAllByIsAvailableForBooking(Boolean flag);
}
