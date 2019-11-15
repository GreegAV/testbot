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

    private double totalSumNumber;
    private String totalSumName;

    private String billAuthor;


}
