package org.example.common;

import org.example.sec07.BankService;
import org.example.sec07.TransferService;
import org.example.sec07.requesthandlers.FlowControlService;
import org.example.sec08.GuessNumberService;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(new BankService(), new TransferService(), new FlowControlService(), new GuessNumberService())
                .start()
                .await();
    }
}
