package org.example.sec04;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.harshit.models.sec03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PerformanceTest {
    private static final Logger log = LoggerFactory.getLogger(PerformanceTest.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) {
        var protoPerson = Person.newBuilder()
                .setLastName("Gupta")
                .setAge(12)
                .setEmail("harshit@gmail.com")
                .setEmployed(true)
                .setSalary(1000.121)
                .setBankAccountNumber(12432543654345L)
                .setBalance(-10000)
                .build();

        var jsonPerson = new JsonPerson(
                "Gupta",
                12,
                "harshit@gmail.com",
                true,
                1000.121,
                12432543654345L,
                -10000
        );

        for (int i=0;i<5;i++){
            runTest("json", ()-> {
                try {
                    json(jsonPerson);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            runTest("proto",()->{
                proto(protoPerson);
            });

        }
    }

    private static void proto(Person person){
        try{
            var bytes = person.toByteArray();
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson person) throws IOException {
        var bytes = objectMapper.writeValueAsBytes(person);
        objectMapper.readValue(bytes, JsonPerson.class);
    }

    private static void runTest(String testName, Runnable runnable){
        var start = System.currentTimeMillis();
        for (int i=0;i<1000000;i++){
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("time taken for {} - {} ms",testName, new long[]{end - start});
    }
}
