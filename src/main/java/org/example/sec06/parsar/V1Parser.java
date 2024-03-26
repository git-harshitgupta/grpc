package org.example.sec06.parsar;

import com.google.protobuf.InvalidProtocolBufferException;
import com.harshit.sec06.v1.Television;
import org.example.sec05.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1Parser {
    private static final Logger log = LoggerFactory.getLogger(V1Parser.class);
    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        log.info("brand : {}", tv.getBrand());
        log.info("year : {}", tv.getYear());
        log.info("get unknown field : {}", tv.getUnknownFields());
    }
}
