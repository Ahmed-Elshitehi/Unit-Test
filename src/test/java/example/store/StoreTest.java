package example.store;

import example.account.AccountManager;
import example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Matchers.*;

public class StoreTest {

    @Test
    void buyNotEnoughProductStock_whenBuy_thenFail(){
        AccountManager accountManager = Mockito.mock(AccountManager.class);
        Store store = new StoreImpl(accountManager);
        Product product=new Product();
        Customer customer = new Customer();

        try {
            store.buy(product, customer);
            Assertions.fail();
        } catch (Exception exception) {
            Assertions.assertEquals("Product out of stock", exception.getMessage());
        }
    }

    @Test
    void buyEnoughProductStock_whenBuy_thenSuccess(){
        AccountManager accountManager = Mockito.mock(AccountManager.class);
        Store store = new StoreImpl(accountManager);
        Product product=new Product();
        Customer customer = new Customer();

        product.setQuantity(10);

        Mockito.when(accountManager.withdraw(any(), anyInt()))
                .thenReturn("success");

        store.buy(product, customer);

        Assertions.assertEquals(9, product.getQuantity());
    }

    @Test
    void buyEnoughProductStock_whenBuy_thenFail() {
        AccountManager accountManager = Mockito.mock(AccountManager.class);
        Store store = new StoreImpl(accountManager);
        Product product=new Product();
        Customer customer = new Customer();

        product.setQuantity(10);

        Mockito.when(accountManager.withdraw(any(), anyInt()))
                .thenReturn("Not success");

        try {
            store.buy(product, customer);
            Assertions.fail();
        } catch (Exception ex) {
            Assertions.assertEquals("Payment failure: Not success", ex.getMessage());
        }
    }


}
