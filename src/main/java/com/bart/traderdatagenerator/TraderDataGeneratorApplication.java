package com.bart.traderdatagenerator;

import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SpringBootApplication
public class TraderDataGeneratorApplication {

	public static void main(String[] args) {
		var task = new KafkaTask(new KafkaProducer<>(getKafkaProperties()));
		task.run();
	}

	private static Properties getKafkaProperties() {
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers","kafka-1:9092");
		properties.setProperty("key.serializer", StringSerializer.class.getName());

		properties.setProperty("value.serializer", JsonSerializer.class.getName());
		return properties;
	}

}
