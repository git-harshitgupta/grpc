package com.harshit.test.sec07.flowcontrol;

import com.harshit.models.sec07.FlowControlServiceGrpc;
import com.harshit.test.common.AbstractChannelTest;
import org.example.common.GrpcServer;
import org.example.sec07.requesthandlers.FlowControlService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlowControlTest extends AbstractChannelTest {
    private final GrpcServer server = GrpcServer.create(new FlowControlService());
    private FlowControlServiceGrpc.FlowControlServiceStub stub;
    @BeforeAll
    public void setup(){
        this.server.start();
        this.stub = FlowControlServiceGrpc.newStub(channel);
    }

    @Test
    public void FlowControlDemo(){
        var responseObserver = new ResponseHandler();
        var requestObserver = this.stub.getMessages(responseObserver);
        responseObserver.setRequestObserver(requestObserver);
        responseObserver.start();
        responseObserver.await();

    }

    @AfterAll
    public void stop(){
        server.stop();
    }
}
