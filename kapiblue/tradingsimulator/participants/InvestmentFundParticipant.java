/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.participants;

/**
 * A participant of an investment fund
 *
 * @author kapib
 */
public class InvestmentFundParticipant {

    private int moneyInvested;
    private Investor investor;

    /**
     * Only an investor may become a participant
     *
     * @param moneyInvested
     * @param investor
     */
    public InvestmentFundParticipant(int moneyInvested, Investor investor) {
        this.moneyInvested = moneyInvested;
        this.investor = investor;
    }

    /**
     * Gets the invested capital
     *
     * @return
     */
    public int getMoneyInvested() {
        return moneyInvested;
    }

    /**
     * Sets money invested
     *
     * @param moneyInvested
     */
    public void setMoneyInvested(int moneyInvested) {
        this.moneyInvested = moneyInvested;
    }

    @Override
    public String toString() {
        return investor.toString() + "\n";
    }

}
