package com.harshit.test.sec07;

import com.harshit.mdoels.sec07.Money;
import com.harshit.mdoels.sec07.WithdrawRequest;
import com.harshit.test.common.ResponseObserver;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStreamingClientTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(ServerStreamingClientTest.class);

    @Test
    public void blockingClientWithdrawTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();

        var iterator = this.blockingStub.withdraw(request);
        int count = 0;
        while(iterator.hasNext()){
            log.info("Received money - {}",iterator.next());
            count++;
        }
        Assertions.assertEquals(5,count);
    }

    @Test
    public void asyncClientWithdrawTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();
        var observer = ResponseObserver.<Money>create();
        this.asyncStub.withdraw(request,observer);
        observer.await();
        Assertions.assertEquals(5,observer.getItems().size());
        Assertions.assertEquals(10,observer.getItems().get(0).getAmount());
        Assertions.assertNull(observer.getThrowable());
    }

}
