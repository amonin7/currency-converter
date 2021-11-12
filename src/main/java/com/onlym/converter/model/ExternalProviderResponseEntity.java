package com.onlym.converter.model;

import java.util.HashMap;

public class ExternalProviderResponseEntity {

    private String base;
    private String date;
    private HashMap<String, Double> rates;
    private String result;
    private boolean success;

    public ExternalProviderResponseEntity() {
    }

    public ExternalProviderResponseEntity(String base, String date, HashMap<String, Double> rates, String result, boolean success) {
        this.base = base;
        this.date = date;
        this.rates = rates;
        this.result = result;
        this.success = success;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
