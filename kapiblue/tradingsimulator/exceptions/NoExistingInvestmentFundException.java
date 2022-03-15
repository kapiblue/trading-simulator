/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised if failed to find an existing investment fund (No funds were created)
 *
 * @author kapib
 */
public class NoExistingInvestmentFundException extends Exception {

    /**
     * Creates a new instance of <code>NoExistingInvestmentFundException</code>
     * without detail message.
     */
    public NoExistingInvestmentFundException() {
    }

    /**
     * Constructs an instance of <code>NoExistingInvestmentFundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoExistingInvestmentFundException(String msg) {
        super(msg);
    }
}
