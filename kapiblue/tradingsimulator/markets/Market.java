/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.markets;

import java.text.DecimalFormat;
import kapiblue.tradingsimulator.assets.Asset;
import java.util.*;
import kapiblue.tradingsimulator.assets.Company;
import kapiblue.tradingsimulator.assets.Equity;
import kapiblue.tradingsimulator.assets.Index;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * General market. If this is a stock market, the indexes is not null.
 * Otherwise, it is null. Indexes field is the only difference between market
 * types.
 *
 * @author kapib
 */
public class Market {

    private String name;
    private String country;
    private String tradingCurrency;
    private String city;
    private String address;
    private float transactionMargin;
    private Set<Equity> equities;
    private List<Index> indexes;

    /**
     * By default indexes is null
     */
    public Market() {
        Random random = new Random();
        Generator generator = new Generator();

        this.name = generator.generateRandomWord(8);
        this.country = generator.generateRandomWord(7);
        this.tradingCurrency = generator.generateRandomWord(3);
        this.city = generator.generateRandomWord(5);
        this.address = generator.generateRandomWord(7);
        this.transactionMargin = random.nextFloat(1, (float) 1.15);

        Set<Equity> equities = new HashSet<>();
        this.equities = equities;
        this.indexes = null;
    }

    /*
    Adds a stock index to the market
     */
    /**
     *
     * @param index
     */
    public void addIndex(Index index) {
        this.indexes.add(index);
    }

    /**
     * Adds an equity for sale to the market
     *
     * @param equity
     */
    public synchronized void addEquity(Equity equity) {
        this.equities.add(equity);
    }

    /**
     * Removes the equity from the market
     *
     * @param equity
     */
    public synchronized void removeEquity(Equity equity) {
        this.equities.remove(equity);
    }

    /**
     * Formatted string with market's details
     *
     * @return
     */
    public String marketDetails() {
        DecimalFormat form = new DecimalFormat("0.00%");

        return "Market: " + name + "\n" + "Country: " + country + "\n" + "Trading currency: " + tradingCurrency + "\n"
                + "City: " + city + "\n" + "Address: " + address + "\n" + "Transaction margin: " + form.format(transactionMargin - 1) + "\n";

    }

    @Override
    public String toString() {
        return "Market " + name;
    }

    /**
     * Getter for transaction margin
     *
     * @return
     */
    public float getTransactionMargin() {
        return transactionMargin;
    }

    /**
     * Getter for equities available at this market
     *
     * @return
     */
    public Set<Equity> getEquities() {
        return equities;
    }

    /**
     * Getter for indexes available at this market
     *
     * @return
     */
    public List<Index> getIndexes() {
        return indexes;
    }

    /**
     * Setter for indexes, used only for stock markets
     *
     * @param indexes
     */
    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

}
