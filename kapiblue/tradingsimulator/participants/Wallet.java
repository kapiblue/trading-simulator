/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.participants;

import java.util.ArrayList;
import java.util.List;
import kapiblue.tradingsimulator.assets.Equity;

/**
 * Wallet for storing equities
 *
 * @author kapib
 */
public class Wallet {

    private List<Equity> equities = new ArrayList<>();

    /**
     * Constructor
     */
    public Wallet() {
    }

    /**
     * Adds equity to the wallet
     *
     * @param equity
     */
    public synchronized void addEquityToWallet(Equity equity) {
        equities.add(equity);
    }

    /**
     * Removes equity from the wallet
     *
     * @param equity
     */
    public synchronized void deleteEquity(Equity equity) {
        equities.remove(equity);
    }

    /**
     * Gets equities
     *
     * @return
     */
    public List<Equity> getEquities() {
        return this.equities;
    }

    @Override
    public String toString() {
        return "Assets: " + equities;
    }

}
