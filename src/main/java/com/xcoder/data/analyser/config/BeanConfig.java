package com.xcoder.data.analyser.config;

import java.util.Objects;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    @SneakyThrows
    public XML consumerXML() {
        return new XMLDocument(
            Objects.requireNonNull(getClass().getResourceAsStream("/kafka/consumer.xml")).readAllBytes());
    }
}
