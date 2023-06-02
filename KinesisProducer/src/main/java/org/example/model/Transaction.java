package org.example.model;

public class Transaction {
    int transactionId;
    String transactionType;
    double txnAmount;

    public Transaction(int transactionId, String transactionType, double txnAmount) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.txnAmount = txnAmount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(double txnAmount) {
        this.txnAmount = txnAmount;
    }
}
