package org.example.sec07;

import com.harshit.mdoels.sec07.TransferRequest;
import com.harshit.mdoels.sec07.TransferResponse;
import com.harshit.mdoels.sec07.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.example.sec07.requesthandlers.TransferRequestHandler;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}
