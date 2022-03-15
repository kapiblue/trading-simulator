/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised when a participant has no assets in the wallet
 *
 * @author kapib
 */
public class NoAssetsInWalletException extends Exception {

    /**
     * Creates a new instance of <code>NoAssetsInWalletException</code> without
     * detail message.
     */
    public NoAssetsInWalletException() {
    }

    /**
     * Constructs an instance of <code>NoAssetsInWalletException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAssetsInWalletException(String msg) {
        super(msg);
    }
}
