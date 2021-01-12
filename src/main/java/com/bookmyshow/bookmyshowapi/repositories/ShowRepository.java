package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.City;
import com.bookmyshow.bookmyshowapi.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show,Long> {
    List<Show> findAllByCityIdAndMovieMovieName(Long cityId,String movieName);
    List<Show> findAllByCityId(Long cityId);
}
