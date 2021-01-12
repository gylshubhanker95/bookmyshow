package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.City;
import com.bookmyshow.bookmyshowapi.models.Location;
import com.bookmyshow.bookmyshowapi.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findByNameAndLocationName(String name, String locationName);

    List<Theatre> findAllByCity(City city);

}
