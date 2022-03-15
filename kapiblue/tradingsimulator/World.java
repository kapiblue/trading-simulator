/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator;

import kapiblue.tradingsimulator.markets.Market;
import kapiblue.tradingsimulator.assets.Company;
import kapiblue.tradingsimulator.assets.Currency;
import kapiblue.tradingsimulator.assets.Commodity;
import kapiblue.tradingsimulator.participants.MarketParticipant;
import kapiblue.tradingsimulator.participants.Investor;
import kapiblue.tradingsimulator.participants.CentralBank;
import java.util.*;
import kapiblue.tradingsimulator.assets.Equity;
import kapiblue.tradingsimulator.assets.Index;
import kapiblue.tradingsimulator.exceptions.NoIndexException;
import kapiblue.tradingsimulator.exceptions.NoMarketException;
import kapiblue.tradingsimulator.participants.InvestmentFund;
import kapiblue.tradingsimulator.utilities.GUI;

/**
 * This Singleton class binds all classes together
 *
 * @author kapib
 */
public class World {

    /**
     * Const DAY_LENGTH is the simulation day length in milliseconds
     */
    public static final int DAY_LENGTH = 5000;

    private static World w = null;
    private int transactionsPerMinute;
    private int bearBullRatio;
    private Set<Market> stockMarkets;
    private Set<Market> currencyMarkets;
    private Set<Market> commodityMarkets;
    private Set<MarketParticipant> marketParticipants;
    private List<Index> stockIndexes;
    private CentralBank centralBank;
    private GUI gui;
    private Date startDate;
    private Date currentDate;

    /**
     * Initially all collections are empty. Start date and current date are set
     * to user's local time.
     */
    public World() {
        Random random = new Random();
        this.transactionsPerMinute = random.nextInt(500);
        this.bearBullRatio = random.nextInt(100);
        Set<Market> stockMarkets = new HashSet<>();
        Set<Market> currencyMarkets = new HashSet<>();
        Set<Market> commodityMarkets = new HashSet<>();
        Set<MarketParticipant> marketParticipants = new HashSet<>();
        List<Index> stockIndexes = new ArrayList<>();
        this.stockMarkets = stockMarkets;
        this.currencyMarkets = currencyMarkets;
        this.commodityMarkets = commodityMarkets;
        this.marketParticipants = marketParticipants;
        this.startDate = new Date();
        this.currentDate = new Date();
        this.stockIndexes = stockIndexes;
    }

    /**
     * Creates a stock market and adds it to this stock markets collection.
     * Creates a list of indexes and sets it to the market. Adds the market to
     * GUI's market list.
     */
    public void createStockMarket() {
        Market market = new Market();
        List<Index> indexes = new ArrayList<>();
        market.setIndexes(indexes);
        gui.getMarketModel().addElement(market);
        this.stockMarkets.add(market);
    }

    /**
     * Creates a stock index.Adds the index to a random stock market. Adds the
     * index to index list of this. Adds the index to GUI's index list.
     *
     */
    public void createIndex() {
        Index index = new Index();
        Market randomMarket = findRandomMarket(stockMarkets);
        try {
            if (randomMarket == null) {
                throw new NoMarketException("Create a stock market first");
            }
            randomMarket.addIndex(index);
            stockIndexes.add(index);
            gui.addIndexToIndexList(index);
        } catch (NoMarketException e) {
            System.out.println("Exception caught: " + e);
        }

    }

    /**
     * Finds and returns a random market
     *
     * @param markets - a set of markets to pick a random market from
     * @return
     */
    public Market findRandomMarket(Set<Market> markets) {
        int numberOfMarkets = markets.size();
        Market randomMarket = null;
        if (numberOfMarkets == 0) {
            return randomMarket;
        }
        int index = (new Random()).nextInt(numberOfMarkets);
        int i = 0;
        for (Market market : markets) {
            if (i == index) {
                randomMarket = market;
            }
            i++;
        }
        return randomMarket;
    }

    /**
     * Creates a currency market and adds it to currency markets set. Adds the
     * market to GUI's market list.
     */
    public void createCurrencyMarket() {
        Market market = new Market();
        gui.getMarketModel().addElement(market);
        this.currencyMarkets.add(market);
    }

    /**
     * Creates a currency market and adds it this currency markets set. Adds the
     * market to GUI's market list.
     */
    public void createCommodityMarket() {
        Market market = new Market();
        gui.getMarketModel().addElement(market);
        this.commodityMarkets.add(market);
    }

    /**
     * Creates a currency. Creates a new equity with the total initial amount.
     * Adds the currency to the central bank.
     */
    public void createCurrency() {
        Currency currency = new Currency();
        Equity equity = new Equity(currency.getInitialAmount(), currency);
        addAssetToCentralBank(equity);
    }

