package com.harshit.test.sec07;

import com.harshit.mdoels.sec07.TransferRequest;
import com.harshit.mdoels.sec07.TransferResponse;
import com.harshit.mdoels.sec07.TransferStatus;
import com.harshit.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BiDirectionalStreamingTest extends AbstractTest{

    @Test
    public void transferTest(){
        var responseObserver = ResponseObserver.<TransferResponse>create();
        var requestObserver = transferAsyncStub.transfer(responseObserver);
        var requests = List.of(
                TransferRequest.newBuilder()
                        .setAmount(10)
                        .setFromAccount(1)
                        .setToAccount(1)
                        .build(),
                TransferRequest.newBuilder()
                        .setAmount(10)
                        .setFromAccount(1)
                        .setToAccount(2)
                        .build()
        );
        requests.forEach(requestObserver::onNext);
        requestObserver.onCompleted();
        responseObserver.await();
        Assertions.assertEquals(2,responseObserver.getItems().size());
        validate(responseObserver.getItems().get(0), TransferStatus.REJECTED,100,100);
        validate(responseObserver.getItems().get(1), TransferStatus.COMPLETED,90,110);
    }

    private void validate(TransferResponse response, TransferStatus status,int fromAccountBalance, int toAccountBalance){
        Assertions.assertEquals(status,response.getStatus());
        Assertions.assertEquals(fromAccountBalance,response.getFromAccount().getBalance());
        Assertions.assertEquals(toAccountBalance,response.getToAccount().getBalance());
    }

}
