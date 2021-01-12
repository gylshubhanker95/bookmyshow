package com.bookmyshow.bookmyshowapi.services;

import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Tier;
import com.bookmyshow.bookmyshowapi.repositories.SeatRepository;
import com.bookmyshow.bookmyshowapi.repositories.TierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisplaySeatsService {

    @Autowired
    private TierRepository tierRepository;

    @Autowired
    private SeatRepository seatRepository;

    public List<List<String>> displayAvailableSeats(Long screenId){
        List<Tier> tiers = tierRepository.findAllByScreenId(screenId);
        List<List<String>> seats = new ArrayList<>();

        for(Tier tier : tiers){
            List<String> seat = new ArrayList<>();
            seat.add(tier.getName());
            List<Seat> seatList = seatRepository.findAllByTier(tier);
            for(Seat s : seatList){
                if (!s.isBooked()){
                    String str = s.getSeatName() + " " + s.isBooked() + " "+s.getSeatPrice();
                    seat.add(str);
                }
            }
            seats.add(new ArrayList<String>(seat));
        }

        return seats;
    }

}
