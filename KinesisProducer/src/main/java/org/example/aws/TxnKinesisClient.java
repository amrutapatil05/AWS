package org.example.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;

import static org.example.constants.Constants.*;

public class TxnKinesisClient {

    static {
        System.setProperty(AWS_ACCESS_KEY_NAME, AWS_ACCESS_KEY);
        System.setProperty(AWS_SECRET_KEY_NAME, AWS_SECRET_KEY);
    }

    public static AmazonKinesis getKinesisClient(){
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }
}
