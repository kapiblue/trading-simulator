/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.participants;

import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import kapiblue.tradingsimulator.World;
import kapiblue.tradingsimulator.assets.Asset;
import kapiblue.tradingsimulator.assets.Commodity;
import kapiblue.tradingsimulator.assets.Company;
import kapiblue.tradingsimulator.assets.Currency;
import kapiblue.tradingsimulator.assets.Equity;
import kapiblue.tradingsimulator.exceptions.NoAssetsInWalletException;
import kapiblue.tradingsimulator.exceptions.NotEnoughMoneyException;
import kapiblue.tradingsimulator.markets.Market;

/**
 * Parent class for investors and founds
 *
 * @author kapib
 */
public abstract class MarketParticipant extends Thread {

    private Wallet wallet;
    private int investmentBudget;
    private final World theWorld;

    /**
     * Has a wallet and access to the World
     *
     * @param world
     */
    public MarketParticipant(World world) {
        Random random = new Random();
        Wallet wallet = new Wallet();
        this.wallet = wallet;
        this.investmentBudget = random.nextInt(40, 100) * 1000;
        this.theWorld = world;
    }

    public void run() {
        while (true) {
            int bearBullRatio = theWorld.getBearBullRatio();
            if (bearBullRatio < 10 || Math.random() < 0.4) {
                generateSellTransaction();
            }
            if (bearBullRatio < 50 || Math.random() < 0.6) {
                generateBuyTransaction();
            }

            try {
                int sleepTime = calculateSleepTime();
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(MarketParticipant.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Randomly selects an equity from the participant's wallet, removes it from
     * the wallet and sends to a relevant market
     *
     */
    public synchronized void generateSellTransaction() {
        int numberOfAssets = this.wallet.getEquities().size();
        try {
            if (numberOfAssets < 1) {
                throw new NoAssetsInWalletException("The participant has no assets in the wallet");
            }
            int index = (int) (Math.random() * numberOfAssets);
            Equity equity = this.wallet.getEquities().get(index);
            int price = (int) (equity.getAmount() * equity.getAsset().getCurrentPrice());
            this.setInvestmentBudget(this.getInvestmentBudget() + price);

            addEquityToRelevantMarket(equity);
            theWorld.getGui().addEquitytoEquityList(equity);

            this.wallet.deleteEquity(equity);
            System.out.println(this + " sold " + equity + " wallet: " + this.getWallet());
        } catch (NoAssetsInWalletException e) {
            System.out.println("Exception caught: " + e);
        }
    }

    /**
     * Generates buy transactions
     */
    public synchronized void generateBuyTransaction() {
        findRandomMarket();
    }

    /**
     * Selects a random market to search for assets to buy
     */
    public void findRandomMarket() {
        int marketType = new Random().nextInt(2);

        if (marketType == 0) {
            selectAffordableFromMarket(theWorld.getStockMarkets());
        } else if (marketType == 1) {
            selectAffordableFromMarket(theWorld.getCommodityMarkets());
        } else if (marketType == 2) {
            selectAffordableFromMarket(theWorld.getCurrencyMarkets());
        }
    }

    /**
     * Searches markets of a given typy until an affordable equity is found. If
     * found, it is removed from the market and added to participant's wallet.
     * Otherwise the investment budget is increased
     *
     * @param markets
     */
    public synchronized void selectAffordableFromMarket(Set<Market> markets) {
        Equity equityForSale = null;
        Boolean foundAffordableEquity = false;

        for (Market market : markets) {
            for (Equity equity : market.getEquities()) {
                Asset asset = equity.getAsset();
                int price = (int) (equity.getAmount() * asset.getCurrentPrice() * market.getTransactionMargin());

                if (price <= this.investmentBudget) {
                    equityForSale = equity;
                    foundAffordableEquity = true;
                    this.setInvestmentBudget(getInvestmentBudget() - price);
                    break;
                }
            }
            if (foundAffordableEquity == true) {
                this.wallet.addEquityToWallet(equityForSale);
                theWorld.getGui().removeEquityFromEquityList(equityForSale);
                market.removeEquity(equityForSale);

                System.out.println(this + " bought " + equityForSale + " wallet: " + this.getWallet());
                break;
            } else {
                increaseBudget();
            }
        }
    }

    /*
    Adds equity to a random market from the set of given destination markets
     */
    /**
     *
     * @param destinationMarkets
     * @param equity
     */
    public synchronized void addEquityToRandomMarket(Set<Market> destinationMarkets, Equity equity) {
        int size = destinationMarkets.size();

        int index = new Random().nextInt(size);
        int i = 0;
        for (Market market : destinationMarkets) {
            if (i == index) {
                market.addEquity(equity);
            }
            i++;
        }
    }

    /**
     * Adds equity to an appropriate market based on class
     *
     * @param equity
     */
    public synchronized void addEquityToRelevantMarket(Equity equity) {
        Asset asset = equity.getAsset();
        if (asset instanceof Company) {
            addEquityToRandomMarket(theWorld.getStockMarkets(), equity);
        }
        if (asset instanceof Commodity) {
            addEquityToRandomMarket(theWorld.getCommodityMarkets(), equity);
        }
        if (asset instanceof Currency) {
            addEquityToRandomMarket(theWorld.getCurrencyMarkets(), equity);
        }
    }

    /**
     * Increases participant's budget
     */
    public void increaseBudget() {
        this.setInvestmentBudget(this.investmentBudget + 5000);
    }

    /**
     * Calculates participant's thread sleep time to approximately achieve the
     * amount of transactions per minute specified by the user
     *
     * @return
     */
    public int calculateSleepTime() {
        int transactionsPerMinute = theWorld.getTransactionsPerMinute();
        int numberOfParticipants = theWorld.getMarketParticipants().size();

        return numberOfParticipants * 60 * 1000 / (transactionsPerMinute + 10) * 2 + (new Random()).nextInt(100, 500);
    }

    /**
     *
     * @return
     */
    public Wallet getWallet() {
        return this.wallet;
    }

    /**
     *
     * @return
     */
    public int getInvestmentBudget() {
        return investmentBudget;
    }

    /**
     *
     * @param investmentBudget
     */
    public void setInvestmentBudget(int investmentBudget) {
        this.investmentBudget = investmentBudget;
    }

    /**
     *
     * @return
     */
    public World getTheWorld() {
        return theWorld;
    }

    /**
     * A method used by child classes
     *
     * @return
     */
    public String participantDetails() {
        return "details";
    }

    @Override
    public String toString() {
        return "MarketParticipant{" + "wallet=" + wallet + '}';
    }
}
