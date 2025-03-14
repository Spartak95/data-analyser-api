package com.xcoder.data.analyser.service;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xcoder.data.analyser.config.LocalDateTimeDeserializer;
import com.xcoder.data.analyser.model.Data;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {
    private final KafkaReceiver<String, Object> kafkaReceiver;
    private final LocalDateTimeDeserializer localDateTimeDeserializer;
    private final KafkaDataService kafkaDataService;

    @PostConstruct
    public void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer)
            .create();
        kafkaReceiver.receive().subscribe(r -> {
            Data data = gson.fromJson(r.value().toString(), Data.class);
            kafkaDataService.handle(data);
            r.receiverOffset().acknowledge();
        });
    }
}
