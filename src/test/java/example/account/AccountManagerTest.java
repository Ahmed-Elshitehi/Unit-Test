package example.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountManagerTest {
    AccountManager accountManager = new AccountManagerImpl();

    // Deposit
    @Test
    public void depositWithPositiveAmount_thenSuccess() {
        // Arrange
        Customer customer = new Customer();
        // ACT
        accountManager.deposit(customer, 100);
        // Assert
        Assertions.assertEquals(100, customer.getBalance());
    }
    @Test
    public void depositWithNegativeAmount_thenFail() {
        // Arrange
        Customer customer = new Customer();
        // ACT & Assert
        try {
            accountManager.deposit(customer, -50);
            Assertions.fail();
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals(0, customer.getBalance());
        }
    }

    // Withdraw
    @Test
    public void withdrawPositive_thenSuccess() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        // ACT
        String result = accountManager.withdraw(customer, 90);
        // Assert
        Assertions.assertEquals("success", result);
        Assertions.assertEquals(10, customer.getBalance());
    }

    @Test
    public void withdrawNegative_thenFail() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        // ACT & Assert
        try {
            accountManager.withdraw(customer, -100);
            Assertions.fail();
        } catch (IllegalArgumentException ex) {
            Assertions.assertEquals(100, customer.getBalance());
        }
    }

    @Test
    public void withdrawPositiveNoEnoughBalance_thenFail() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        // ACT
        String result = accountManager.withdraw(customer, 150);
        // Assert
        Assertions.assertEquals("insufficient account balance", result);
        Assertions.assertEquals(100, customer.getBalance());
    }

    @Test
    public void withdrawPositiveNoEnoughBalanceWithCredit_thenSuccess() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        // ACT
        String result = accountManager.withdraw(customer, 150);
        // Assert
        Assertions.assertEquals("success", result);
        Assertions.assertEquals(-50, customer.getBalance());
    }

    @Test
    public void withdrawPositiveNoEnoughBalanceWithCreditLimitExceed_thenFail() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        // ACT
        String result = accountManager.withdraw(customer, 1200);
        // Assert
        Assertions.assertEquals("maximum credit exceeded", result);
        Assertions.assertEquals(100, customer.getBalance());
    }


    @Test
    public void withdrawPositiveNoEnoughBalanceWith_VIP_LimitExceed_thenFail() {
        // Arrange
        Customer customer = new Customer();
        customer.setBalance(100);
        customer.setCreditAllowed(true);
        customer.setVip(true);
        // ACT
        String result = accountManager.withdraw(customer, 1200);
        // Assert
        Assertions.assertEquals("success", result);
        Assertions.assertEquals(-1100, customer.getBalance());
    }

}
