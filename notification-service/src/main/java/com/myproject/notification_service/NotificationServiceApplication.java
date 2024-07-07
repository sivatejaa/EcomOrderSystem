package com.myproject.notification_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}


	/*

	spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.group-id=my-consumer-group

spring.kafka.consumer.properties.spring.json.type.mapping=event:com.myproject.notification_service.Order

spring.kafka.consumer.properties.spring.json.trusted.packages=com.myproject.notification_service
	 */
	@KafkaListener(topics = "notificationTopic", groupId = "my-consumer-group")
	public void handleNotification(String orderRequest){


        log.info("notificationTopic called {}", orderRequest);
	}
}
