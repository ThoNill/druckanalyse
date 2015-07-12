package thomas.nill.druckanalyse;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;


public class DruckanalyseTest {
	
		@Test
	public void test3() {
		DruckAnalyse register = new DruckAnalyse();
		
		register.inputLine("input:ab1:sql:select name , gebdatum, ort from kunde");
		register.inputLine("input:ab1:start");
		register.inputLine("input:ab1:data:thomas|30011962");

		register.inputLine("input:ab2:sql:select  ort from adresse ");
		register.inputLine("input:ab2:start:thomas");
		register.inputLine("input:ab2:data:München");
		register.inputLine("name      thomas|m|münchen");
		register.inputLine("input:ab2:stop");
		
		register.inputLine("input:ab1:data:susanne|30071965");
		register.inputLine("input:ab2:sql:select  ort from adresse ");
		register.inputLine("input:ab2:start:susanne");
		register.inputLine("input:ab2:data:stuttgart");
		register.inputLine("name      susanne|w|stuttgart");
		register.inputLine("input:ab2:stop");
		register.inputLine("");

		
		register.inputLine("input:ab1:stop");
		register.ausgabe(System.out);
		
	}	
	
	
}