package org.example.sec05;

import com.harshit.models.sec05.BodyStyle;
import com.harshit.models.sec05.Car;
import com.harshit.models.sec05.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Map {

    private static final Logger log = LoggerFactory.getLogger(Map.class);

    public static void main(String[] args) {
        var car1 = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2000)
                .setBodyStyle(BodyStyle.COUPE)
                .build();

        var car2 = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2002)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        var dealer = Dealer.newBuilder()
                .putInventory(car1.getYear(),car1)
                .putInventory(car2.getYear() ,car2)
                .build();

        log.info("{}",dealer);

        log.info("2002 ? : {}",dealer.containsInventory(2002));
        log.info("2003 ? : {}",dealer.containsInventory(2003));
        log.info("2002 model : {}",dealer.getInventoryOrDefault(2004,car1));
    }
}
