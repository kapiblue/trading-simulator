/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import static java.lang.Thread.sleep;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import kapiblue.tradingsimulator.World;
import kapiblue.tradingsimulator.exceptions.CentralBankReserveException;
import kapiblue.tradingsimulator.markets.Market;
import kapiblue.tradingsimulator.participants.CentralBank;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Company asset
 *
 * @author kapib
 */
public class Company extends Asset implements Runnable {

    private String name;
    private Date dateIPO;
    private float shareValueIPO;
    private float openingPrice;
    private float currentPrice;
    private float minimalPrice;
    private float maximalPrice;
    private float profit;
    private float revenue;
    private int capital;
    private int tradingVolume;
    private float totalSales;
    private int totalShares;
    private World theWorld;
    private Thread companyThread;

    /**
     * Constructor starts company Thread
     *
     * @param world
     */
    public Company(World world) {
        super(1000, 100);
        Random random = new Random();
        Generator generator = new Generator();
        this.name = generator.generateRandomWord(8);
        this.dateIPO = generator.generateRandomDate();
        this.shareValueIPO = random.nextFloat(50);
        this.openingPrice = this.getCurrentPrice();
        this.currentPrice = openingPrice + random.nextFloat(-4, 20);
        this.minimalPrice = openingPrice - random.nextFloat(3);
        this.maximalPrice = openingPrice + random.nextFloat(20);
        this.profit = random.nextFloat() * openingPrice;
        this.revenue = random.nextFloat(10, 1000);
        this.capital = random.nextInt(10, 100) * 1000;
        this.tradingVolume = random.nextInt(10, 1000);
        this.totalSales = random.nextInt(10, 1000);
        this.totalShares = random.nextInt(10, 500) * 1000;
        this.theWorld = world;
        this.companyThread = new Thread(this);
        this.companyThread.start();
    }

    /**
     * Generates revenue and profit
     */
    @Override
    public void run() {
        while (true) {
            generateRevenue();
            generateProfit();

            if (Math.random() < 0.1) {
                this.increaseTotalShares();
            }
            int dayLength = World.getDAY_LENGTH();
            try {
                sleep(dayLength);
            } catch (InterruptedException ex) {
                Logger.getLogger(Company.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Generates and sets random revenue
     */
    public void generateRevenue() {
        Random random = new Random();
        revenue = 1000 * random.nextInt(5, 250);
        this.revenue = revenue;
    }

    /**
     * Generates and sets a random profit relative to revenue
     */
    public void generateProfit() {
        Random random = new Random();
        profit = Float.max(this.revenue - 1000 * random.nextInt(5, 50), 0);
        this.profit = profit;
    }

    /**
     * Doubles total shares of a company
     */
    public synchronized void increaseTotalShares() {
        int currentTotalShares = this.totalShares;
        currentTotalShares = currentTotalShares * 2;

        this.totalShares = currentTotalShares;
    }

    /**
     * Updates current price and minimal, maximal and opening prices
     *
     * @param changeFactor
     */
    @Override
    public void updatePrice(float changeFactor) {
        Random random = new Random();

        float tempCurrentPrice = getCurrentPrice();
        addPriceHistoryValue(tempCurrentPrice);
        float newPrice = tempCurrentPrice + random.nextFloat(10, tempCurrentPrice / 5 + 12) * changeFactor;

        setCurrentPrice(newPrice);
        newPrice = getCurrentPrice();

        currentPrice = newPrice;
        openingPrice = newPrice + random.nextFloat(3);
        minimalPrice = newPrice * (1 - random.nextFloat((float) 0.1));
        maximalPrice = newPrice * (1 + random.nextFloat((float) 0.1));
    }

    /**
     * Buys from the central bank the user specified amount of shares if
     * available
     */
    /**
     *
     * @param amount
     */
    public void buyOut(int amount) {
        int soldAmount = 0;

        synchronized (companyThread) {
            Equity equity = findInCentralBank();
            try {
                try {
                    if (equity.getAmount() <= amount) {
                        throw new CentralBankReserveException("Not enough shares for buy out");
                    }
                    equity.addToAmount(-amount);
                } catch (CentralBankReserveException e) {
                    System.out.println("Exception caught: " + e);
                }
            } catch (NullPointerException e) {
                System.out.println("Company not found in the Central Bank");
            }
        }
        System.out.println("Successful buy out");
    }

    /*
    Returns equity concerning this (company) form the central bank
     */
    private synchronized Equity findInCentralBank() {
        CentralBank centralBank = theWorld.getCentralBank();
        Equity centralBankEquity = null;

        for (Equity equity : centralBank.getWallet().getEquities()) {
            Asset foundAsset = equity.getAsset();

            if (foundAsset.equals(this)) {
                centralBankEquity = equity;
            }
        }
        return centralBankEquity;
    }

    /**
     * Formatted string with company's deatils
     *
     * @return
     */
    @Override
    public String assetDetails() {
        DecimalFormat form = new DecimalFormat("0.00");
        return "Company name: " + name + "\n" + "IPO date: " + dateIPO + "\n" + "IPO share value: " + form.format(shareValueIPO) + "\n"
                + "Opening price: " + form.format(openingPrice) + "\n" + "Current Price: " + form.format(currentPrice) + "\n" + "Minimal price: " + form.format(minimalPrice) + "\n"
                + "Maximal price: " + form.format(maximalPrice) + "\n" + "Profit: " + profit + "\n" + "Revenue: " + form.format(revenue) + "\n"
                + "Capital: " + capital + "\n" + "Trading volume: " + tradingVolume + "\n" + "Total sales: " + totalSales + "\n" + "Total shares: " + totalShares + "\n";
    }

    @Override
    public String toString() {
        return "Company " + name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.dateIPO);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.dateIPO, other.dateIPO)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int getTotalShares() {
        return totalShares;
    }

}
