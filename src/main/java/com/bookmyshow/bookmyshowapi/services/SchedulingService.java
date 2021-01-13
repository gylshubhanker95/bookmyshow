package com.bookmyshow.bookmyshowapi.services;

import com.bookmyshow.bookmyshowapi.models.PreBooking;
import com.bookmyshow.bookmyshowapi.models.Show;
import com.bookmyshow.bookmyshowapi.repositories.PreBookingRepository;
import com.bookmyshow.bookmyshowapi.repositories.ShowRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class SchedulingService {

    @Autowired
    private PreBookingRepository preBookingRepository;

    @Autowired
    private ShowRepository showRepository;

    private long tenMinutesInMilliSeconds = (long)(10*60*1000);

    private long thirtyMinutesInMilliSeconds = (long) (30*60*1000);


    @Scheduled(fixedRate = 5*1000)
    public void checkPreBookingTableForSessionTimeouts(){
        List<PreBooking> preBookings = preBookingRepository.findAll();
        List<PreBooking> sessionTimeOutBookings = new ArrayList<>();
        for (PreBooking preBooking: preBookings) {
            Date preBookingDate = preBooking.getCreatedAt();
            Date currentDate = new Date();
            long diffInMilliSeconds = currentDate.getTime() - preBookingDate.getTime();
            if(diffInMilliSeconds > tenMinutesInMilliSeconds){
                log.info("Deleting entry for user - "+preBooking.getUser().getId()+" from PreBooking due to timeout");
                sessionTimeOutBookings.add(preBooking);
            }
        }

        preBookingRepository.deleteAll(sessionTimeOutBookings);

    }

    @Scheduled(fixedRate = 5*60*1000)
    public void checkShowsWhichStarted(){
        List<Show> shows = showRepository.findAllByIsAvailableForBooking(true);
        for(Show show : shows){
            Date showStartingDate = show.getShowTime();
            Date currentDate = new Date();
            long diffInMilliSeconds = Math.abs(showStartingDate.getTime() - currentDate.getTime());
            if(diffInMilliSeconds > thirtyMinutesInMilliSeconds){
                log.info("Show with Id - "+show.getId()+" is starting in 30 minutes");
                show.setIsAvailableForBooking(false);
            }
        }
    }

}
