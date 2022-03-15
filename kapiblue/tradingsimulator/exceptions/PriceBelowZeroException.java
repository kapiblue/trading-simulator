/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised if the price of an asset falls below zero
 *
 * @author kapib
 */
public class PriceBelowZeroException extends Exception {

    /**
     * Creates a new instance of <code>PriceBelowZeroException</code> without
     * detail message.
     */
    public PriceBelowZeroException() {
    }

    /**
     * Constructs an instance of <code>PriceBelowZeroException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PriceBelowZeroException(String msg) {
        super(msg);
    }
}
