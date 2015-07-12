package thomas.nill.druckanalyse;

import java.io.OutputStream;

public class DruckAnalyse {
	AbfrageRegister abfrageRegister = new AbfrageRegister();
	FeldReihenRegister connectionRegister = new FeldReihenRegister();
	
	public DruckAnalyse() {
		
	}
	
	public void inputLine(String line) {
		abfrageRegister.zeileBearbeiten(line);
		connectionRegister.zeileBearbeiten(line);
	}
	
	public void ausgabe(OutputStream out) {
		Ausgabe ausgabe = new Ausgabe(out,connectionRegister.getMatchingConnections(),abfrageRegister);
		ausgabe.print(abfrageRegister.getWurzelAbfragen());
	}

}
