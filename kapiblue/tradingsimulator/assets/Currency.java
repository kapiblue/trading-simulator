/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import java.text.DecimalFormat;
import java.util.*;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Currency asset
 *
 * @author kapib
 */
public class Currency extends Asset {

    private String code;
    private float buyRate;
    private float sellRate;
    private Set<String> countries;

    /**
     * Initialized with random values
     */
    public Currency() {
        super(1000, 1);

        Random random = new Random();
        Generator generator = new Generator();
        float currentPrice = this.getCurrentPrice();
        this.code = generator.generateRandomWord(3);
        this.buyRate = currentPrice - random.nextFloat();
        this.sellRate = currentPrice;
        Set<String> countries = new HashSet<>();
        int numberOfCountries = random.nextInt(6) + 1;
        for (int i = 0; i < numberOfCountries; i++) {
            String country = generator.generateRandomWord(8);
            countries.add(country);
        }
        this.countries = countries;
    }

    /**
     * Updates the current price, sell rate and buy rate
     *
     * @param changeFactor
     */
    @Override
    public void updatePrice(float changeFactor) {
        float currrentPrice = getCurrentPrice();
        addPriceHistoryValue(currrentPrice);

        float newPrice = currrentPrice + (new Random()).nextFloat(10, currrentPrice / 10 + 12) * changeFactor;
        setCurrentPrice(newPrice);

        sellRate = newPrice;
        buyRate = newPrice - (new Random()).nextFloat();
    }

    /**
     * Gets the code of this currency
     *
     * @return
     */
    @Override
    public String getName() {
        return code;
    }

    /**
     * Returns a string of countries of this currency each in a new line.
     *
     * @return
     */
    public String countriesInNewLine() {
        String countriesLine = "\n";
        for (String country : this.countries) {
            countriesLine += country + "\n";
        }
        return countriesLine;
    }

    /**
     * Formatted string with this currency details
     *
     * @return
     */
    @Override
    public String assetDetails() {
        DecimalFormat form = new DecimalFormat("0.00");
        return "Currency: " + code + "\n" + "Buy rate: " + form.format(buyRate) + "\n"
                + "Sell rate: " + form.format(sellRate) + "\n" + "Countries: " + countriesInNewLine();
    }

    @Override
    public String toString() {
        return "Currency " + code;
    }

}
