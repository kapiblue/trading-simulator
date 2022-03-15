/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.participants;

import java.util.HashSet;
import java.util.Set;
import kapiblue.tradingsimulator.World;
import kapiblue.tradingsimulator.markets.Market;
import kapiblue.tradingsimulator.participants.MarketParticipant;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Investment fund can be joined by investors
 *
 * @author kapib
 */
public class InvestmentFund extends MarketParticipant {

    private String name;
    private String managerFirstName;
    private String managerLastName;
    private Set<InvestmentFundParticipant> fundParticipants;
    private int totalBudget;

    /**
     * Investors are stored in a set
     *
     * @param world
     */
    public InvestmentFund(World world) {
        super(world);
        Generator generator = new Generator();
        this.name = generator.generateRandomWord(5);
        this.managerFirstName = generator.generateRandomWord(8);
        this.managerLastName = generator.generateRandomWord(8);
        this.fundParticipants = new HashSet<>();
    }

    /**
     * Adds a participant. Increases total budget of this
     *
     * @param investor
     * @param moneyInvested
     */
    public void addParticipant(Investor investor, int moneyInvested) {
        InvestmentFundParticipant participant = new InvestmentFundParticipant(moneyInvested, investor);
        fundParticipants.add(participant);
        this.totalBudget += moneyInvested;
    }

    @Override
    public String participantDetails() {
        return "Investment Fund: " + name + "\n" + "Manager's name: " + managerFirstName + "\n"
                + "Manager's surname: " + managerLastName + "\n" + "Participants: " + fundParticipants + "\n";
    }

    @Override
    public String toString() {
        return "InvestmentFund " + name;
    }

    /**
     *
     * @return
     */
    public int getTotalBudget() {
        return totalBudget;
    }

    /**
     *
     * @return
     */
    public Set<InvestmentFundParticipant> getFundParticipants() {
        return fundParticipants;
    }

    /**
     *
     * @return
     */
    public String getFundName() {
        return this.name;
    }
}
