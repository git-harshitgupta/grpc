package com.harshit.test.sec11;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.harshit.mdoels.sec11.Money;
import com.harshit.mdoels.sec11.WithdrawRequest;
import com.harshit.test.common.ResponseObserver;
import com.harshit.test.sec07.ServerStreamingClientTest;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class SeverStreamingDeadlineTest extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(ServerStreamingClientTest.class);
    /*
        1. The problem here could be the sever side keeps on doing task with will take alot of time since its a streaming
            process so we need to define deadline based on that.
        2. The next problem is after the client cancelled the process the sever is still not notified about that and keeps
            on running the process

        the solution this problem is Context which is equal to requestScope in spring which will check if the client is
        cancelled the request or still running it.

     */

    @Test
    public void blockingDeadlineTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();

        var ex = Assertions.assertThrows(StatusRuntimeException.class,
                () ->{
                    var iterator = blockingStub
                        .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                        .withdraw(request);

                    while(iterator.hasNext()){
                        iterator.next();
                    }
                }
        );

        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED,ex.getStatus().getCode());
    }

    @Test
    public void asyncDeadlineTest(){
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();
        var observer = ResponseObserver.<Money>create();
        asyncStub
            .withDeadline(Deadline.after(2,TimeUnit.SECONDS))
            .withdraw(request,observer);
        observer.await();
        Assertions.assertEquals(2,observer.getItems().size());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED,Status.fromThrowable(observer.getThrowable()).getCode());
    }



}
