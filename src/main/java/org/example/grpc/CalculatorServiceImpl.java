package org.example.grpc;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GRpcService
public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CalculatorServiceImpl.class);

    @Override
    public void add(NumberCouple request, StreamObserver<Number> responseObserver) {
        LOGGER.info("server received {}", request);
        Number reply = Number.newBuilder().setValue(request.getNum1().getValue() + request.getNum2().getValue()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void sub(NumberCouple request, StreamObserver<Number> responseObserver) {
        LOGGER.info("server received {}", request);
        Number reply = Number.newBuilder().setValue(request.getNum1().getValue() - request.getNum2().getValue()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void mul(NumberCouple request, StreamObserver<Number> responseObserver) {
        LOGGER.info("server received {}", request);
        Number reply = Number.newBuilder().setValue(request.getNum1().getValue() * request.getNum2().getValue()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void div(NumberCouple request, StreamObserver<Number> responseObserver) {
        LOGGER.info("server received {}", request);
        Number reply = Number.newBuilder().setValue(request.getNum1().getValue() / request.getNum2().getValue()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Number> addAsync(final StreamObserver<Number> responseObserver) {
        return new StreamObserver<Number>() {
            double sum;
            @Override
            public void onNext(Number number) {
                sum += number.getValue();
                responseObserver.onNext(newNumber(sum));
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    private Number newNumber(double a) {
        return Number.newBuilder().setValue(a).build();
    }
}
