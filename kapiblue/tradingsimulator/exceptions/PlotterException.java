/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package kapiblue.tradingsimulator.exceptions;

/**
 * Raised when there is an incorrect input for the Plotter
 *
 * @author kapib
 */
public class PlotterException extends Exception {

    /**
     * Creates a new instance of <code>PlotterException</code> without detail
     * message.
     */
    public PlotterException() {
    }

    /**
     * Constructs an instance of <code>PlotterException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PlotterException(String msg) {
        super(msg);
    }
}
