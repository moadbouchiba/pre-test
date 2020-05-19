package com.priceminister.account;

import static org.assertj.core.api.Assertions.*;

import org.junit.*;

import com.priceminister.account.implementation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Please create the business code, starting from the unit tests below.
 * Implement the first test, the develop the code that makes it pass.
 * Then focus on the second test, and so on.
 * <p>
 * We want to see how you "think code", and how you organize and structure a simple application.
 * <p>
 * When you are done, please zip the whole project (incl. source-code) and send it to recrutement-dev@priceminister.com
 */
public class CustomerAccountTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerAccountTest.class);

    AccountRule rule;
    Account customerAccount;

    @Before
    public void setUp() {
        rule = new CustomerAccountRule();
        customerAccount = new CustomerAccount();
    }

    /**
     * Tests that an empty account always has a balance of 0.0, not a NULL.
     */
    @Test
    public void testAccountWithoutMoneyHasZeroBalance() {
        assertThat(customerAccount.getBalance()).isEqualTo(0d);
    }

    /**
     * Adds money to the account and checks that the new balance is as expected.
     */
    @Test
    public void testAddPositiveAmount() {
        final Double addedAmount = 20d;
        final Double initialBalance = customerAccount.getBalance();

        customerAccount.add(addedAmount);

        assertThat(customerAccount.getBalance()).isEqualTo(initialBalance + addedAmount);
    }

    /**
     * Tests that an illegal added amount throws the expected exception.
     */
    @Test
    public void testAddNegativeOrNullAmount() {
        assertThatThrownBy(() -> customerAccount.add(-20d))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Illegal addedAmount argument: -20.0");

        assertThatThrownBy(() -> customerAccount.add(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Illegal addedAmount argument: null");
    }

    /**
     * Tests that a legal withdrawal returns the right balance.
     */
    @Test
    public void testWithdrawAndReportBalance() {
        final Double addedAmount = 100d;
        final Double withdrawnAmount = 20d;

        Double reportedBalance = null;
        try {
            customerAccount.add(addedAmount);
            reportedBalance = customerAccount.withdrawAndReportBalance(withdrawnAmount, rule);
        } catch (IllegalBalanceException e) {
            log.error("{}", e.getMessage());
        }

        assertThat(reportedBalance).isEqualTo(addedAmount - withdrawnAmount);
    }

    /**
     * Tests that an illegal withdrawal throws the expected exception.
     */
    // @Test(expected = IllegalBalanceException.class) -> using JUnit
    @Test
    public void testWithdrawAndThrowIllegalBalanceException() {
        final Double withdrawnAmount = 20d;

        assertThatThrownBy(() -> customerAccount.withdrawAndReportBalance(withdrawnAmount, rule))
                .isInstanceOf(IllegalBalanceException.class)
                .hasMessageContaining(
                        "Illegal account balance: ".concat(String.valueOf(customerAccount.getBalance() - withdrawnAmount))
                );
    }

    /**
     * Tests that an illegal arguments throws the expected exception.
     */
    @Test
    public void testWithdrawAndThrowIllegalArgumentException() {
        // we can't withdrawn negative amount
        assertThatThrownBy(() -> customerAccount.withdrawAndReportBalance(-20d, rule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Illegal withdrawnAmount argument: -20.0");

        assertThatThrownBy(() -> customerAccount.withdrawAndReportBalance(20d, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Account rule argument must be not null");

        assertThatThrownBy(() -> customerAccount.withdrawAndReportBalance(null, rule))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Illegal withdrawnAmount argument: null");
    }

}
