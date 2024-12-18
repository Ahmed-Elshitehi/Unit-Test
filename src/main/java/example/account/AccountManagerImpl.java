package example.account;

public class AccountManagerImpl implements AccountManager {
    private static final int MAX_CREDIT = 1000;
    @Override
    public void deposit(Customer customer, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount can't be negative");
        }
        customer.setBalance(customer.getBalance() + amount);
    }

    @Override
    public String withdraw(Customer customer, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount can't be negative");
        }

        int expectedBalance = customer.getBalance() - amount;

        if (expectedBalance < 0) {
            if (!customer.isCreditAllowed()) {
                return "insufficient account balance";
            } else if (expectedBalance < -1 * MAX_CREDIT && !customer.isVip()) {
                return "maximum credit exceeded";
            }
        }
        customer.setBalance(expectedBalance);
        return "success";
    }
}