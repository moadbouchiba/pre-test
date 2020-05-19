package com.priceminister.account.implementation;

import com.priceminister.account.*;


public class CustomerAccount implements Account {

    private Double balance;

    public CustomerAccount() {
        balance = 0d;
    }

    /**
     * {@inheritDoc}
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * {@inheritDoc}
     */
    public void add(Double addedAmount) {
        if (!isValidAmount(addedAmount))
            throw new IllegalArgumentException(
                    "Illegal addedAmount argument: "
                            .concat(String.valueOf(addedAmount))
            );

        balance += addedAmount;
    }

    /**
     * {@inheritDoc}
     */
    public Double withdrawAndReportBalance(Double withdrawnAmount, AccountRule rule) {
        if (!isValidAmount(withdrawnAmount))
            throw new IllegalArgumentException(
                    "Illegal withdrawnAmount argument: "
                            .concat(String.valueOf(withdrawnAmount))
            );

        if (!isValidAccountRule(rule))
            throw new IllegalArgumentException("Account rule argument must be not null");

        if (!rule.withdrawPermitted((balance - withdrawnAmount)))
            throw new IllegalBalanceException(
                    "Illegal account balance: "
                            .concat(String.valueOf(balance - withdrawnAmount))
            );

        balance -= withdrawnAmount;
        return balance;
    }

    /**
     * check if rule is not null
     *
     * @param rule - the AccountRule that defines which balance is allowed for this account
     * @return true if the rule is not null
     */
    private boolean isValidAccountRule(AccountRule rule) {
        return rule != null;
    }

    /**
     * check if the withdrawnAmount is not null and has a positive value
     *
     * @param amount - the money to withdraw
     * @return true if the withdrawnAmount is not null and has a positive value
     */
    private boolean isValidAmount(Double amount) {
        return amount != null && amount > 0;
    }

}
