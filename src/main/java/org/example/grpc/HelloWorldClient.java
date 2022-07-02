package org.example.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component
public class HelloWorldClient {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(HelloWorldClient.class);

    private HelloWorldServiceGrpc.HelloWorldServiceBlockingStub helloWorldServiceBlockingStub;

    public HelloWorldClient() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 6565).usePlaintext().build();

        helloWorldServiceBlockingStub =
                HelloWorldServiceGrpc.newBlockingStub(managedChannel);
    }

    public static void main(String[] args) throws Exception {
        HelloWorldClient helloWorldClient = new HelloWorldClient();
        String response = helloWorldClient.sayHello("Aakash","Bhagat");
        LOGGER.info(response);
    }

    public String sayHello(String firstName, String lastName) {
        Person person = Person.newBuilder().setFirstName(firstName)
                .setLastName(lastName).build();
        LOGGER.info("client sending {}", person);

        Greeting greeting =
                helloWorldServiceBlockingStub.sayHello(person);
        LOGGER.info("client received {}", greeting);

        return greeting.getMessage();
    }
}
