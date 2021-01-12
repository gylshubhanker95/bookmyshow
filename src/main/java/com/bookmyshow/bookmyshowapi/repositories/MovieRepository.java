package com.bookmyshow.bookmyshowapi.repositories;

import com.bookmyshow.bookmyshowapi.models.Movie;
import enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByMovieNameAndLanguage(String movieName, Language language);

    List<Movie> findAllByMovieName(String movieName);

}
