package com.harshit.test.sec07.flowcontrol;

import com.google.common.util.concurrent.Uninterruptibles;
import com.harshit.models.sec07.Output;
import com.harshit.models.sec07.RequestSize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ResponseHandler implements StreamObserver<Output> {
    private static final Logger log = LoggerFactory.getLogger(ResponseHandler.class);
    private final CountDownLatch latch = new CountDownLatch(1);
    private int size;
    private StreamObserver<RequestSize> requestObserver;
    public void setRequestObserver(StreamObserver<RequestSize> requestObserver){
        this.requestObserver = requestObserver;
    }

    private void request(int size){
        log.info("Requesting size - {}", size);
        this.size = size;
        this.requestObserver.onNext(RequestSize.newBuilder().setSize(size).build());
    }

    private void process(Output output){
        log.info("Received size {}", output.getValue());
        Uninterruptibles.sleepUninterruptibly(ThreadLocalRandom.current().nextInt(50,200), TimeUnit.MILLISECONDS);
    }
    @Override
    public void onNext(Output output) {
        this.size--;
        process(output);
        if (size==0) {
            request(3);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.requestObserver.onCompleted();
        log.info("Completed");
        latch.countDown();
    }

    public void await(){
        try{
            latch.await();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public void start(){
        this.request(3);
    }
}
