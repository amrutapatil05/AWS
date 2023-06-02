package org.example;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.aws.TxnKinesisClient;
import org.example.model.Transaction;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.example.constants.Constants.STREAM_NAME;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        App app = new App();
        System.out.println( "Start App!" );

        //get client
        AmazonKinesis kinesisClient = TxnKinesisClient.getKinesisClient();
        System.out.println("Kinesis client created");

        app.putSingleTxn(kinesisClient);
        //app.putMultipleTxns(kinesisClient);

    }

    private void putSingleTxn(AmazonKinesis kinesisClient){
        //1.1put record request
        PutRecordRequest putRecordRequest=getPutRecordRequest();
        System.out.println("PutRecordRequest created :: StreamName = "+putRecordRequest.getStreamName());

        //1.2put record
        PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);
        System.out.println("putRecordResult == "+putRecordResult);
    }
    private void putMultipleTxns(AmazonKinesis kinesisClient) {
        //2.1put records request
        List<PutRecordsRequestEntry> putRecordsRequestEntries=getRecordsRequestList();
        PutRecordsRequest putRecordsRequest = new PutRecordsRequest();
        putRecordsRequest.setRecords(putRecordsRequestEntries);
        putRecordsRequest.setStreamName(STREAM_NAME);
        System.out.println("PutRecordsRequest created :: StreamName = "+putRecordsRequest.getStreamName());

        //2.2putRecords(500 records with single api call)
        PutRecordsResult putRecordsResult = kinesisClient.putRecords(putRecordsRequest);
        System.out.println("putRecordsResult == "+putRecordsResult);
        System.out.println("Failed records count" + putRecordsResult.getFailedRecordCount());
/*        for(PutRecordsResultEntry result : putRecordsResult.getRecords()){
            if(result.getErrorCode()!=null) System.out.println("failed record = "+ result.getSequenceNumber());
        }*/
    }
    private List<PutRecordsRequestEntry> getRecordsRequestList () {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<PutRecordsRequestEntry> putRecordsRequestEntries = new ArrayList<>();
        for(Transaction transaction : getTransactionList()) {
            PutRecordsRequestEntry putRecordsRequestEntry = new PutRecordsRequestEntry();
            putRecordsRequestEntry.setData(ByteBuffer.wrap(gson.toJson(transaction).getBytes(StandardCharsets.UTF_8)));
            putRecordsRequestEntry.setPartitionKey(UUID.randomUUID().toString());
            putRecordsRequestEntries.add(putRecordsRequestEntry);
        }
        return putRecordsRequestEntries;
    }
    private PutRecordRequest getPutRecordRequest(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setStreamName(STREAM_NAME);
        putRecordRequest.setData(ByteBuffer.wrap(gson.toJson(getTransaction()).getBytes(StandardCharsets.UTF_8)));
        putRecordRequest.setPartitionKey(UUID.randomUUID().toString());
        return putRecordRequest;
    }
    private Transaction getTransaction(){
        return new Transaction(1, "Purchase", 100);
    }
    private List<Transaction> getTransactionList (){
        List<Transaction> transactions = new ArrayList<>();
        for(int i=0; i<=500; i++){
            transactions.add(new Transaction(i, "Purchase", 1500));
        }
        return transactions;
    }
}
