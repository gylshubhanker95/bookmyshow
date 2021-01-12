package com.bookmyshow.bookmyshowapi.controllers;

import com.bookmyshow.bookmyshowapi.models.*;
import com.bookmyshow.bookmyshowapi.repositories.*;
import com.bookmyshow.bookmyshowapi.services.DisplaySeatsService;
import com.bookmyshow.bookmyshowapi.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if(city.isEmpty()){
            System.out.println("Wrong City Name");
            return null;
        }

        return theatreRepository.findAllByCity(city.get());
    }

    @GetMapping("/getShows/{cityId}")
    public List<Show> getShows(@PathVariable(name = "cityId") Long cityId){
        List<Show> shows = showRepository.findAllByCityId(cityId);
        if(shows.isEmpty()){
            return null;
        }
        return shows;
    }

    @GetMapping("/getShowsByMovieName/{cityId}")
    public List<Show> getShowsByMovieName(@PathVariable(name = "cityId") Long cityId,
                                          @RequestParam(name = "movieName") String movieName){
        List<Show> shows = showRepository.findAllByCityIdAndMovieMovieName(cityId,movieName);
        if(shows.isEmpty()){
            return null;
        }
        return shows;
    }

    @GetMapping("/getTheatre/{theatreId}")
    public Theatre getTheatre(@PathVariable(name = "theatreId") Long Id){
        Optional<Theatre> theatre = theatreRepository.findById(Id);
        if(theatre.isEmpty()){
            System.out.println("No Theatre Found");
            return null;
        }
        return theatre.get();
    }

    @GetMapping("/getSeats")
    public List<List<String>> getSeats(@PathVariable(name = "showId") Long showId){
        Optional<Show> show = showRepository.findById(showId);
        if(show.isEmpty()){
            return null;
        }
        Long screenId = show.get().getScreen().getId();

        return displaySeatsService.displayAvailableSeats(screenId);
    }

    @PostMapping("/bookSeats")
    public String bookSeats(@PathVariable(name = "showId") Long showId,
                            @RequestParam(name = "mobile") String mobile,
                            @RequestBody List<Long> seatId){
        Optional<Show> show = showRepository.findById(showId);
        if(show.isEmpty()){
            return "Wrong Show Id";
        }
        Optional<User> user = userRepository.findByMobile(mobile);
        if(user.isEmpty()){
            return "Register to Book";
        }
        List<PreBooking> preBookings = new ArrayList<>();

        for(Long id : seatId){
            Optional<PreBooking> preBooking = preBookingRepository.findBySeatId(id);
            if(preBooking.isPresent() || !seatRepository.findById(id).get().isBooked()){
                return "Something Went Wrong";
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
        if(user.isEmpty()){
            return null;
        }
        List<PreBooking> preBookings = preBookingRepository.findAllByUserId(userId);
        List<String> seats = new ArrayList<>();
        if(preBookings.isEmpty()){
            System.out.println("Session time out.Please book seat again");
            return null;
        }


        int num = preBookings.size();
        double priceOfOneTicket = preBookings.get(0).getSeat().getSeatPrice();

        if(!paymentService.pay(num * priceOfOneTicket)){
            System.out.println("Payment Failed");
            preBookingRepository.deleteAll(preBookings);
            return null;
        }

        for(PreBooking pre : preBookings){
            seats.add(pre.getSeat().getSeatName());
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
        if(user.isEmpty()){
            return null;
        }
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);

        return bookings;
    }

}
