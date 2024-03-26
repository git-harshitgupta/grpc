package org.example.sec05;

import com.harshit.models.sec05.Address;
import com.harshit.models.sec05.School;
import com.harshit.models.sec05.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Composition {
    private static final Logger log = LoggerFactory.getLogger(Composition.class);
    public static void main(String[] args) {
        var address = Address.newBuilder()
                .setCity("Durg")
                .setState("Chhattisgarh")
                .setStreet("Arya Nagar")
                .build();

        var student = Student.newBuilder()
                .setAddress(address)
                .setName("Harshit")
                .build();

        var school = School.newBuilder()
                .setId(1)
                .setName("DAV")
                .setAddress(address.toBuilder().
                        setStreet("Mohan Nagar"))
                .build();

        log.info("school - {}",school);
        log.info("student - {}",student);
    }



}
