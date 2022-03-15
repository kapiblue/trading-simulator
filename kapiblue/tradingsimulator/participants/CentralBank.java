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
import kapiblue.tradingsimulator.assets.Asset;
import kapiblue.tradingsimulator.assets.Equity;
import kapiblue.tradingsimulator.exceptions.CentralBankReserveException;
import kapiblue.tradingsimulator.exceptions.NoAssetsInWalletException;
import kapiblue.tradingsimulator.markets.Market;

/**
 * Singlton class. Stores newly created assets an sells them in small portions.
 * Reserve is the minimum amount of each asset that has to stay in the bank
 */
public class CentralBank extends MarketParticipant {

    private final static int RESERVE = 5000;

    private static CentralBank bank = null;
    private int assetsNumber = 0;

    /**
     * Gets an instance
     *
     * @param world
     * @return
     */
    public static CentralBank getInstance(World world) {
        if (bank == null) {
            Wallet wallet = new Wallet();
            bank = new CentralBank(wallet, world);
        }
        return bank;
    }

    /**
     * Class constructor
     *
     * @param wallet
     * @param world
     */
    public CentralBank(Wallet wallet, World world) {
        super(world);
    }

    /**
     * Updates simulation time, creates more participants if needed, udates
     * prices, generates central bank transactions
     */
    @Override
    public void run() {
        while (true) {
            getTheWorld().updateCurrentDate();
            getTheWorld().getGui().displayCurrentDate();

            int currentNumberOfAssets = getWallet().getEquities().size();
            if (currentNumberOfAssets > this.assetsNumber) {
                createMoreParticipants();
                this.assetsNumber = currentNumberOfAssets;
            }

            updatePrices();
            generateSellTransaction();

            int dayLength = World.getDAY_LENGTH();
            try {
                Thread.sleep(dayLength);
            } catch (InterruptedException ex) {
                Logger.getLogger(CentralBank.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Generates 3 buy transactions of the central bank
     *
     */
    @Override
    public synchronized void generateSellTransaction() {
        for (int i = 0; i < 3; i++) {
            int numberOfAssets = this.getWallet().getEquities().size();
            try {
                if (numberOfAssets < 1) {
                    throw new NoAssetsInWalletException("Please create some assets");
                }
                int index = (int) (Math.random() * numberOfAssets);
                Equity randomEquity = this.getWallet().getEquities().get(index);

                try {
                    if (randomEquity.getAmount() <= RESERVE) {
                        throw new CentralBankReserveException("Not enough equity of this type in the Central Bank " + randomEquity.getAsset());
                    }
                    Asset soldAsset = randomEquity.getAsset();

                    int amount = new Random().nextInt(1, 20) * 100;
                    Equity soldEquity = new Equity(amount, soldAsset);
                    randomEquity.addToAmount(-1 * amount);

                    getTheWorld().getGui().addEquitytoEquityList(soldEquity);
                    addEquityToRelevantMarket(soldEquity);
                } catch (CentralBankReserveException ex) {
                    System.out.println("Exception caught: " + ex);
                }

            } catch (NoAssetsInWalletException e) {
                System.out.println("Exception caught: " + e);
            }

        }
    }

    /**
     * Updates prices of all assets in the central bank
     */
    public synchronized void updatePrices() {
        float changeFactor = (float) (0.5 - (float) getTheWorld().getBearBullRatio() / 100);

        for (Equity equity : getWallet().getEquities()) {
            equity.getAsset().updatePrice(changeFactor);
        }
    }

    /**
     * Creates two investors and an investment fund
     */
    private void createMoreParticipants() {
        World theWorld = getTheWorld();

        theWorld.createInvestor();
        theWorld.createInvestor();
        theWorld.createInvestmentFund();
    }
}
