package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestStrictBankAccount {

    /**
     *
     */
    private static final int TEST_AMOUNT = 42;

    private final static int INITIAL_AMOUNT = 100;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 42);
        bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(mRossi, bankAccount.getAccountHolder());
        assertEquals(0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT);
        assertEquals(1,bankAccount.getTransactionsCount());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertTrue(bankAccount.getBalance() < INITIAL_AMOUNT);
        assertEquals(0,bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), -TEST_AMOUNT);
            fail("Withdrawed negative amount");
        } catch (final IllegalArgumentException e) {
            assertEquals("Cannot withdraw a negative amount", e.getMessage());
            assertEquals(0, bankAccount.getTransactionsCount());
            assertEquals(0, bankAccount.getBalance());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(mRossi.getUserID(), TEST_AMOUNT);
            fail("Withdrawed when there were insufficient moneys");
        } catch (final IllegalArgumentException e) {
            assertEquals("Insufficient balance",e.getMessage());
            assertEquals(0, bankAccount.getBalance());
            assertEquals(0, bankAccount.getTransactionsCount());
        }
    }
}
