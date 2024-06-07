package com.example.logist_sevice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic raceEvents(){
        return TopicBuilder.name("race_event_topic")
                .build();
    }

    @Bean
    public NewTopic raceGeoPositions() {
        return TopicBuilder.name("race_geo_positions_topic")
                .build();
    }
}
