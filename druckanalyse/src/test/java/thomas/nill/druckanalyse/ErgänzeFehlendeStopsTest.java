package thomas.nill.druckanalyse;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;


public class ErgänzeFehlendeStopsTest {
	
		@Test
	public void test3() {
		ErgänzeFehlendeStopsRegister register = new ErgänzeFehlendeStopsRegister();
		
		register.zeileBearbeiten("input:ab1:sql:select name , gebdatum, ort from kunde");
		register.zeileBearbeiten("input:ab1:start");
		register.zeileBearbeiten("input:ab1:data:thomas|30011962");

		register.zeileBearbeiten("input:ab2:sql:select  ort from adresse ");
		register.zeileBearbeiten("input:ab2:start:thomas");
		register.zeileBearbeiten("input:ab2:data:München");
		register.zeileBearbeiten("name      thomas|m|münchen");
	
		
		register.zeileBearbeiten("input:ab1:data:susanne|30071965");
		register.zeileBearbeiten("input:ab2:sql:select  ort from adresse ");
		register.zeileBearbeiten("input:ab2:start:susanne");
		register.zeileBearbeiten("input:ab2:data:stuttgart");
		register.zeileBearbeiten("name      susanne|w|stuttgart");
		register.zeileBearbeiten("");
		register.zeileBearbeiten("input:ab3:sql:select  ort from adresse ");
		register.zeileBearbeiten("input:ab3:start:susanne");
		register.zeileBearbeiten("input:ab1:stop");
	

		assertTrue(register.getAbfragenOhneStopMitData().contains("ab2"));
		assertTrue(register.getAbfragenOhneStopUndData().contains("ab3"));
		assertFalse(register.getAbfragenOhneStopUndData().contains("ab2"));
		
		
	}	
	
	
}