package org.example.common;

import io.grpc.*;
import org.example.sec07.BankService;

import java.io.IOException;
import java.util.Arrays;

public class GrpcServer {
    private final Server server;
    private GrpcServer(Server server){
        this.server = server;
    }

    public static GrpcServer create(BindableService... services){
        return create(6565,services);
    }

    public static GrpcServer create(int port, BindableService... services){
        var builder = ServerBuilder.forPort(port);
        Arrays.asList(services).forEach(builder::addService);
        return new GrpcServer(builder.build());
    }

    public GrpcServer start(){
        var services = server.getServices()
                        .stream()
                        .map(ServerServiceDefinition::getServiceDescriptor)
                        .map(ServiceDescriptor::getName)
                        .toList();
        try {
            server.start();
            System.out.println("Server started listing to port "+server.getPort()+" services : "+services);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void await(){
        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop(){
        server.shutdown();
        System.out.println("Server shut down");
    }
}