    /**
     * Creates a company. Adds the company to a ranodm index. Creates a new
     * equity with the total initial amount of shares. Adds the company to the
     * central bank.
     */
    public void createCompany() {
        Company company = new Company(this);
        addToRandomIndex(company);
        Equity equity = new Equity(company.getTotalShares(), company);
        addAssetToCentralBank(equity);
    }

    /**
     * Adds company to a random stock index
     *
     * @param company
     */
    public void addToRandomIndex(Company company) {
        int numberOfIndexes = this.stockIndexes.size();

        try {
            if (numberOfIndexes == 0) {
                throw new NoIndexException("Please create an Index first");
            }

            int idx = (new Random()).nextInt(numberOfIndexes);
            Index stockIndex = this.stockIndexes.get(idx);
            stockIndex.addCompanyToIndex(company);

        } catch (NoIndexException e) {
            System.out.println("Exception caugth: " + e);
        }
    }

    /**
     * Creates a commodity. Creates a new equity with the total initial amount.
     * Adds the commodity to the central bank.
     *
     */
    public void createCommodity() {
        Commodity commodity = new Commodity();
        Equity equity = new Equity(commodity.getInitialAmount(), commodity);
        addAssetToCentralBank(equity);
    }

    /**
     * Adds the given equity to the central bank's wallet
     *
     * @param equity
     */
    public void addAssetToCentralBank(Equity equity) {
        this.centralBank.getWallet().addEquityToWallet(equity);
    }

    /**
     * Adds a participant to the set of participants of this
     *
     * @param participant
     */
    public void addMarketParticipant(MarketParticipant participant) {
        this.marketParticipants.add(participant);
    }

    /**
     * Creates an Investor. Starts its thread and adds it to the GUI's
     * participant list.
     *
     */
    public void createInvestor() {
        Investor investor = new Investor(this);
        investor.start();
        gui.getParticipantModel().addElement(investor);
        addMarketParticipant(investor);
    }

    /**
     * Creates an Investment Fund. Starts its thread and adds it to the GUI's
     * participant list.
     *
     */
    public void createInvestmentFund() {
        InvestmentFund investmentFund = new InvestmentFund(this);
        investmentFund.start();
        addMarketParticipant(investmentFund);
        gui.getParticipantModel().addElement(investmentFund);
    }

    /**
     * Project's main function. Initializes this and the central bank.
     * Initializes GUI. Starts the thread of the central bank
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        World theWorld = World.getInstance();

        theWorld.centralBank = theWorld.centralBank.getInstance(theWorld);

        theWorld.gui = new GUI(theWorld);

        theWorld.centralBank.start();
    }

    /**
     * Getter for the total transactions in a minute (real time)
     *
     * @return
     */
    public int getTransactionsPerMinute() {
        return transactionsPerMinute;
    }

    /**
     * Sets transactions per minute
     *
     * @param transactionsPerMinute
     */
    public void setTransactionsPerMinute(int transactionsPerMinute) {
        this.transactionsPerMinute = transactionsPerMinute;
    }

    /**
     * Gets the ratio. Value in range 0-100
     *
     * @return
     */
    public int getBearBullRatio() {
        return bearBullRatio;
    }

    /**
     * Sets the ratio
     *
     * @param bearBullRatio
     */
    public void setBearBullRatio(int bearBullRatio) {
        this.bearBullRatio = bearBullRatio;
    }

    /**
     * Getter for all available stock markets
     *
     * @return
     */
    public Set<Market> getStockMarkets() {
        return stockMarkets;
    }

    /**
     * Getter for all available currency markets
     *
     * @return
     */
    public Set<Market> getCurrencyMarkets() {
        return currencyMarkets;
    }

    /**
     * Getter for all available commodity markets
     *
     * @return
     */
    public Set<Market> getCommodityMarkets() {
        return commodityMarkets;
    }

    /**
     * Getter for all market participants. Investors and Investment Funds
     *
     * @return
     */
    public Set<MarketParticipant> getMarketParticipants() {
        return marketParticipants;
    }

    /**
     * Getter for the singleton instance of the central bank
     *
     * @return
     */
    public CentralBank getCentralBank() {
        return centralBank;
    }

    /**
     * Getter for the GUI
     *
     * @return
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * Gets current simulation date
     *
     * @return
     */
    public Date getCurrentDate() {
        return currentDate;
    }

    /**
     * Sets current simulation date
     *
     * @param currentDate
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * Updates current simulation date by one day
     */
    public void updateCurrentDate() {
        long millisecondsPerDay = 1000 * 60 * 60 * 24;
        this.currentDate = new Date(currentDate.getTime() + millisecondsPerDay);
    }

    /**
     * Gets starting date of the simulation
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets the simulation day length
     *
     * @return
     */
    public static int getDAY_LENGTH() {
        return DAY_LENGTH;
    }

    /**
     * Gets an instance of World
     *
     * @return
     */
    public static World getInstance() {
        if (w == null) {
            w = new World();
        }
        return w;
    }
}
