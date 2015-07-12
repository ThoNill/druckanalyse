package thomas.nill.druckanalyse;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;


public class AbfrageRegisterTest {
	
	@Test
	public void test1() {
		AbfrageRegister register = new AbfrageRegister();
		
		register.zeileBearbeiten("input:ab1:sql:select name , gebdatum from kunde");
		register.zeileBearbeiten("input:ab1:start");
		register.zeileBearbeiten("input:ab1:data:thomas|30011962");
		register.zeileBearbeiten("name      thomas|münchen");
		register.zeileBearbeiten("input:ab1:data:susanne|30071965");
		register.zeileBearbeiten("name      susanne|stuttgart");
		register.zeileBearbeiten("input:ab1:data:anne|20011962");
		register.zeileBearbeiten("name      anne|freiburg");
		register.zeileBearbeiten("input:ab1:stop");
		Collection<Abfrage> cset = register.getAbfragen();
		assertEquals(1,cset.size());
		assertEquals(1, register.getWurzelAbfragen().size());
		assertEquals("ab1",((Abfrage)cset.toArray()[0]).getName());
	}
	
	@Test
	public void test2() {
		AbfrageRegister register = new AbfrageRegister();
		
		register.zeileBearbeiten("input:ab1:sql:select name , gebdatum, ort from kunde");
		register.zeileBearbeiten("input:ab1:start");
		register.zeileBearbeiten("input:ab1:data:thomas|30011962|München");
		register.zeileBearbeiten("name      thomas|münchen");
		register.zeileBearbeiten("input:ab1:data:susanne|30071965|Stuttgart");
		register.zeileBearbeiten("name      susanne|stuttgart");
		register.zeileBearbeiten("input:ab1:data:anne|20011962|freiburg");
		register.zeileBearbeiten("name      anne|freiburg");
		register.zeileBearbeiten("input:ab1:stop");
		Collection<Abfrage> cset = register.getAbfragen();
		assertEquals(1,cset.size());
		assertEquals(1, register.getWurzelAbfragen().size());
		assertEquals("ab1",((Abfrage)cset.toArray()[0]).getName());
	}
	
	@Test
	public void test3() {
		AbfrageRegister register = new AbfrageRegister();
		
		register.zeileBearbeiten("input:ab1:sql:select name , gebdatum, ort from kunde");
		register.zeileBearbeiten("input:ab1:start");
		register.zeileBearbeiten("input:ab1:data:thomas|30011962");

		register.zeileBearbeiten("input:ab2:sql:select  ort from adresse ");
		register.zeileBearbeiten("input:ab2:start:thomas");
		register.zeileBearbeiten("input:ab2:data:München");
		register.zeileBearbeiten("name      thomas|m|münchen");
		register.zeileBearbeiten("input:ab2:stop");
		
		register.zeileBearbeiten("input:ab1:data:susanne|30071965");
		register.zeileBearbeiten("input:ab2:sql:select  ort from adresse ");
		register.zeileBearbeiten("input:ab2:start:susanne");
		register.zeileBearbeiten("input:ab2:data:stuttgart");
		register.zeileBearbeiten("name      susanne|w|stuttgart");
		register.zeileBearbeiten("input:ab2:stop");

		
		register.zeileBearbeiten("input:ab1:stop");
		Collection<Abfrage> cset = register.getAbfragen();
		assertEquals(2,cset.size());
		assertEquals(1, register.getWurzelAbfragen().size());
		assertTrue(cset.contains(new Abfrage("ab1", "select name , gebdatum, ort from kunde")));
		assertTrue(cset.contains(new Abfrage("ab2", "select  ort from adresse ")));
		
		int countFound=0;
		for(Abfrage a : cset) {
			if ("ab1".equals(a.getName())) {
				countFound++;
				FeldReihe child = a.getChilds().elementAt(0);
				assertEquals("ab2",child.getName());
			}
			if ("ab2".equals(a.getName())) {
				countFound++;
				FeldReihe child = a.getChilds().elementAt(0);
				assertEquals("name",child.getName());
			}
		}
		assertEquals(2, countFound);
		
	}	
	
	
}