/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import java.text.DecimalFormat;

/**
 * Fund Unit can be bought form a Fund by an Investor
 *
 * @author kapib
 */
public class FundUnit {

    private String fundName;
    private int value;
    private float fundPart; //  = value / total investment fund's capital

    /**
     *
     * @param fundName
     * @param value
     * @param fundPart
     */
    public FundUnit(String fundName, int value, float fundPart) {
        this.fundName = fundName;
        this.value = value;
        this.fundPart = fundPart;
    }

    @Override
    public String toString() {
        DecimalFormat form = new DecimalFormat("0.00%");
        return "Name: " + fundName + " Value: " + value + " Part of the fund: " + form.format(fundPart) + "\n";
    }

}
