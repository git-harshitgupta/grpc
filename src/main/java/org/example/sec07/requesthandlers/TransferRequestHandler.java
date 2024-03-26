package org.example.sec07.requesthandlers;

import com.harshit.mdoels.sec07.AccountBalance;
import com.harshit.mdoels.sec07.TransferRequest;
import com.harshit.mdoels.sec07.TransferResponse;
import com.harshit.mdoels.sec07.TransferStatus;
import io.grpc.stub.StreamObserver;
import org.example.sec07.repo.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferRequestHandler implements StreamObserver<TransferRequest>{
    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);
    private final StreamObserver<TransferResponse> responseObserver;

    public TransferRequestHandler(StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    /*
        * it's not necessary to always send response to any request that is coming
     */
    @Override
    public void onNext(TransferRequest transferRequest) {
        var status = transfer(transferRequest);
        var transferResponse = TransferResponse.newBuilder()
                .setFromAccount(toAccountBalance(transferRequest.getFromAccount()))
                .setToAccount(toAccountBalance(transferRequest.getToAccount()))
                .setStatus(status)
                .build();
        //if (status==TransferStatus.COMPLETED)
            responseObserver.onNext(transferResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error occurred - {}",throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        log.info("Transfer request stream completed");
        responseObserver.onCompleted();
    }

    private TransferStatus transfer(TransferRequest request){
        var amount = request.getAmount();
        var fromAccount = request.getFromAccount();
        var toAccount = request.getToAccount();

        if (fromAccount==toAccount||AccountRepository.getBalance(fromAccount)<amount){
            return TransferStatus.REJECTED;
        }
        else{
            AccountRepository.deductAmount(fromAccount,amount);
            AccountRepository.depositMoney(toAccount,amount);
            return TransferStatus.COMPLETED;
        }
    }

    private AccountBalance toAccountBalance(int accountBalance){
        return AccountBalance.newBuilder().setAccountNumber(accountBalance)
                .setBalance(AccountRepository.getBalance(accountBalance))
                .build();
    }
}
