
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import org.junit.Rule;
import org.powermock.modules.junit4.rule.PowerMockRule;

import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

@Points("04-02")
@PrepareForTest({YourFirstBankTransfer.class, Account.class})
public class YourFirstBankTransferTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void test() throws Exception {
        Account matthewsAccount = createMock(Account.class);
        Account myAccount = createMock(Account.class);

        expectNew(Account.class, "Matthews account", 1000.0).andReturn(matthewsAccount);
        expectNew(Account.class, "My account", 0.0).andReturn(myAccount);

        matthewsAccount.withdrawal(100.0);
        myAccount.deposit(100.0);

        replay(matthewsAccount, Account.class);
        replay(myAccount, Account.class);

        try {
            YourFirstBankTransfer.main(new String[0]);
            verify(matthewsAccount, Account.class);
            verify(myAccount, Account.class);

        } catch (Throwable t) {
            String error = t.getMessage();
            if (error.contains("Matthews account")) {
                fail("Create an account with the parameters \"Matthews account\", 1000.0");
            } else if (error.contains("My account")) {
                fail("Create an account with the parameters \"My account\", 0.0");
            } else if (error.contains("withdrawal")) {
                fail("Create an account with the parameters \"Matthews account\", 1000.0 and withdraw 100.0 from it");
            } else if (error.contains("deposit")) {
                fail("Create an account with the parameters  \"My account\", 0.0 and deposit 100.0 to it");
            }
            fail("Unexpected situation\n" + error);
        }
    }

    @Test
    public void testToString() throws Exception {
        MockInOut mio = new MockInOut("");

        YourFirstBankTransfer.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("After the bank transfer the program must print the details of Matthews account", out.contains("900.0"));
        assertTrue("After the bank transfer the program must print the details of My account", out.contains("100.0"));
        mio.close();

    }
}
