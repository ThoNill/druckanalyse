package thomas.nill.druckanalyse;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class AbfrageRegister extends ZeilenRegister {
    private HashMap<String, Abfrage> abfragen = new HashMap<>();
    private List<Abfrage> wurzelAbfragen = new ArrayList<>();
    private HashMap<String, FeldReihe> feldReihen = new HashMap<>();
    private Deque<Abfrage> aktuelleAbfrage = new ArrayDeque<>();

    public AbfrageRegister() {
    }

    @Override
    protected void bearbeite(DatenZeile zeile) {
        String name = zeile.getName();
        FeldReihe reihe = feldReihen.get(name);
        if (reihe == null) {
            reihe = new FeldReihe(name);
            feldReihen.put(name, reihe);
        }
        zurAktuellenAbfrageHinzufügen(reihe);

    }

    @Override
    protected void bearbeite(String name, String command, String parameter) {
        switch (command) {
        case "sql": {
            if (!abfragen.containsKey(name)) {
                Abfrage abfrage = new Abfrage(name, parameter);
                abfragen.put(name, abfrage);
            }
        }
            break;
        case "start": {
            Abfrage abfrage = abfragen.get(name);
            zurAktuellenAbfrageHinzufügen(abfrage);
            if (aktuelleAbfrage.size() == 0) {
                wurzelAbfragen.add(abfrage);
            }
            aktuelleAbfrage.push(abfrage);

        }
            break;
        case "stop": {
            if (aktuelleAbfrage.size() > 0) {
                aktuelleAbfrage.pop();
            }
        }
            break;

        }

    }

    private void zurAktuellenAbfrageHinzufügen(FeldReihe inout) {
        if (aktuelleAbfrage.size() > 0) {
            Abfrage parent = aktuelleAbfrage.peek();
            parent.addElement(inout);
        }
    }

    public Collection<Abfrage> getAbfragen() {
        return abfragen.values();
    }

    public List<Abfrage> getWurzelAbfragen() {
        return wurzelAbfragen;
    }

    public String getFeldName(FeldVerbindung connection) {
        return abfragen.get(connection.getInName()).getFeld(
                connection.getInIndex());
    }

}
