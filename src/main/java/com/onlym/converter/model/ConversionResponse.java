package com.onlym.converter.model;

public class ConversionResponse {

    private String from;
    private String to;
    private Double amount;
    private Double converted;

    public ConversionResponse() {
    }

    public ConversionResponse(String from, String to, Double amount, Double converted) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.converted = converted;
    }

    public String getFrom() {
        return from;
    }

    public static ConversionResponse from(ConversionRequest request, Double rate) {
        return new ConversionResponse(
                request.getFrom(),
                request.getTo(),
                request.getAmount(),
                request.getAmount() * rate);
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

    public Double getConverted() {
        return converted;
    }

    public void setConverted(Double converted) {
        this.converted = converted;
    }
}
