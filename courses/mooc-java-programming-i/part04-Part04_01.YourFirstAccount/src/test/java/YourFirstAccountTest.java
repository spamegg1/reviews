
import fi.helsinki.cs.tmc.edutestutils.Points;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Rule;
import org.powermock.modules.junit4.rule.PowerMockRule;

import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

@Points("04-01")
@PrepareForTest({YourFirstAccount.class, Account.class})
public class YourFirstAccountTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void test() throws Exception {
        Account accountMock = createMock(Account.class);

        expectNew(Account.class, EasyMock.anyObject(String.class), EasyMock.eq(100.0)).andReturn(accountMock);

        accountMock.deposit(20.0);
        replay(accountMock, Account.class);

        try {
            YourFirstAccount.main(new String[0]);
            verify(accountMock, Account.class);

        } catch (Throwable t) {
            String error = t.getMessage();
            if (error.contains("deposit")) {
                fail("create an account and call the deposit method with paramter 20 ");
            } else if (error.contains("constructor")) {
                fail("When creating an account, give the constructor parameter 100.0 ");
            } else if (error.contains("saldo")) {
                fail("The program must print the account details by callign System.out.println(account). Here account refers to Account variable named account. " + error);
            }
            fail("Unexpected error:\n" + error);
        }
    }

    @Test
    public void testaaToString() throws Exception {
        MockInOut mio = new MockInOut("The program must print the account details by callign System.out.println(account). Here account refers to Account variable named account. ");

        YourFirstAccount.main(new String[0]);

        String out = mio.getOutput();
        assertTrue("", out.contains("120.0"));
        mio.close();

    }
}
