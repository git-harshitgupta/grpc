package org.example.sec07.requesthandlers;

import com.harshit.mdoels.sec07.AccountBalance;
import com.harshit.mdoels.sec07.DepositRequest;
import io.grpc.stub.StreamObserver;
import org.example.sec07.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepositRequestHandler implements StreamObserver<DepositRequest> {
    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);
    private final StreamObserver<AccountBalance> responseObserver;
    private int accountNumber;
    public DepositRequestHandler(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        switch (depositRequest.getRequestCase()){
            case ACCOUNT_NUMBER -> this.accountNumber=depositRequest.getAccountNumber();
            case AMOUNT -> AccountRepository.depositMoney(this.accountNumber,depositRequest.getAmount());
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("Client error - {}",throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();
        this.responseObserver.onNext(accountBalance);
        this.responseObserver.onCompleted();
    }
}
