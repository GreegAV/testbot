package com.obrttestbot;

import java.util.Date;

public class Bill {
    private Firm recepient;
    private Firm sender;
    private Firm payer;

    private String billReason;
    private String billNumber;
    private Date billDateCreate;
    private Date billDateValid;

    private Product product;
    private int productQuantity;

    private double totalSumNumber;
    private String totalSumName;

    private String billAuthor;

    public String getTotalSumName() {
        return getPriceToString(this.totalSumNumber);
    }

    private String getPriceToString(double totalSumNumber) {
        // TODO convert price to string
        return "";
    }

    public void setBillAuthor(Firm sender) {
        this.billAuthor = sender.getFirmHead();
    }

    public String getBillAuthor() {
        return sender.getFirmHead();
    }

    public Firm getRecepient() {
        return recepient;
    }

    public void setRecepient(Firm recepient) {
        this.recepient = recepient;
    }

    public Firm getSender() {
        return sender;
    }

    public void setSender(Firm sender) {
        this.sender = sender;
    }

    public Firm getPayer() {
        return payer;
    }

    public void setPayer(Firm payer) {
        this.payer = payer;
    }

    public String getBillReason() {
        return billReason;
    }

    public void setBillReason(String billReason) {
        this.billReason = billReason;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Date getBillDateCreate() {
        return billDateCreate;
    }

    public void setBillDateCreate(Date billDateCreate) {
        this.billDateCreate = billDateCreate;
    }
}
