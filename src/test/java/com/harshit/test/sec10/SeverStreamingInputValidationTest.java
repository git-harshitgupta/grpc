package com.harshit.test.sec10;

import com.harshit.mdoels.sec10.Money;
import com.harshit.mdoels.sec10.ValidationCode;
import com.harshit.mdoels.sec10.WithdrawRequest;
import com.harshit.test.common.ResponseObserver;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SeverStreamingInputValidationTest extends AbstractTest {

    @ParameterizedTest
    @MethodSource("testData")
    public void blockingInputValidationTest(WithdrawRequest request, ValidationCode code){
        var ex = Assertions.assertThrows(StatusRuntimeException.class,()->{
            var response = blockingStub.withdraw(request).hasNext();
        });
        Assertions.assertEquals(code,getValidationCode(ex));
    }

    @ParameterizedTest
    @MethodSource("testData")
    public void asyncInputValidation(WithdrawRequest request,ValidationCode code){
        var observer = ResponseObserver.<Money>create();
        asyncStub.withdraw(request,observer);
        observer.await();
        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(code,getValidationCode((StatusRuntimeException)observer.getThrowable()));
    }

    private Stream<Arguments> testData(){
        return Stream.of(
                Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(10)
                        .setAccountNumber(11)
                        .build(),
                        ValidationCode.INVALID_ACCOUNT
                ), Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(12)
                        .setAccountNumber(10)
                        .build(),
                    ValidationCode.INVALID_AMOUNT
                ), Arguments.of( WithdrawRequest.newBuilder()
                        .setAmount(150)
                        .setAccountNumber(10)
                        .build(),
                   ValidationCode.INSUFFICIENT_BALANCE
                )
        );
    }

}
