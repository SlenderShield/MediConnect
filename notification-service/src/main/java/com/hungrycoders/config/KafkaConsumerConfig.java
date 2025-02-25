package com.hungrycoders.config;

import com.hungrycoders.model.Appointment;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka consumer.
 * This sets up the Kafka consumer factory and listener container factory
 * for processing Kafka messages.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	// Kafka broker address
	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String kafkaAddress;

	// Consumer group ID for Kafka
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;

	/**
	 * Creates a Kafka ConsumerFactory with configurations for the consumer.
	 * @return ConsumerFactory instance configured for Kafka.
	 */
	@Bean
	public ConsumerFactory<String, Appointment> consumerFactory() {
		// Kafka consumer properties
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress); // Kafka broker address
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId); // Consumer group ID
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Key deserialization
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // Value deserialization
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // Disable auto-commit for better control
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Start consuming from the earliest offset if no offset is committed
		return new DefaultKafkaConsumerFactory<>(props);
	}

	/**
	 * Configures the Kafka listener container factory.
	 * @return ConcurrentKafkaListenerContainerFactory instance for managing Kafka listeners.
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Appointment> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Appointment> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory()); // Set the consumer factory
		factory.setConcurrency(3); // Allow 3 concurrent consumers
		factory.getContainerProperties().setPollTimeout(3000); // Poll timeout for fetching messages
		return factory;
	}
}
