package com.harshit.test.common;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractChannelTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractChannelTest.class);
    protected ManagedChannel channel;
    @BeforeAll
    public void setupChannel(){
        log.info("Starting sever");
        this.channel = ManagedChannelBuilder.forAddress("localhost",6565)
                .usePlaintext()
                .build();
    }
    @AfterAll
    public void stopChannel() throws InterruptedException {
        this.channel.shutdownNow()
                .awaitTermination(5, TimeUnit.SECONDS);
        log.info("Shutting down the server");
    }
}
