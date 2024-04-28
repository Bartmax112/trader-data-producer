package com.bart.traderdatagenerator;

import java.time.Instant;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SpringBootApplication
public class TraderDataGeneratorApplication {

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers","kafka-1:9092");
		properties.setProperty("key.serializer", StringSerializer.class.getName());

		properties.setProperty("value.serializer",JsonSerializer.class.getName());

		KafkaProducer<String, DataStructure> producer = new KafkaProducer<>(properties);

		var valueToTransfer = new DataStructure(Date.from(Instant.now()), "100");
		
		ProducerRecord<String, DataStructure> record = new ProducerRecord<>("gold", valueToTransfer);

		producer.send(record);

		producer.flush();

		producer.close();
	}

}
