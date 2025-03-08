package com.xcoder.data.analyser.service;

import com.xcoder.data.analyser.model.Data;

public interface KafkaDataService {
    void handle(Data data);
}
