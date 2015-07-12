package thomas.nill.druckanalyse;

import java.util.List;

public class FeldReihenRegister extends ZeilenRegister {
	DatenZeilenHash datenZeilen = new DatenZeilenHash();
	VerbindungsStatistik statistik = new VerbindungsStatistik();
	
	public FeldReihenRegister() {
	}
	
	
	@Override
	protected void bearbeite(String name, String command, String parameter) {
		switch (command) {
		case "start" : {
			DatenZeile out = new DatenZeile(name,parameter);
			statistik.compare(datenZeilen, out);
		} break;
		case "data" : {
			datenZeilen.put(new DatenZeile(name,parameter));
		} break;
		case "stop" : {
			datenZeilen.remove(name);
		} break;
		
		}
		
		
	}

	@Override
	protected void bearbeite(DatenZeile zeile) {
		statistik.compare(datenZeilen,zeile);
	}

	public List<FeldVerbindung> getMatchingConnections() {
		return statistik.getMatchingConnections();
	}

}
