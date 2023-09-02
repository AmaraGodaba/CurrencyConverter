package com.example.currencyconverter;

import java.util.HashMap;

public class Currency {
    private String Name;
    private HashMap<String,Double> exchangeValues = new HashMap<String, Double>();
    public Currency(String n){
        this.Name = n;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public HashMap<String, Double> getExchangeValues() {
        return exchangeValues;
    }

    public void setExchangeValues(HashMap<String, Double> exchangeValues) {
        this.exchangeValues = exchangeValues;
    }
    public void DefaultVales(){
        String currency = this.Name;
        switch (currency){
            case "USD":
                this.exchangeValues.put("USD",1.00);
                break;
            case "GBP":
                this.exchangeValues.put("GBP",1.00);
                this.exchangeValues.put("EUR",1.17);
        }
    }
}
