package org.example.sec06;

import com.google.protobuf.InvalidProtocolBufferException;
import com.harshit.sec06.v2.Television;
import com.harshit.sec06.v2.Type;
import org.example.sec06.parsar.V1Parser;
import org.example.sec06.parsar.V2Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V2VersionCompatibility {
    private static final Logger log = LoggerFactory.getLogger(V2VersionCompatibility.class);

    public static void main(String[] args) throws InvalidProtocolBufferException {
        var tv = Television.newBuilder()
                .setBrand("samsung")
                .setModel(2019)
                .setType(Type.OLED)
                .build();
        V2Parser.parse(tv.toByteArray());
        V1Parser.parse(tv.toByteArray());
    }
}
