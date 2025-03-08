package com.xcoder.data.analyser.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcabi.xml.XML;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${topics}")
    private List<String> topics;

    private final XML settings;

    @Bean
    public Map<String, Object> receiveProperties() {
        Map<String, Object> props = HashMap.newHashMap(5);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, new TextXpath(this.settings, "//groupId").toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                  new TextXpath(this.settings, "//keyDeserializer").toString());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                  new TextXpath(this.settings, "//valueDeserializer").toString());
        props.put("spring.json.trusted.packages", new TextXpath(this.settings, "//trustedPackages").toString());
        return props;
    }

    @Bean
    public ReceiverOptions<String, Object> receiverOptions() {
        ReceiverOptions<String, Object> receiverOptions = ReceiverOptions.create(receiveProperties());
        return receiverOptions.subscription(topics)
            .addAssignListener(partitions ->
                log.info("Partitions assigned: {}", partitions))
            .addRevokeListener(partitions ->
                log.info("Partitions revoked: {}", partitions));
    }

    @Bean
    public KafkaReceiver<String, Object> receiver(ReceiverOptions<String, Object> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }
}
