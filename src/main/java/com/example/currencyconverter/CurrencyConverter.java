package com.example.currencyconverter;

import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;

public class CurrencyConverter {

    @FXML
    private ComboBox<String> sourceCurrencyCombo;
    @FXML
    private ComboBox<String> targetCurrencyCombo;
    @FXML
    private Label lastUpdatedLabel;
    @FXML
    private Button convertButton;
    @FXML
    private Label convertedValueLabel;
    @FXML
    private TextField amountField;

    private static final DecimalFormat DECFOR = new DecimalFormat("0.00");

    @FXML
    public void initialize() {
        ObservableList<String> currencies = FXCollections.observableArrayList(
                "USD", "EUR", "GBP", "INR", "CNY", "JPY", "CAD", "AUD",
                "ZAR", "BRL", "SAR", "RUB", "MXN", "KRW", "NGN"
        );

        CurrencyService service = new CurrencyService();
        JsonObject rates = service.fetchExchangerates();

        sourceCurrencyCombo.setItems(currencies);
        targetCurrencyCombo.setItems(currencies);

        convertButton.setOnAction(actionEvent -> convertCurrency(rates));
    }

    private void convertCurrency(JsonObject rates) {
        if (isValidInput()) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (sourceCurrencyCombo.getValue().equals("USD")) {
                    handleUSDSourceConversion(amount, rates);
                } else {
                    handleNonUSDSourceConversion(amount, rates);
                }
            } catch (NumberFormatException e) {
                notifyUser("Invalid number format.");
            }
        } else {
            notifyUser("Incomplete input. Please ensure you've selected both currencies and entered an amount.");
        }
    }

    private boolean isValidInput() {
        return sourceCurrencyCombo.getValue() != null
                && targetCurrencyCombo.getValue() != null
                && !amountField.getText().isEmpty();
    }

    private void handleUSDSourceConversion(double amount, JsonObject rates) {
        double rate = getRateSafe(rates, "USD" + targetCurrencyCombo.getValue());
        double result = amount * rate;

        convertedValueLabel.setText("Converted Amount: " + DECFOR.format(result));
        lastUpdatedLabel.setText("1 USD = " + rate + " " + targetCurrencyCombo.getValue());
    }

    private void handleNonUSDSourceConversion(double amount, JsonObject rates) {
        double sourceRate = getRateSafe(rates, "USD" + sourceCurrencyCombo.getValue());
        double targetRate = getRateSafe(rates, "USD" + targetCurrencyCombo.getValue());
        double inUSD = amount / sourceRate;
        double result = inUSD * targetRate;

        convertedValueLabel.setText("Converted Amount: " + DECFOR.format(result));
        lastUpdatedLabel.setText("1 " + sourceCurrencyCombo.getValue() + " = " + (1/sourceRate) * targetRate + targetCurrencyCombo.getValue());
    }

    private double getRateSafe(JsonObject rates, String key) {
        return rates.has(key) ? rates.get(key).getAsDouble() : 0;
    }

    private void notifyUser(String message) {
        // This just prints now, but you might want to use an Alert or other dialog in future
        System.out.println(message);
    }
}
