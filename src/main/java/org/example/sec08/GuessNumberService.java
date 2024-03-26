package org.example.sec08;

import com.harshit.models.sec08.GuessNumberGrpc;
import com.harshit.models.sec08.GuessRequest;
import com.harshit.models.sec08.GuessResponse;
import com.harshit.models.sec08.Result;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuessNumberService extends GuessNumberGrpc.GuessNumberImplBase {

    private static final Logger log = LoggerFactory.getLogger(GuessNumberService.class);
    private static final Integer number = 26;

    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
        return new RequestHandler(responseObserver);
    }

    private static class RequestHandler implements StreamObserver<GuessRequest>{

        private Integer attempt;
        private StreamObserver<GuessResponse> responseObserver;

        public RequestHandler(StreamObserver<GuessResponse> responseObserver) {
            this.attempt = 0;
            this.responseObserver = responseObserver;
        }

        @Override
        public void onNext(GuessRequest guessRequest) {
            int guessedNumber = guessRequest.getGuess();
            log.info("Guessed number is - {}", guessedNumber);
            if (guessedNumber == number){
                responseObserver.onNext(GuessResponse.newBuilder().setAttempt(++attempt).setResult(Result.CORRECT).build());
                responseObserver.onCompleted();
            }
            else if (guessedNumber < number){
                responseObserver.onNext(GuessResponse.newBuilder().setAttempt(++attempt).setResult(Result.TOO_LOW).build());
            }
            else{
                responseObserver.onNext(GuessResponse.newBuilder().setAttempt(++attempt).setResult(Result.TOO_HIGH).build());
            }
        }

        @Override
        public void onError(Throwable throwable) {
            log.error("Error - {}",throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            responseObserver.onCompleted();
        }
    }
}
