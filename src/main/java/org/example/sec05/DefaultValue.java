package org.example.sec05;

import com.harshit.models.sec05.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultValue {
    private static final Logger log = LoggerFactory.getLogger(DefaultValue.class);

    public static void main(String[] args) {
        var school = School.newBuilder().build();
        log.info("{}",school.getId());
        log.info("{}",school.getName());
        log.info("{}",school.getAddress().getCity());

        log.info("is default? : {}",school.getAddress().equals(Address.getDefaultInstance()));
        log.info("has address? : {}",school.hasAddress());

        var lib = Library.newBuilder().build();
        log.info("{}",lib.getBooksList());

        var dealer = Dealer.newBuilder().build();
        log.info("{}",dealer.getInventoryMap());

        var car = Car.newBuilder().build();
        log.info("{}",car.getBodyStyle());
    }
}
