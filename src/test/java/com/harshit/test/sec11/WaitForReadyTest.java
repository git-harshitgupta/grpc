package com.harshit.test.sec11;


import com.google.common.util.concurrent.Uninterruptibles;
import com.harshit.mdoels.sec11.BalanceCheckRequest;
import com.harshit.mdoels.sec11.BankServiceGrpc;
import com.harshit.test.common.AbstractChannelTest;
import io.grpc.Deadline;
import org.example.common.GrpcServer;
import org.example.sec11.DeadlineBankService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WaitForReadyTest extends AbstractChannelTest {
    private static final Logger log = LoggerFactory.getLogger(WaitForReadyTest.class);
    private final GrpcServer server = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    @BeforeAll
    public void setup(){
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        Thread thread = new Thread(()->{
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            server.start();
        });
        thread.start();
    }

    @Test
    public void waitForReadyTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        log.info("Hitting the server");
        var response = blockingStub
                .withWaitForReady()
                .withDeadline(Deadline.after(15,TimeUnit.SECONDS))
                .getAccountBalance(request);

        Assertions.assertEquals(1,response.getAccountNumber());
    }

    @AfterAll
    public void stop(){
        server.stop();
    }
}
