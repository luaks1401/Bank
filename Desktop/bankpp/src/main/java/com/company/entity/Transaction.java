package com.company.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private int id;
    private Date date;
    private int fromAccountNumber;
    private BigDecimal amount;
    private String type;
    private Boolean access;
    private Boolean canDoTransaction = true;
    private String transactionStatus = "...";

    public Transaction() {}

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(int fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCanDoTransaction() {
        return this.canDoTransaction;
    }

    public void setCanDoTransaction(Boolean doTransaction) {
        this.canDoTransaction = doTransaction;
    }

    public String getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
