package thomas.nill.druckanalyse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class FeldReihenRegisterTest {

    @Test
    public void test1() {
        FeldReihenRegister register = new FeldReihenRegister();

        register.zeileBearbeiten("input:ab1:sql:select name , gebdatum from kunde");
        register.zeileBearbeiten("input:ab1:start");
        register.zeileBearbeiten("input:ab1:data:thomas|30011962");
        register.zeileBearbeiten("name      thomas|münchen");
        register.zeileBearbeiten("input:ab1:data:susanne|30071965");
        register.zeileBearbeiten("name      susanne|stuttgart");
        register.zeileBearbeiten("input:ab1:data:anne|20011962");
        register.zeileBearbeiten("name      anne|freiburg");
        register.zeileBearbeiten("input:ab1:stop");
        List<FeldVerbindung> cset = register.getMatchingConnections();
        assertEquals(1, cset.size());
        assertTrue(cset.contains(new FeldVerbindung("ab1", 0, "name", 0)));
    }

    @Test
    public void test2() {
        FeldReihenRegister register = new FeldReihenRegister();

        register.zeileBearbeiten("input:ab1:sql:select name , gebdatum, ort from kunde");
        register.zeileBearbeiten("input:ab1:start");
        register.zeileBearbeiten("input:ab1:data:thomas|30011962|München");
        register.zeileBearbeiten("name      thomas|münchen");
        register.zeileBearbeiten("input:ab1:data:susanne|30071965|Stuttgart");
        register.zeileBearbeiten("name      susanne|stuttgart");
        register.zeileBearbeiten("input:ab1:data:anne|20011962|freiburg");
        register.zeileBearbeiten("name      anne|freiburg");
        register.zeileBearbeiten("input:ab1:stop");
        List<FeldVerbindung> cset = register.getMatchingConnections();
        System.out.println(cset);
        assertEquals(2, cset.size());
        assertTrue(cset.contains(new FeldVerbindung("ab1", 0, "name", 0)));
        assertTrue(cset.contains(new FeldVerbindung("ab1", 2, "name", 1)));
    }

}