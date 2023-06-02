package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.KinesisEvent;

import java.nio.charset.StandardCharsets;


public class TransactionConsumer {
    public String handleRequest(KinesisEvent inputEvent, Context context) {
        for(KinesisEvent.KinesisEventRecord record : inputEvent.getRecords()){
            String data = StandardCharsets.UTF_8.decode(record.getKinesis().getData()).toString();
            System.out.println(data);
        }
        return "SUCCESS";
    }
}
