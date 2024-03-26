package com.harshit.test.sec07;

import com.harshit.mdoels.sec07.AccountBalance;
import com.harshit.mdoels.sec07.DepositRequest;
import com.harshit.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientStreamingTest extends AbstractTest{

    @Test
    public void depositTest(){
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver = this.asyncStub.deposit(responseObserver);
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(1).build());
        for (int i = 0; i < 5; i++) {
            var depositRequest = DepositRequest.newBuilder().setAmount(10).build();
            requestObserver.onNext(depositRequest);
        }
        requestObserver.onCompleted();
        responseObserver.await();
        Assertions.assertEquals(1,responseObserver.getItems().size());
        Assertions.assertEquals(150,responseObserver.getItems().get(0).getBalance());
        Assertions.assertNull(responseObserver.getThrowable());
    }

}
