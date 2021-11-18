package com.onlym.converter.model;

public class ConversionResponse {

    private final String from;
    private final String to;
    private final Double amount;
    private final Double converted;

    public ConversionResponse(String from, String to, Double amount, Double converted) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.converted = converted;
    }

    public String getFrom() {
        return from;
    }

    public static ConversionResponse from(ConversionRequest request, Double converted) {
        return new ConversionResponse(
                request.getFrom(),
                request.getTo(),
                request.getAmount(),
                converted);
    }

    public String getTo() {
        return to;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getConverted() {
        return converted;
    }
}
