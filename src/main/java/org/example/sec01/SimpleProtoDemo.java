package org.example.sec01;

import com.harshit.models.sec01.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SimpleProtoDemo {
    private static final Logger log =  LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {

        var person1 = createPerson();
        var person2 = createPerson();

        log.info("{}",person1.equals(person2));
        log.info("{}",person1==person2);

        Person mike = person1.toBuilder().setName("Mike").build();
        log.info("{}",mike==person1);

        var person4 = person1.toBuilder().clearName().build();
        log.info("{}",person4);
    }

    private static com.harshit.models.sec01.Person createPerson(){
        return com.harshit.models.sec01.Person.newBuilder()
                .setAge(18)
                .setName("Harshit")
                .build();
    }
}
