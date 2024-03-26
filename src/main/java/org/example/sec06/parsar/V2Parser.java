package org.example.sec06.parsar;

import com.google.protobuf.InvalidProtocolBufferException;
import com.harshit.sec06.v2.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V2Parser {
    private static final Logger log = LoggerFactory.getLogger(V2Parser.class);
    public static void parse(byte[] bytes) throws InvalidProtocolBufferException {
        var tv = Television.parseFrom(bytes);
        log.info("brand : {}", tv.getBrand());
        log.info("model : {}", tv.getModel());
        log.info("type : {}", tv.getType());
    }
}
