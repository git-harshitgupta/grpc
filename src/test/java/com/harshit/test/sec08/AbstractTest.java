package com.harshit.test.sec08;

import com.harshit.models.sec08.GuessNumberGrpc;
import com.harshit.test.common.AbstractChannelTest;
import org.example.common.GrpcServer;
import org.example.sec08.GuessNumberService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(new GuessNumberService());
    private GuessNumberGrpc.GuessNumberStub stub;

    @BeforeAll
    public void setup(){
        this.server.start();
        this.stub = GuessNumberGrpc.newStub(channel);
    }

    @Test
    public void TestGuessGame(){
        var responseHandler = new ResponseHandler();
        var requestObserver = this.stub.makeGuess(responseHandler);
        responseHandler.setRequest(requestObserver);
        responseHandler.guess();
        responseHandler.await();

    }

    @AfterAll
    public void stop(){
        server.stop();
    }

}
