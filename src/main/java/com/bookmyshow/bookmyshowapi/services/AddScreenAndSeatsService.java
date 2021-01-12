package com.bookmyshow.bookmyshowapi.services;

import com.bookmyshow.bookmyshowapi.models.Screen;
import com.bookmyshow.bookmyshowapi.models.Seat;
import com.bookmyshow.bookmyshowapi.models.Theatre;
import com.bookmyshow.bookmyshowapi.models.Tier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddScreenAndSeatsService {

    public List<Seat> addSeats(Tier tier,int row,int col){
        List<Seat> seats = new ArrayList<>();
        int n = 65;
        for(int i=0;i<row;i++){
            char ch = (char)(n+i);
            for(int j=1;j<=col;j++){
                String name = ch + "" + j;
                double price = tier.getMultiplier() * tier.getScreen().getTheatre().getBasePrice();
                Seat seat = new Seat(tier,name,false,null,price);
                seats.add(seat);
            }
        }
        return seats;
    }

    public List<Screen> addScreens(Theatre theatre,int num){
        List<Screen> screens = new ArrayList<>();
        for(int i=1;i<=num;i++){
            Screen screen = new Screen(theatre,(long)i);
            screens.add(screen);
        }
        return screens;
    }

}
