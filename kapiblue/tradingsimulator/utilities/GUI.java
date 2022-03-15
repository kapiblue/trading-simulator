/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import kapiblue.tradingsimulator.World;
import kapiblue.tradingsimulator.assets.Asset;
import kapiblue.tradingsimulator.assets.Equity;
import kapiblue.tradingsimulator.assets.Index;
import kapiblue.tradingsimulator.exceptions.PlotterException;
import kapiblue.tradingsimulator.markets.Market;
import kapiblue.tradingsimulator.participants.MarketParticipant;

/**
 * Graphical User Interface. Done from scratch in Swing
 *
 * @author kapib
 */
public final class GUI {

    World theWorld;
    private JFrame window;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JPanel rightPanel;
    private JPanel slideBar;
    private DefaultListModel<MarketParticipant> participantModel;
    private volatile DefaultListModel<Equity> equityModel;
    private DefaultListModel<Market> marketModel;
    private DefaultListModel<Index> indexModel;
    private JLabel dateLabel;
    private Color buttonColor = new Color(220, 245, 253);
    private final Object lockListAccess = new Object();

    /**
     * Has theWorld
     *
     * @param world
     * @throws InterruptedException
     */
    public GUI(World world) throws InterruptedException {
        this.theWorld = world;
        window = new JFrame();

        configureControlPanel();
        configureMainPanel();
        configureRigthPanel();

        slideBar = new JPanel();
        dateLabel = new JLabel();
        slideBar.add(dateLabel, BorderLayout.LINE_START);
        slideBar.add(new JLabel("I made my name being tougher than the toughies"
                + " and smarter than the smarties - Scrooge McDuck"), BorderLayout.LINE_END);
        slideBar.setBackground(new Color(247, 186, 79));

        window.add(controlPanel, BorderLayout.LINE_START);
        window.add(mainPanel, BorderLayout.CENTER);
        window.add(rightPanel, BorderLayout.LINE_END);
        window.add(slideBar, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(900, 800);
        window.setTitle("Trading Simulator");
        window.setVisible(true);
    }

    /**
     * Sets color and the title label, configures sliders
     */
    public void configureControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setBackground(new Color(216, 98, 70));
        controlPanel.setLayout(new GridLayout(12, 1, 5, 5));

        JLabel controlLabel = new JLabel("Control Panel");
        controlLabel.setFont(new Font("Courier", Font.PLAIN, 20));
        controlPanel.add(controlLabel);

        configureBearBullSlider();
        configureTransactionsPerMinuteSlider();
        configureControlPanelButtons();

        for (Component component : controlPanel.getComponents()) {
            component.setBackground(buttonColor);
        }
    }

    /**
     * Creates the slider for setting the value of bear bull ratio (between 0
     * and 100). The values in the World class are updated
     */
    public void configureBearBullSlider() {
        JPanel bearBullSliderPanel = new JPanel();

        JSlider bearBullSlider = new JSlider(0, 100, 20);
        bearBullSlider.setValue(theWorld.getBearBullRatio());
        bearBullSlider.setPaintLabels(true);
        bearBullSlider.setMajorTickSpacing(20);
        bearBullSlider.setBackground(buttonColor);

        bearBullSlider.addChangeListener(e -> {
            theWorld.setBearBullRatio(bearBullSlider.getValue());
        });

        bearBullSliderPanel.setLayout(new GridLayout(2, 1));
        bearBullSliderPanel.add(new JLabel("Set Bear Bull Ratio"));

        bearBullSliderPanel.add(bearBullSlider);
        controlPanel.add(bearBullSliderPanel);
    }

    /**
     * Configures the slider for transactions per minute (between 0 and 1500).
     * Updates the value of the World class.
     */
    public void configureTransactionsPerMinuteSlider() {
        JPanel transactionsPanel = new JPanel();

        JSlider transactionsSlider = new JSlider(0, 1500, 300);
        transactionsSlider.setValue(theWorld.getTransactionsPerMinute());
        transactionsSlider.setPaintLabels(true);
        transactionsSlider.setMajorTickSpacing(500);
        transactionsSlider.setBackground(buttonColor);

        transactionsSlider.addChangeListener(e -> {
            theWorld.setTransactionsPerMinute(transactionsSlider.getValue());
        });

        transactionsPanel.setLayout(new GridLayout(2, 1));
        transactionsPanel.add(new JLabel("Set Transactions per minute"));
        transactionsPanel.add(transactionsSlider);
        controlPanel.add(transactionsPanel);
    }

