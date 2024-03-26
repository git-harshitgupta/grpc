package com.harshit.test.sec07;

import com.google.protobuf.Empty;
import com.harshit.mdoels.sec07.BalanceCheckRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnaryBlockingClientTest extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(UnaryBlockingClientTest.class);

    @Test
    public void getBalanceTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(2)
                .build();
        var balance = this.blockingStub.getAccountBalance(request);
        log.info("Unary balance received : {}",balance);
        Assertions.assertEquals(100,balance.getBalance());
    }

    @Test
    public void getAllAccountsTest(){
        var accounts = this.blockingStub.getAllAccounts(Empty.getDefaultInstance());
        log.info("Unary all accounts received : {}",accounts);
        Assertions.assertTrue(accounts.getAccountsCount()>0);
    }
}
