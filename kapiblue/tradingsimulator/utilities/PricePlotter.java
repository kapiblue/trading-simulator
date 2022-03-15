/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import kapiblue.tradingsimulator.assets.Asset;
import kapiblue.tradingsimulator.assets.Company;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 * Plotter for a single asset. Displays also asset's details
 *
 * @author kapib
 */
public class PricePlotter extends JFrame {

    /**
     * Constructor creates a dataset and a chart.Creates a panel and displays
     * asset's details.If the asset is a Company, there will also be a Buy Out
     * button visible.
     *
     * @param currentDate
     * @param asset
     */
    public PricePlotter(Date currentDate, Asset asset) {
        final XYDataset dataset = createDataset(currentDate, asset);
        final JFreeChart chart = createChart(dataset);

        JPanel assetDetailsPanel = new JPanel(new BorderLayout());
        JTextArea assetDetailsText = new JTextArea(asset.assetDetails());
        assetDetailsText.setEditable(false);
        assetDetailsText.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 20));
        assetDetailsPanel.add(assetDetailsText, BorderLayout.NORTH);

        if (asset instanceof Company) {
            JButton buyOutButton = buyOuyButton((Company) asset);
            buyOutButton.setPreferredSize(new Dimension(40, 100));
            assetDetailsPanel.add(buyOutButton, BorderLayout.PAGE_END);
        }
        add(assetDetailsPanel, BorderLayout.EAST);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.WEST);

        pack();
        setTitle(asset.getName() + " details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    /**
     * Sets up the buy out button. If the button is clicked, opens an input
     * dialog
     */
    private JButton buyOuyButton(Company asset) {

        JButton buyOutButton = new JButton("Company buy out");
        buyOutButton.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog("Enter share amount");

            try {
                int shareAmount = Integer.parseInt(amount);
                asset.buyOut(shareAmount);

            } catch (NumberFormatException ex) {
                System.out.println("Exception caught:" + ex);
            }
        });
        return buyOutButton;
    }

    /**
     * Creates a dataset with a single time series
     */
    private XYDataset createDataset(Date currentDate, Asset asset) {
        final TimeSeries series = new TimeSeries(asset.getName());
        RegularTimePeriod day = new Day(currentDate);

        for (float value : asset.getPriceHistory()) {
            try {
                series.add(day, value);
                day = day.next();
            } catch (SeriesException e) {
                System.out.println("Error adding to data series of the Plotter: " + e);
            }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        return dataset;
    }

    /**
     * Creates a chart with title and labels for axes
     *
     * @param dataset
     * @return
     */
    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Price history over time", "Date", "Price ($)", dataset, true, true, false);
        XYPlot plot = chart.getXYPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        return chart;
    }

}
