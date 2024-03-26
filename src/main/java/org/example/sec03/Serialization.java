package org.example.sec03;

import com.harshit.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serialization {
    private static final Logger log = LoggerFactory.getLogger(Serialization.class);
    private static final Path path = Path.of("person.out");

    public static void main(String[] args) throws IOException {
        var person = Person.newBuilder()
                .setLastName("Gupta")
                .setAge(12)
                .setEmail("harshit@gmail.com")
                .setEmployed(true)
                .setSalary(1000.121)
                .setBankAccountNumber(12432543654345L)
                .setBalance(-10000)
                .build();

        serialize(person);
        log.info("{}",deserialize());
        log.info("equal : {}",person.equals(deserialize()));
    }

    public static void serialize(Person person) throws IOException{
        try(var stream = Files.newOutputStream(path)){
            person.writeTo(stream);
        }

    }

    public static Person deserialize() throws IOException{
        return Person.parseFrom(Files.newInputStream(path));
    }
}
