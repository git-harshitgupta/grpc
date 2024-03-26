package com.harshit.test.sec08;

import com.harshit.models.sec08.GuessRequest;
import com.harshit.models.sec08.GuessResponse;
import com.harshit.models.sec08.Result;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ResponseHandler implements StreamObserver<GuessResponse> {
    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);
    private StreamObserver<GuessRequest> request;

    private CountDownLatch count = new CountDownLatch(1);

    public void setRequest(StreamObserver<GuessRequest> request){
        this.request = request;
    }
    private int low = 1;
    private int high = 100;

    @Override
    public void onNext(GuessResponse result) {
        switch (result.getResultValue()) {
            case Result.TOO_LOW_VALUE -> {
                low = getMid() + 1;
                request.onNext(GuessRequest.newBuilder().setGuess(getMid()).build());
                break;
            }

            case Result.TOO_HIGH_VALUE -> {
                high = getMid() - 1;
                request.onNext(GuessRequest.newBuilder().setGuess(getMid()).build());
                break;
            }

            default -> {
                log.info("You guessed the number - {}", result);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error - {}",throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        request.onCompleted();
        count.countDown();
    }

    public void guess(){
        request.onNext(GuessRequest.newBuilder().setGuess(getMid()).build());
    }

    public void await(){
        try {
            count.await();
        }catch (InterruptedException exception){
            throw new RuntimeException(exception);
        }
    }

    private int getMid(){
        return (low+high)/2;
    }
}