    /**
     * Adds creation buttons and their action listeners.
     */
    public void configureControlPanelButtons() {
        JButton createStockMarketButton = new JButton("Create a Stock Market");
        createStockMarketButton.addActionListener(e -> theWorld.createStockMarket());
        controlPanel.add(createStockMarketButton);

        JButton createCommodityMarketButton = new JButton("Create a Commodity Market");
        createCommodityMarketButton.addActionListener(e -> theWorld.createCommodityMarket());
        controlPanel.add(createCommodityMarketButton);

        JButton createCurrencyMarketButton = new JButton("Create a Currency Market");
        createCurrencyMarketButton.addActionListener(e -> theWorld.createCurrencyMarket());
        controlPanel.add(createCurrencyMarketButton);

        JButton createCompanyButton = new JButton("Create a Company");
        createCompanyButton.addActionListener(e -> {
            if (theWorld.getStockMarkets().size() == 0) {
                theWorld.createStockMarket();
            }
            theWorld.createCompany();
        });
        controlPanel.add(createCompanyButton);

        JButton createIndexButton = new JButton("Create an Index");
        createIndexButton.addActionListener(e -> theWorld.createIndex());
        controlPanel.add(createIndexButton);

        JButton createCommodityButton = new JButton("Create a Commodity");
        createCommodityButton.addActionListener(e -> {
            if (theWorld.getCommodityMarkets().size() == 0) {
                theWorld.createCommodityMarket();
            }
            theWorld.createCommodity();
        });
        controlPanel.add(createCommodityButton);

        JButton createCurrencyButton = new JButton("Create a Currency");
        createCurrencyButton.addActionListener(e -> {
            if (theWorld.getCurrencyMarkets().size() == 0) {
                theWorld.createCurrencyMarket();
            }
            theWorld.createCurrency();
        });
        controlPanel.add(createCurrencyButton);

        JButton createInvestorButton = new JButton("Create an Investor");
        createInvestorButton.addActionListener(e -> {
            theWorld.createInvestor();
        });

        controlPanel.add(createInvestorButton);
        JButton createInvestmentFundButton = new JButton("Create an Investment Fund");
        createInvestmentFundButton.addActionListener(e -> {
            theWorld.createInvestmentFund();
        });
        controlPanel.add(createInvestmentFundButton);
    }

