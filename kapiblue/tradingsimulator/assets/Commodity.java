/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Commodity asset
 *
 * @author kapib
 */
public class Commodity extends Asset {

    private String name;
    private String tradingUnit;
    private String tradingCurrency;
    private float minimalPrice;
    private float maximalPrice;

    /**
     * Constructor. Minimal and maximal prices are relative to the current price
     */
    public Commodity() {
        super(1000, 100);
        Generator generator = new Generator();
        Random random = new Random();
        this.name = generator.generateRandomWord(5);
        this.tradingUnit = generator.generateRandomWord(6);
        this.tradingCurrency = generator.generateRandomWord(3);
        float currentPrice = this.getCurrentPrice();
        this.minimalPrice = currentPrice - random.nextFloat(2);
        this.maximalPrice = currentPrice + random.nextFloat(3);
    }

    /**
     * Updates current price
     *
     * @param changeFactor
     */
    @Override
    public void updatePrice(float changeFactor) {
        float currentPrice = getCurrentPrice();
        addPriceHistoryValue(currentPrice);

        float newPrice = currentPrice + (new Random()).nextFloat(10, currentPrice / 12 + 15) * changeFactor;

        setCurrentPrice(newPrice);
        minimalPrice = newPrice - (new Random()).nextFloat(2);
        maximalPrice = newPrice + (new Random()).nextFloat(3);
    }

    /**
     * Formatted string with details of this commodity
     *
     * @return
     */
    @Override
    public String assetDetails() {
        DecimalFormat form = new DecimalFormat("0.00");
        return "Commodity: " + name + "\n" + "Trading unit: " + tradingUnit + "\n"
                + "Trading currency: " + tradingCurrency + "\n" + "Minimal price: " + form.format(minimalPrice) + "\n"
                + "Maximal price: " + form.format(maximalPrice) + "\n";
    }

    @Override
    public String toString() {
        return "Commodity " + name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

}
