/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

/**
 * Equity stores an Asset and its amount
 *
 * @author kapib
 */
public class Equity {

    private int amount;
    private Asset asset;

    /**
     *
     * @param amount
     * @param asset
     */
    public Equity(int amount, Asset asset) {
        this.amount = amount;
        this.asset = asset;
    }

    /**
     * Formatted string with the details of this equity
     *
     * @return
     */
    public String equityDetails() {
        return "Amount: " + amount + "\n" + asset.assetDetails();
    }

    /**
     * Increases the amount of this equity by value
     *
     * @param value
     */
    public void addToAmount(int value) {
        this.amount += value;
    }

    /**
     * Getter for amount of equity
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Getter for the Asset associated with this equity
     *
     * @return
     */
    public Asset getAsset() {
        return asset;
    }

    @Override
    public String toString() {
        return asset + " " + amount;
    }

}
