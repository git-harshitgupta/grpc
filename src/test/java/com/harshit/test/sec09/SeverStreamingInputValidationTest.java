package com.harshit.test.sec09;

import com.harshit.mdoels.sec09.AccountBalance;
import com.harshit.mdoels.sec09.BalanceCheckRequest;
import com.harshit.mdoels.sec09.Money;
import com.harshit.mdoels.sec09.WithdrawRequest;
import com.harshit.test.common.ResponseObserver;
import com.sun.jdi.connect.Connector;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SeverStreamingInputValidationTest extends AbstractTest{

    @ParameterizedTest
    @MethodSource("testData")
    public void blockingInputValidationTest(WithdrawRequest request, Status.Code code){
        var ex = Assertions.assertThrows(StatusRuntimeException.class,()->{
            var response = blockingStub.withdraw(request).hasNext();
        });
        Assertions.assertEquals(code,ex.getStatus().getCode());
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void asyncInputValidation(WithdrawRequest request,Status.Code code){
        var observer = ResponseObserver.<Money>create();
        asyncStub.withdraw(request,observer);
        observer.await();
        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(code,((StatusRuntimeException)observer.getThrowable()).getStatus().getCode());
    }

    private Stream<Arguments> testData(){
        return Stream.of(
                Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(10)
                        .setAccountNumber(11)
                        .build(),
                    Status.Code.INVALID_ARGUMENT
                ), Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(12)
                        .setAccountNumber(11)
                        .build(),
                    Status.Code.INVALID_ARGUMENT
                ), Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(150)
                        .setAccountNumber(10)
                        .build(),
                    Status.Code.FAILED_PRECONDITION
                )
        );
    }

}
