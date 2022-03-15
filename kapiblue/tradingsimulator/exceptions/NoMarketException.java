/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised if no market was created
 *
 * @author kapib
 */
public class NoMarketException extends Exception {

    /**
     * Creates a new instance of <code>NoMarketException</code> without detail
     * message.
     */
    public NoMarketException() {
    }

    /**
     * Constructs an instance of <code>NoMarketException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoMarketException(String msg) {
        super(msg);
    }
}
