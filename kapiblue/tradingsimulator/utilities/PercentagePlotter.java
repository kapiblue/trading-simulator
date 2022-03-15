/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.utilities;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import kapiblue.tradingsimulator.assets.Equity;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * Plotting class for multiple assets on a percentage scale. Inherits from
 * JFrame. This is a window with the graph
 *
 */
public class PercentagePlotter extends JFrame {

    /**
     * Constructor, loads the dataset, creates a chart and displays the frame
     *
     * @param currentDate
     * @param equities
     */
    public PercentagePlotter(Date currentDate, List<Equity> equities) {
        final XYDataset dataset = createDataset(currentDate, equities);
        final JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);

        pack();
        setTitle("Assets percentage plot");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    /**
     * Creates a dataset with a time series of the input list of equities
     *
     * @param current simulation date
     * @param a list of equities to be plotted
     */
    private XYDataset createDataset(Date currentDate, List<Equity> equities) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for (Equity equity : equities) {
            final TimeSeries series = new TimeSeries(equity.getAsset().getName());
            RegularTimePeriod day = new Day(currentDate);

            float initialPrice = equity.getAsset().getPriceHistory().get(0);
            for (float value : equity.getAsset().getPriceHistory()) {
                try {
                    series.add(day, value / initialPrice * 100);
                    day = day.next();
                } catch (SeriesException e) {
                    System.out.println("Error adding to data series of the Plotter: " + e);
                }
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    /**
     * Creates a chart, sets the labels of axes and the chart's title
     *
     * @param dataset
     * @return
     */
    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Price history over time", "Date", "% of initial price", dataset, true, true, false);
        XYPlot plot = chart.getXYPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        return chart;
    }

}
