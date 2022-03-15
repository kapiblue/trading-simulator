/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import java.util.*;
import kapiblue.tradingsimulator.exceptions.PriceBelowZeroException;

/**
 * Abstract class for assets: commodities, companies and currencies
 *
 * @author kapib
 */
public abstract class Asset {

    private int initialAmount;
    private float currentPrice;
    private List<Float> priceHistory;
    private String name = getName();

    /**
     * Constructor with integer multipliers. Multipliers make it possible to get
     * different ranges of initial values based on asset type.
     *
     * @param amountMultiplier
     * @param priceMultiplier
     */
    public Asset(int amountMultiplier, int priceMultiplier) {
        Random random = new Random();
        int amount = (random.nextInt(10, 50)) * amountMultiplier;
        float currentPrice = random.nextFloat(1, 10) * priceMultiplier;
        List<Float> priceHistory = new ArrayList<>();
        this.initialAmount = amount;
        this.currentPrice = currentPrice;
        this.priceHistory = priceHistory;
    }

    /**
     * Adds value to the history of prices of an asset
     *
     * @param value
     */
    public void addPriceHistoryValue(float value) {
        priceHistory.add(value);
    }

    /**
     * Abstract method for updating the price of this
     *
     * @param changeFactor
     */
    public abstract void updatePrice(float changeFactor);

    /**
     *
     * @return
     */
    public abstract String getName();

    /**
     *
     * @return
     */
    public int getInitialAmount() {
        return initialAmount;
    }

    /**
     *
     * @param initialAmount
     */
    public void setInitialAmount(int initialAmount) {
        this.initialAmount = initialAmount;
    }

    /**
     *
     * @return
     */
    public float getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Sets current price. Checks if not below zero and throws an exception
     *
     * @param currentPrice
     */
    public void setCurrentPrice(float currentPrice) {
        try {
            if (currentPrice <= 0) {
                throw new PriceBelowZeroException("There was an attempt to set asset's price to value <= 0");
            }
            this.currentPrice = currentPrice;

        } catch (PriceBelowZeroException e) {
            this.currentPrice = (float) 0.5;
            System.out.println("Exception caught: " + e);
        }
    }

    /**
     *
     * @return
     */
    public List<Float> getPriceHistory() {
        return priceHistory;
    }

    /**
     * Abstract method
     *
     * @return
     */
    public abstract String assetDetails();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Asset other = (Asset) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Asset{" + "amount=" + initialAmount + '}';
    }

}
