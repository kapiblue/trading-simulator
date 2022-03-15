/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.assets;

import java.util.HashSet;
import java.util.Set;
import kapiblue.tradingsimulator.utilities.Generator;

/**
 * Stock index
 *
 * @author kapib
 */
public class Index {

    private String name;
    private int shareValueSum;
    private Set<Company> companies;

    /**
     * Constructor. Share value sum is initially 0 and increased if a company is
     * assigned to this
     */
    public Index() {
        this.name = (new Generator()).generateRandomWord(5);
        this.shareValueSum = 0;
        Set<Company> companies = new HashSet<>();
        this.companies = companies;
    }

    /**
     * Adds company to this.companies and increases the sum of share values by
     * the company's current share price
     *
     * @param company
     */
    public void addCompanyToIndex(Company company) {
        this.shareValueSum += company.getCurrentPrice();
        this.companies.add(company);
    }

    /**
     * Formatted string with details of this
     *
     * @return
     */
    public String indexDetails() {
        return "Index: " + name + "\n" + "Share value sum: " + shareValueSum + "\n"
                + "Companies:\n" + this.companiesString();
    }

    /**
     * @return String, each company from this.companies in a new line
     */
    public String companiesString() {
        String finalString = "";
        for (Company company : this.companies) {
            finalString += company.getName() + "\n";
        }
        return finalString;
    }

    @Override
    public String toString() {
        return "Index " + name;
    }
}
