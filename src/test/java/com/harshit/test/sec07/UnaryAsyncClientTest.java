package com.harshit.test.sec07;

import com.google.protobuf.Empty;
import com.harshit.mdoels.sec07.AccountBalance;
import com.harshit.mdoels.sec07.AllAccountsResponse;
import com.harshit.mdoels.sec07.BalanceCheckRequest;
import com.harshit.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UnaryAsyncClientTest extends AbstractTest{

    private static final Logger log = LoggerFactory.getLogger(UnaryAsyncClientTest.class);
    @Test
    public void getBalanceTest() throws InterruptedException {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.asyncStub.getAccountBalance(request,observer);
        observer.await();
        Assertions.assertEquals(1,observer.getItems().size());
        Assertions.assertEquals(100,observer.getItems().get(0).getBalance());
        Assertions.assertNull(observer.getThrowable());
    }

    @Test
    public void getAllAccountsTest(){
        var observer = ResponseObserver.<AllAccountsResponse>create();
        this.asyncStub.getAllAccounts(Empty.getDefaultInstance(),observer);
        observer.await();
        Assertions.assertEquals(1,observer.getItems().size());
        Assertions.assertEquals(10,observer.getItems().get(0).getAccountsCount());
        Assertions.assertNull(observer.getThrowable());
    }
}
