package com.priceminister.account;

/**
 * This class represents a simple account.
 * It doesn't handle different currencies, all money is supposed to be of standard currency EUR.
 */
public interface Account {

    /**
     * Adds money to this account.
     *
     * @param addedAmount - the money to add
     */
    void add(Double addedAmount);

    /**
     * Withdraws money from the account.
     *
     * @param withdrawnAmount - the money to withdraw
     * @param rule            - the AccountRule that defines which balance is allowed for this account
     * @return the remaining account balance
     */
    Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule);

    /**
     * Gets the current account balance.
     *
     * @return the account's balance
     */
    Double getBalance();

}
