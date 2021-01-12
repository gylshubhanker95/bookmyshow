package com.bookmyshow.bookmyshowapi.controllers;

import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.repositories.*;
import com.bookmyshow.bookmyshowapi.services.AddScreenAndSeatsService;
import enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/theatreAdmin")
public class TheatreAdminController {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private TierRepository tierRepository;

    @Autowired
    private AddScreenAndSeatsService addScreenAndSeatsService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private CityRepository cityRepository;

    @PostMapping("/addScreens/{theatreId}")
    public String addScreens(@PathVariable(name = "theatreId") Long theatreId,
                             @RequestParam int screenNumbers){
        Optional<Theatre> theatre = theatreRepository.findById(theatreId);
        if(theatre.isEmpty()){
            return "Theatre not added";
        }
        List<Screen> screens = addScreenAndSeatsService.addScreens(theatre.get(), screenNumbers);
        screenRepository.saveAll(screens);
        return screenNumbers+ " Screens added";
    }

    @PostMapping("/addTier/{screenId}")
    public String addTier(@PathVariable(name = "screenId")Long screenId,
                          @RequestBody Tier data){
        Optional<Screen> screen = screenRepository.findById(screenId);
        if(screen.isEmpty()){
            return "Screen not added";
        }
        Double multiplier = data.getMultiplier();
        multiplier = multiplier.max(multiplier,1);
        Tier tier = Tier.builder().
                screen(screen.get()).
                name(data.getName()).
                multiplier(multiplier).build();
        tierRepository.save(tier);
        return "Tier with id-"+tier.getId()+" is added";
    }

    @PostMapping("/addSeats/{tierId}")
    public String addSeats(@PathVariable(name = "tierId") Long tierId,
                           @RequestParam(name = "rows") int rows,
                           @RequestParam(name = "cols") int cols){
        Optional<Tier> tier = tierRepository.findById(tierId);
        if(tier.isEmpty()){
            return "Tier not added";
        }
        List<Seat> seats = addScreenAndSeatsService.addSeats(tier.get(),rows,cols);
        seatRepository.saveAll(seats);

        return rows*cols+" "+tier.get().getName()+" seats added";
    }

    @PostMapping("/addShow/{theatreId}")
    public String addShows(@PathVariable(name = "theatreId") Long theatreId,
                           @RequestParam(name = "cityId") Long cityId,
                           @RequestParam(name = "movieId") Long movieId,
                           @RequestParam(name = "screenNum") Long screenNum,
                           @RequestParam(name = "showDate") @DateTimeFormat(pattern = "yyyy/MM/dd,HH-mm-ss") Date showTiming){

        Optional<City> city = cityRepository.findById(cityId);
        if(city.isEmpty()){
            return "City not present";
        }

        Optional<Screen> screen = screenRepository.findByTheatreIdAndScreenNumber(theatreId,screenNum);
        if(screen.isEmpty()){
            return "Screen not present";
        }
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isEmpty()){
            return "Movie is not present";
        }
        Show show = Show.builder().showTime(showTiming).
                screen(screen.get()).
                city(city.get()).
                movie(movie.get()).build();

        showRepository.save(show);
        return "Show with timing "+showTiming+" is added";
    }

    @GetMapping("/getScreens/{theatreId}")
    public List<Screen> getScreens(@PathVariable(name = "theatreId") Long theatreId){
        return screenRepository.findAllByTheatreId(theatreId);
    }

}
