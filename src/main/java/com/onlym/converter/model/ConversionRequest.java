package com.onlym.converter.model;

public class ConversionRequest {

    private String from;
    private String to;
    private Double amount;

    public ConversionRequest(String from, String to, Double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public ConversionRequest() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
