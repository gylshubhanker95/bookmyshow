package com.bookmyshow.bookmyshowapi.controllers;


import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        if(cityOptional.isPresent()){
            return "City is already presented";
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
        return cityRepository.findAll();
    }

    @GetMapping("/getCity/{cityName}")
    public City getCity(@PathVariable(name = "cityName") String name){
        Optional<City> city = cityRepository.findByName(name);
        if(city.isEmpty()){
            return null;
        }
        return city.get();
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    @PostMapping("/addTheatre")
    public String addTheatre(@RequestParam(name = "cityName") String cityName,
                             @RequestBody Theatre theatre,
                             @RequestParam (name = "longitude") String longitude,
                             @RequestParam(name = "latitude") String latitude){
        City city = getCity(cityName);
        if(city == null){
            return "Add city first";
        }
        Optional<Theatre> temp = theatreRepository.findByNameAndLocationName(theatre.getName(), theatre.getLocationName());
        if(temp.isPresent()){
            return "Theatre already added";
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
                city(city).
                name(theatre.getName()).
                basePrice(theatre.getBasePrice()).
                location(location).
                locationName(theatre.getLocationName()).build();
        theatreRepository.save(theatreObject);

        return theatreObject.getName()+" Theatre is added with id-"+theatreObject.getId() ;
    }

    @PostMapping("/addMovie")
    public String addMovie(@RequestBody Movie data){
        Optional<Movie> movie1 = movieRepository.findByMovieNameAndLanguage(data.getMovieName(), data.getLanguage());
        if(movie1.isPresent()){
            return "Movie already added";
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
