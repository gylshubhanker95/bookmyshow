package com.bookmyshow.bookmyshowapi.controllers;

import com.bookmyshow.bookmyshowapi.exceptions.*;
import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.repositories.*;
import com.bookmyshow.bookmyshowapi.services.DisplaySeatsService;
import com.bookmyshow.bookmyshowapi.services.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DisplaySeatsService displaySeatsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreBookingRepository preBookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/getTheatres/{cityName}")
    public List<Theatre> getTheatresByCity(@PathVariable(name = "cityName") String name){
        Optional<City> city = cityRepository.findByName(name);
        log.info("Request received to get Theatres by City Name - "+name);
        if(city.isEmpty()){
            throw new CityNotFoundException("City with name - "+name+" not found");
        }

        return theatreRepository.findAllByCity(city.get());
    }

    @GetMapping("/getShows/{cityId}")
    public List<Show> getShows(@PathVariable(name = "cityId") Long cityId){
        List<Show> shows = showRepository.findAllByCityIdAndIsAvailableForBooking(cityId,true);
        log.info("Request received to get Shows by City Id - "+cityId);
        if(shows.isEmpty()){
            throw new ShowNotFoundException("Shows not Found");
        }
        return shows;
    }

    @GetMapping("/getShowsByMovieName/{cityId}")
    public List<Show> getShowsByMovieName(@PathVariable(name = "cityId") Long cityId,
                                          @RequestParam(name = "movieName") String movieName){
        List<Show> shows = showRepository.findAllByCityIdAndMovieMovieName(cityId,movieName);
        log.info("Request received to get Shows by City Id - "+cityId+" and Movie Name - "+movieName);
        if(shows.isEmpty()){
            throw new ShowNotFoundException("Shows not Found");
        }
        return shows;
    }

    @GetMapping("/getTheatre/{theatreId}")
    public Theatre getTheatre(@PathVariable(name = "theatreId") Long Id){
        Optional<Theatre> theatre = theatreRepository.findById(Id);
        log.info("Request received to get Theatre by Id - "+Id);
        if(theatre.isEmpty()){
            throw new TheatreNotFoundException("No Theatre Found with id - "+Id);
        }
        return theatre.get();
    }

    @GetMapping("/getSeats")
    public List<List<String>> getSeats(@PathVariable(name = "showId") Long showId){
        Optional<Show> show = showRepository.findById(showId);
        log.info("Request received to get Available Seats by Show Id - "+showId);
        if(show.isEmpty()){
            throw new ShowNotFoundException("Show with Id - "+showId + " not Found");
        }
        Long screenId = show.get().getScreen().getId();

        return displaySeatsService.displayAvailableSeats(screenId);
    }

    @PostMapping("/bookSeats")
    public String bookSeats(@PathVariable(name = "showId") Long showId,
                            @RequestParam(name = "mobile") String mobile,
                            @RequestBody List<Long> seatId){
        Optional<Show> show = showRepository.findById(showId);
        log.info("Request received to Book Seats for show Id - "+showId+" User with Mobile Number - "+mobile);
        if(show.isEmpty()){
            throw new ShowNotFoundException("Show with Id - "+showId + " not Found");
        }
        if(!show.get().getIsAvailableForBooking()){
            throw new ShowStartedException("Show has started. Tickets not available for Booking");
        }
        Optional<User> user = userRepository.findByMobile(mobile);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with mobile - "+mobile+" not Found");
        }
        List<PreBooking> preBookings = new ArrayList<>();

        for(Long id : seatId){
            Optional<PreBooking> preBooking = preBookingRepository.findBySeatId(id);
            if(preBooking.isPresent() || !seatRepository.findById(id).get().isBooked()){
                throw new SomethingWentWrongException("Something Went Wrong .Please try booking seat again");
            }
            PreBooking pre = PreBooking.builder().
                    seat(seatRepository.findById(id).get()).
                    user(user.get()).
                    show(show.get()).build();
            preBookings.add(pre);
        }
        preBookingRepository.saveAll(preBookings);

        return "Complete Payment to Book Seat";
    }

    @GetMapping("/payAmount/{userId}")
    public Ticket payAmount(@PathVariable(name = "userId") Long userId){
        Optional<User> user = userRepository.findById(userId);
        log.info("Request received to make a payment for User with Id - "+userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with Id - "+ userId +" not Found");
        }
        List<PreBooking> preBookings = preBookingRepository.findAllByUserId(userId);
        List<String> seats = new ArrayList<>();
        if(preBookings.isEmpty()){
            throw new SomethingWentWrongException("Something Went Wrong .Please try booking seat again");
        }


        int num = preBookings.size();
        double priceOfOneTicket = preBookings.get(0).getSeat().getSeatPrice();

        if(!paymentService.pay(num * priceOfOneTicket)){
            preBookingRepository.deleteAll(preBookings);
            throw new PaymentFailedException("Payment Failed");
        }

        for(PreBooking pre : preBookings){
            seats.add(pre.getSeat().getSeatName());
            pre.getSeat().setBooked(true);
        }
        Ticket ticket = Ticket.builder().
                movie(preBookings.get(0).getShow().getMovie()).
                amount(num * priceOfOneTicket).
                screen(preBookings.get(0).getShow().getScreen()).
                showTime(preBookings.get(0).getShow().getShowTime()).
                tierName(preBookings.get(0).getSeat().getTier().getName()).
                seats(seats).
                build();
        Booking booking = Booking.builder().
                user(user.get()).
                ticket(ticket).build();
        ticketRepository.save(ticket);
        preBookingRepository.deleteAll(preBookings);
        bookingRepository.save(booking);
        return ticket;
    }

    @GetMapping("/getBookings/{userId}")
    public List<Booking> getBookings(@PathVariable(name = "userId") Long userId){
        Optional<User> user = userRepository.findById(userId);
        log.info("Request received to get All Booking by User Id - "+userId);
        log.info("Request received to fetch all booking for id - "+userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User with Id - "+userId+" not found");
        }
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);

        return bookings;
    }

}
