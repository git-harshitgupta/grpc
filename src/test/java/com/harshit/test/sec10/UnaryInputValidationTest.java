package com.harshit.test.sec10;

import com.harshit.mdoels.sec10.AccountBalance;
import com.harshit.mdoels.sec10.BalanceCheckRequest;
import com.harshit.mdoels.sec10.ErrorMessage;
import com.harshit.mdoels.sec10.ValidationCode;
import com.harshit.test.common.ResponseObserver;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnaryInputValidationTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(UnaryInputValidationTest.class);
    @Test
    public void blockingInputValidationTest(){
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () ->{
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(11)
                    .build();
            var response = this.blockingStub.getAccountBalance(request);
        });

        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT,getValidationCode(ex));

    }

    @Test
    public void asyncInputValidation(){
        var request =  BalanceCheckRequest.newBuilder()
                .setAccountNumber(11)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        asyncStub.getAccountBalance(request,observer);
        observer.await();
        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT,getValidationCode((StatusRuntimeException)observer.getThrowable()));
    }

}
