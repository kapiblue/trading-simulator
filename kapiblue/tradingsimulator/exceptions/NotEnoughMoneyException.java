/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised when a Market Participant has not enough money to buy an asset
 *
 * @author kapib
 */
public class NotEnoughMoneyException extends Exception {

    /**
     * Creates a new instance of <code>NotEnoughMoney</code> without detail
     * message.
     */
    public NotEnoughMoneyException() {
    }

    /**
     * Constructs an instance of <code>NotEnoughMoney</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
