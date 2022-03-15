/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised when no stock index was created
 *
 * @author kapib
 */
public class NoIndexException extends Exception {

    /**
     * Creates a new instance of <code>NoIndexException</code> without detail
     * message.
     */
    public NoIndexException() {
    }

    /**
     * Constructs an instance of <code>NoIndexException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoIndexException(String msg) {
        super(msg);
    }
}
