package co.com.crediya.sqs.listener.helper;

import co.com.crediya.sqs.listener.SQSProcessor;
import co.com.crediya.sqs.listener.config.SQSProperties;
import jakarta.annotation.PostConstruct;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class SQSListener {

    private final SqsAsyncClient sqsAsyncClient;
    private final SQSProperties properties;
    private final SQSProcessor processor;

    @PostConstruct
    public void start() {
        log.info("Iniciando Reactive SQS Listener en cola: {}", properties.queueUrl());

        Flux.defer(this::listen)
                .subscribeOn(Schedulers.boundedElastic())
                .repeat()
                .retry()
                .subscribe();
    }

    private Flux<Void> listen() {
        return Mono.fromFuture(() -> sqsAsyncClient.receiveMessage(ReceiveMessageRequest.builder()
                        .queueUrl(properties.queueUrl())
                        .maxNumberOfMessages(properties.maxNumberOfMessages())
                        .waitTimeSeconds(properties.waitTimeSeconds())
                        .visibilityTimeout(properties.visibilityTimeoutSeconds())
                        .build()))
                .flatMapMany(response -> Flux.fromIterable(response.messages()))
                .flatMap(this::handleMessage, properties.numberOfThreads())
                .onErrorContinue((ex, obj) -> log.error("Error en listener con {}", obj, ex));
    }

    private Mono<Void> handleMessage(Message message) {
        return processor.apply(message)
                .then(deleteMessage(message))
                .onErrorResume(ex -> {
                    log.error("Error procesando mensaje (ID={}): {}", message.messageId(), ex.getMessage(), ex);
                    return Mono.empty();
                });
    }

    private Mono<Void> deleteMessage(Message message) {
        return Mono.fromFuture(() -> sqsAsyncClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(properties.queueUrl())
                        .receiptHandle(message.receiptHandle())
                        .build()))
                .doOnSuccess(r -> log.info("Mensaje eliminado (ID={})", message.messageId()))
                .then();
    }
}