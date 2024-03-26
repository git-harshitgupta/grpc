package org.example.sec07.requesthandlers;

import com.harshit.models.sec07.FlowControlServiceGrpc;
import com.harshit.models.sec07.Output;
import com.harshit.models.sec07.RequestSize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(FlowControlService.class);
    @Override
    public StreamObserver<RequestSize> getMessages(StreamObserver<Output> responseObserver) {
        return new RequestHandler(responseObserver);
    }

    private static class RequestHandler implements StreamObserver<RequestSize>{
        private final StreamObserver<Output> responseObserver;
        private Integer emitted;
        public RequestHandler(StreamObserver<Output> responseObserver) {
            this.responseObserver = responseObserver;
            emitted = 0;
        }

        @Override
        public void onNext(RequestSize requestSize) {
            IntStream.rangeClosed(emitted+1,100)
                    .limit(requestSize.getSize())
                    .forEach(i ->{
                        log.info("Emitting - {}",i);
                        responseObserver.onNext(Output.newBuilder().setValue(i).build());
                    });
            emitted = emitted + requestSize.getSize();
            if (emitted >=100){
                responseObserver.onCompleted();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            log.error("Error happened !!");
        }

        @Override
        public void onCompleted() {
            responseObserver.onCompleted();
        }
    }
}
