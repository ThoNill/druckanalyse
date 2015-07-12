package thomas.nill.druckanalyse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class AbfrageRegister extends ZeilenRegister {
	private HashMap<String,Abfrage> abfragen = new HashMap<>();
	private Vector<Abfrage> wurzelAbfragen = new Vector<>();
	private HashMap<String,FeldReihe> feldReihen = new HashMap<>();
	private Stack<Abfrage> aktuelleAbfrage = new Stack<>();
	
	public AbfrageRegister() {
	}
	
	
	@Override
	protected void bearbeite(DatenZeile zeile) {
		String name = zeile.getName();
		FeldReihe reihe = feldReihen.get(name);
		if (reihe == null) {
			reihe = new FeldReihe(name);
			feldReihen.put(name,reihe );
		}
		zurAktuellenAbfrageHinzufügen(reihe);
		
	}
	
	@Override
	protected void bearbeite(String name, String command, String parameter) {
		switch (command) {
		case "sql" : {
			if (!abfragen.containsKey(name)) {
				Abfrage abfrage = new Abfrage(name, parameter);
				abfragen.put(name, abfrage);
			}
		} break;
		case "start" : {
			Abfrage abfrage = abfragen.get(name);
			zurAktuellenAbfrageHinzufügen(abfrage);
			if (aktuelleAbfrage.size()==0) {
				wurzelAbfragen.addElement(abfrage);
			}
			aktuelleAbfrage.push(abfrage);
			
		} break;
		case "stop" : {
			if (aktuelleAbfrage.size()>0) {
				aktuelleAbfrage.pop();
			}
		} break;
		
		}
		
		
	}


	private void zurAktuellenAbfrageHinzufügen(FeldReihe inout) {
		if (aktuelleAbfrage.size()>0) {
			Abfrage parent = aktuelleAbfrage.peek();
			parent.addElement(inout);
		}
	}


	public Collection<Abfrage> getAbfragen() {
		return abfragen.values();
	}


	public Vector<Abfrage> getWurzelAbfragen() {
		return wurzelAbfragen;
	}
	
	public String getFeldName(FeldVerbindung connection) {
		return abfragen.get(connection.getInName()).getFeld(connection.getInIndex());
	}

}
