/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised if there are too few equities of certain type remaining in the Central
 * Bank The reserve amount is specified by RESERVE
 *
 * @author kapib
 */
public class CentralBankReserveException extends Exception {

    /**
     * Creates a new instance of <code>CentralBankReserveException</code>
     * without detail message.
     */
    public CentralBankReserveException() {
    }

    /**
     * Constructs an instance of <code>CentralBankReserveException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CentralBankReserveException(String msg) {
        super(msg);
    }
}