    /**
     * Sets the color of the main panel
     */
    public void configureMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(251, 242, 231));

        configureMainPanelAssetList();
        configureMainPanelInvestorList();
    }

    /**
     * Configures the asset list on the main panel. Sets the list model,
     * selection model and action listeners. Two buttons for plots and details
     * are created.
     *
     */
    public void configureMainPanelAssetList() {
        JPanel assetPanel = new JPanel();

        equityModel = new DefaultListModel<>();
        equityModel.ensureCapacity(1000);
        JList<Equity> equityList = new JList(equityModel);
        equityList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JButton plotButton = new JButton("Show plot and details");
        plotButton.addActionListener(e -> {
            Equity equity = equityList.getSelectedValue();
            try {
                if (equity == null) {
                    throw new PlotterException("To show asset's details please select exactly one item from the list");
                }

                Asset asset = equity.getAsset();
                new PricePlotter(theWorld.getStartDate(), asset);
            } catch (PlotterException ex) {
                System.out.println("Exception caught: " + ex);
            }
        });

        JScrollPane assetScrollPane = new JScrollPane(equityList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        assetScrollPane.setPreferredSize(
                new Dimension(250, 550));

        JButton plotPercentageButton = new JButton("Show percentage plot");

        plotPercentageButton.addActionListener(e
                -> {
            List<Equity> selectedEquities = equityList.getSelectedValuesList();
            new PercentagePlotter(theWorld.getStartDate(), selectedEquities);
        }
        );

        JPanel parentPanel = new JPanel();

        assetPanel.setLayout(new BorderLayout());
        assetPanel.add(new JLabel("Available Assets"), BorderLayout.PAGE_START);
        assetPanel.add(assetScrollPane, BorderLayout.CENTER);
        assetPanel.add(plotButton, BorderLayout.PAGE_END);

        parentPanel.setLayout(
                new BorderLayout());
        parentPanel.add(assetPanel, BorderLayout.CENTER);

        parentPanel.add(plotPercentageButton, BorderLayout.PAGE_END);

        mainPanel.add(parentPanel);
    }

    /**
     * Creates and sets up the list of created market participants. If a
     * participant is selected, a message dialog with details pops up.
     */
    public void configureMainPanelInvestorList() {
        JPanel participantPanel = new JPanel();
        JList<MarketParticipant> participantList = new JList<>();

        participantModel = new DefaultListModel<>();
        participantList.setModel(participantModel);

        participantList.getSelectionModel().addListSelectionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, participantList.getSelectedValue().participantDetails(), "Participant's details", 1);
        });
        JScrollPane participantScrollPane = new JScrollPane(participantList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        participantPanel.setLayout(new BorderLayout());
        participantPanel.add(new JLabel("Market Participants"), BorderLayout.PAGE_START);
        participantScrollPane.setPreferredSize(new Dimension(200, 600));
        participantPanel.add(participantScrollPane);
        mainPanel.add(participantPanel);
    }

    /**
     * Creates the panel, sets the layout. Loads an image
     */
    public void configureRigthPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        JLabel picture = new JLabel();
        ImageIcon image = new ImageIcon("classes/uncle.jpg");
        picture.setIcon(new ImageIcon(image.getImage().getScaledInstance(200, 200, 10)));
        picture.setMaximumSize(new Dimension(200, 200));
        rightPanel.add(picture, BorderLayout.PAGE_START);

        configureRightPanelLists();
    }

    /**
     * Configures the list of created markets and a list of stock indexes. If a
     * market or an index is selected, a message dialog with details appears.
     */
    public void configureRightPanelLists() {
        JPanel marketPanel = new JPanel();
        marketPanel.setLayout(new BorderLayout());

        JList<Market> marketList = new JList<>();
        marketModel = new DefaultListModel<>();
        marketList.setModel(marketModel);

        marketList.getSelectionModel().addListSelectionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, marketList.getSelectedValue().marketDetails(), "Market's details", 1);
        });

        JScrollPane marketScrollPane = new JScrollPane(marketList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        marketScrollPane.setPreferredSize(new Dimension(200, 400));

        marketPanel.add(new JLabel("Markets"), BorderLayout.PAGE_START);
        marketPanel.add(marketScrollPane, BorderLayout.CENTER);

        JList<Index> indexList = new JList<>();
        indexModel = new DefaultListModel<>();
        indexList.setModel(indexModel);

        indexList.getSelectionModel().addListSelectionListener(e -> {
            JOptionPane.showMessageDialog(mainPanel, indexList.getSelectedValue().indexDetails(), "Index's details", 1);
        });
        JScrollPane indexScrollPane = new JScrollPane(indexList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        indexScrollPane.setPreferredSize(new Dimension(200, 200));
        marketPanel.add(indexScrollPane, BorderLayout.PAGE_END);

        rightPanel.add(marketPanel);
    }

    /**
     * Adds a stock index to the model
     *
     * @param index
     */
    public void addIndexToIndexList(Index index) {
        indexModel.addElement(index);
    }

    /**
     * Adds an equity to the model
     *
     * @param equity
     */
    public void addEquitytoEquityList(Equity equity) {
        synchronized (lockListAccess) {
            equityModel.addElement(equity);
        }
    }

    /**
     * Removes equity
     *
     * @param equity
     */
    public void removeEquityFromEquityList(Equity equity) {
        synchronized (lockListAccess) {
            if (equityModel.getSize() > 0) {
                equityModel.removeElement(equity);
            }
        }
    }

    /**
     * Displays the current simulation date
     */
    public void displayCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        String currentDate = dateFormat.format(theWorld.getCurrentDate());
        dateLabel.setText(currentDate);
    }

    /**
     *
     * @return
     */
    public DefaultListModel<Market> getMarketModel() {
        return marketModel;
    }

    /**
     *
     * @return
     */
    public DefaultListModel<MarketParticipant> getParticipantModel() {
        return participantModel;
    }

    /**
     *
     * @param participantModel
     */
    public void setParticipantModel(DefaultListModel<MarketParticipant> participantModel) {
        this.participantModel = participantModel;
    }
}
