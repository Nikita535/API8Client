package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import org.example.Entity.Task;

public class Client {

    public static GreetingServiceGrpc.GreetingServiceBlockingStub stub;
    public static void startClient() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8081").
            usePlaintext().build();
        stub = GreetingServiceGrpc.newBlockingStub(channel);

//        channel.shutdownNow();
    }

    public static GreetingServiceOuterClass.AllTasksResponse allCases(){
        GreetingServiceOuterClass.Done request = GreetingServiceOuterClass.Done
                .newBuilder().setBoolDone(true).build();
        return stub.allTasks(request);
    }

    public static GreetingServiceOuterClass.TaskResponse addCase(Task entity){
        GreetingServiceOuterClass.TaskRequest request = GreetingServiceOuterClass.TaskRequest
                .newBuilder().setDate(entity.getDate()).setDescription(entity.getDescription())
                .setName(entity.getName()).setId(0).build();
        return stub.addTask(request);
    }

    public static GreetingServiceOuterClass.Done deleteCase(Long id){
        GreetingServiceOuterClass.TaskRequest request = GreetingServiceOuterClass.TaskRequest
                .newBuilder().setDate("").setDescription("").setName("").setId(id).build();
        return stub.deleteTask(request);
    }

    public static GreetingServiceOuterClass.TaskResponse changeCase(Task entity){
        GreetingServiceOuterClass.TaskRequest request = GreetingServiceOuterClass.TaskRequest
                .newBuilder().setDate(entity.getDate()).setDescription(entity.getDescription())
                .setName(entity.getName()).setId(entity.getId()).build();
        return stub.changeTask(request);
    }
}
