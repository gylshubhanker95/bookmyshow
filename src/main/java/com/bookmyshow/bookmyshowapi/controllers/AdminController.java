package com.bookmyshow.bookmyshowapi.controllers;


import com.bookmyshow.bookmyshowapi.exceptions.CityAlreadyPresentException;
import com.bookmyshow.bookmyshowapi.exceptions.CityNotFoundException;
import com.bookmyshow.bookmyshowapi.exceptions.MovieAlreadyPresentException;
import com.bookmyshow.bookmyshowapi.exceptions.TheatreAlreadyPresentException;
import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.repositories.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/addCity")
    public String addCity(@RequestBody City city){
        Optional<City> cityOptional = cityRepository.findByName(city.getName());
        log.info("Request Received to add new City with name - "+city.getName());
        if(cityOptional.isPresent()){
            throw new CityAlreadyPresentException("City with name - "+city.getName()+" is already present");
        }
        City cityObject = City.builder().
                name(city.getName()).
                state(city.getState()).
                country(city.getCountry()).
                build();

        cityRepository.save(cityObject);
        return "City "+cityObject.getName()+" with id "+cityObject.getId()+" is added";
    }

    @GetMapping("/getCities")
    public List<City> getCities(){
        log.info("Request received to get All Cities");
        return cityRepository.findAll();
    }

    @GetMapping("/getCity/{cityName}")
    public City getCity(@PathVariable(name = "cityName") String name){
        Optional<City> city = cityRepository.findByName(name);
        log.info("Request received to find City by Name - "+name);
        if(city.isEmpty()){
            throw new CityNotFoundException("No city with name - "+name+" found");
        }
        return city.get();
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        log.info("Request Received to get All Users");
        return users;
    }

    @PostMapping("/addTheatre")
    public String addTheatre(@RequestParam(name = "cityName") String cityName,
                             @RequestBody Theatre theatre,
                             @RequestParam (name = "longitude") String longitude,
                             @RequestParam(name = "latitude") String latitude){
        Optional<City> city = cityRepository.findByName(cityName);
        log.info("Request received to add new Theatre with name - "+theatre.getName()+" at "+theatre.getLocationName());
        if(city.isEmpty()){
            throw new CityNotFoundException("No City found with name - "+cityName);
        }
        Optional<Theatre> temp = theatreRepository.findByNameAndLocationName(theatre.getName(), theatre.getLocationName());
        if(temp.isPresent()){
            throw new TheatreAlreadyPresentException("Theatre Already Present");
        }
        Location location;
        Optional<Location> optionalLocation = locationRepository.findByLongitudeAndLatitude(longitude, latitude);
        if(optionalLocation.isPresent()){
            location = optionalLocation.get();
        }
        else{
            location = new Location(longitude,latitude);
            locationRepository.save(location);
        }
        Theatre theatreObject = Theatre.builder().
                city(city.get()).
                name(theatre.getName()).
                basePrice(theatre.getBasePrice()).
                location(location).
                locationName(theatre.getLocationName()).build();
        theatreRepository.save(theatreObject);

        return theatreObject.getName()+" Theatre is added with id-"+theatreObject.getId() ;
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestBody Movie data){
        log.info("Request Received to add new Movie - "+data.getMovieName()+"("+data.getLanguage()+")");
        Optional<Movie> movie1 = movieRepository.findByMovieNameAndLanguage(data.getMovieName(), data.getLanguage());
        if(movie1.isPresent()){
            throw new MovieAlreadyPresentException("Movie already present");
        }
        Movie movie = Movie.builder().
                movieName(data.getMovieName()).
                aboutMovie(data.getAboutMovie()).
                language(data.getLanguage()).
                certificateType(data.getCertificateType()).
                genre(data.getGenre()).build();

        movieRepository.save(movie);
        return "Movie with "+movie.getMovieName()+"("+movie.getLanguage()+") is added";
    }
}
