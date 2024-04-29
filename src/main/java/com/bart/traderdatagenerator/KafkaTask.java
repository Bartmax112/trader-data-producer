package com.bart.traderdatagenerator;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@AllArgsConstructor
public class KafkaTask implements Runnable {
    private KafkaProducer<String, DataStructure> producer;

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Sending messages started");

                ProducerRecord<String, DataStructure> goldRecord = getGoldRecord();
                ProducerRecord<String, DataStructure> silverRecord = getSilverRecord();
                ProducerRecord<String, DataStructure> oilRecord = getOilRecord();

                producer.send(silverRecord);
                producer.send(goldRecord);
                producer.send(oilRecord);

                producer.flush();
                System.out.println("Sending messages successfully finished");
                Thread.sleep(5000);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }


    private static ProducerRecord<String, DataStructure> getGoldRecord() {
        var valueToTransfer = generateRandomValues();
        ProducerRecord<String, DataStructure> record = new ProducerRecord<>("gold", valueToTransfer);
        return record;
    }

    private static ProducerRecord<String, DataStructure> getSilverRecord() {
        var valueToTransfer = generateRandomValues();
        ProducerRecord<String, DataStructure> record = new ProducerRecord<>("silver", valueToTransfer);
        return record;
    }

    private static ProducerRecord<String, DataStructure> getOilRecord() {
        var valueToTransfer = generateRandomValues();
        ProducerRecord<String, DataStructure> record = new ProducerRecord<>("oil", valueToTransfer);
        return record;
    }

    private static DataStructure generateRandomValues() {
        Random random = new Random();
        int randomNumber = random.nextInt(101);

        return new DataStructure(Date.from(Instant.now()), String.valueOf(randomNumber));
    }

}
