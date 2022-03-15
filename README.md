# Trading Simluator 

A market simulator in Java.

## INTRODUCTION

It supports three market types, companies, stock indexes and market participants,
who trade on markets. Some Duck Tales reference was included in the program.

## REQUIREMENTS

I used JFreeChart for plots.

## FUNCTIONAL INFO

The main class of the project is the Singleton World. Another Singleton class is the Central Bank.
Once an asset is created, it is added to the Central Bank.
The bank's thread generates transactions. It also monitors price updates and sumulation date updates.
The price of an asset depends on the Bear Bull ratio (linear relationship with some randomness).
In order to see the price trend change, please change the ratio on the slider.
The participants are more much less likely to trade assets if the ratio is above 50.
The Transactions per minute is the approximate number of transactions per one minute of real time.

## GUI INSTRUCTIONS

The GUI consists of four main panels:
* To the left we can see the control panel. Here the user can change world parameters
and create new instances (markets, assets and participants).
* In the middle we can see the main panel. It contains lists of available equities for sale
and a list of participants. There are also two buttons for showing asset's details.
* To the right we can see the right panel. It contains an icon, the list of available markets and 
the list of created stock indexes.
* At the bottom there is a bar with the current simulation date and a deep, inspiring quote.

Exceptions can work as a guide. Please create a stock index before creating a company.

In order to display details about an object, please select it on the list. A dialog opens.
This doesn't apply for assets. In case of assets, please select one asset and click the button.
In order to see the percentage plot, please select multiple assets on the list and click
"Show percentage plot".
