/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.participants;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import kapiblue.tradingsimulator.World;
import kapiblue.tradingsimulator.assets.FundUnit;
import kapiblue.tradingsimulator.exceptions.NoExistingInvestmentFundException;
import kapiblue.tradingsimulator.markets.Market;
import kapiblue.tradingsimulator.participants.MarketParticipant;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Market participant. Can buy through investment funds or directly
 *
 * @author kapib
 */
public class Investor extends MarketParticipant {

    private String firstName;
    private String lastName;
    private String tradingID;
    private Set<FundUnit> fundUnits;

    /**
     * Has the World. Fund units are stored separately from equities
     *
     * @param world
     */
    public Investor(World world) {
        super(world);
        Random random = new Random();
        Generator generator = new Generator();
        this.firstName = generator.generateRandomWord(6);
        this.lastName = generator.generateRandomWord(8);
        this.tradingID = generator.generateRandomWord(6);
        this.fundUnits = new HashSet<>();
    }

    @Override
    public void run() {
        while (true) {
            int bearBullRatio = getTheWorld().getBearBullRatio();

            if (bearBullRatio < 50 || Math.random() < 0.3) {
                this.generateSellTransaction();
            }

            if (bearBullRatio < 60 || Math.random() < 0.7) {
                this.generateBuyTransaction();
            }

            this.generateBuyTransaction();
            this.generateBuyTransaction();

            if (Math.random() < 0.15) {
                buyThroughInvestmentFund();
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
     * Buys units of a found investment fund. Throws an exception if no funds
     * were created
     */
    public void buyThroughInvestmentFund() {
        InvestmentFund fundToJoin = findInvestmentFound();
        try {
            if (fundToJoin == null) {
                throw new NoExistingInvestmentFundException("There are no investment funds created");
            }
            synchronized (this) {
                int moneyInvested = (int) (this.getInvestmentBudget() * (new Random().nextDouble(0.2, 0.7)));
                this.setInvestmentBudget(getInvestmentBudget() - moneyInvested);

                if (!fundToJoin.getFundParticipants().contains(this)) {
                    fundToJoin.addParticipant(this, moneyInvested);

                    String fundName = fundToJoin.getFundName();
                    float fundPart = (float) moneyInvested / fundToJoin.getTotalBudget();
                    FundUnit fundUnit = new FundUnit(fundName, moneyInvested, fundPart);

                    this.fundUnits.add(fundUnit);
                }
            }
        } catch (NoExistingInvestmentFundException e) {
            System.out.println("Exception caught: " + e);
        }
    }

    /**
     * Finds an investment fund
     *
     * @return
     */
    public synchronized InvestmentFund findInvestmentFound() {
        MarketParticipant fundToJoin = null;

        for (MarketParticipant participant : getTheWorld().getMarketParticipants()) {
            if (participant instanceof InvestmentFund) {
                fundToJoin = participant;
                break;
            }
        }
        return (InvestmentFund) fundToJoin;
    }

    @Override
    public String participantDetails() {
        return "Name: " + firstName + "\n" + "Surname: " + lastName + "\n"
                + "Trading ID: " + tradingID + "\n" + "Investment budget: " + getInvestmentBudget() + "\n"
                + "Wallet: " + getWallet().getEquities() + "\n" + "Fund units: " + fundUnits;
    }

    @Override
    public String toString() {
        return "Investor " + firstName + " " + lastName;
    }
;
}
