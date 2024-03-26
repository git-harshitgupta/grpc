package com.harshit.test.sec07;

import com.harshit.mdoels.sec07.BankServiceGrpc;
import com.harshit.mdoels.sec07.TransferServiceGrpc;
import com.harshit.test.common.AbstractChannelTest;
import org.example.common.GrpcServer;
import org.example.sec07.BankService;
import org.example.sec07.TransferService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractTest extends AbstractChannelTest {
    private final GrpcServer grpcServer = GrpcServer.create(new BankService(),new TransferService());
    protected BankServiceGrpc.BankServiceBlockingStub blockingStub;
    protected BankServiceGrpc.BankServiceStub asyncStub;
    protected TransferServiceGrpc.TransferServiceStub transferAsyncStub;

    @BeforeAll
    public void setup(){
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.asyncStub = BankServiceGrpc.newStub(channel);
        this.transferAsyncStub = TransferServiceGrpc.newStub(channel);
    }

    @AfterAll
    public void stop(){
        this.grpcServer.stop();
    }
}
