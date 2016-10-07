package thomas.nill.druckanalyse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ErgänzeFehlendeStopsRegister extends ZeilenRegister {
    Zähler<String> stopZählen = new Zähler<>();
    Zähler<String> dataZählen = new Zähler<>();
    Set<String> abfragen = new HashSet<>();

    public ErgänzeFehlendeStopsRegister() {
    }

    @Override
    protected void bearbeite(String name, String command, String parameter) {
        switch (command) {
        case "start": {
            abfragen.add(name);
        }
            break;
        case "data": {
            dataZählen.inc(name);
        }
            break;
        case "stop": {
            stopZählen.inc(name);
            break;
        }
        }

    }

    @Override
    protected void bearbeite(DatenZeile zeile) {
    }

    public List<String> getAbfragenOhneStopUndData() {
        List<String> ohneStopUndData = new ArrayList<>();
        for (String name : abfragen) {
            if (dataZählen.getAnzahl(name) == 0
                    && stopZählen.getAnzahl(name) == 0) {
                ohneStopUndData.add(name);
            }
        }
        return ohneStopUndData;
    }

    public List<String> getAbfragenOhneStopMitData() {
        List<String> ohneStopUndData = new ArrayList<>();
        for (String name : abfragen) {
            if (dataZählen.getAnzahl(name) > 0
                    && stopZählen.getAnzahl(name) == 0) {
                ohneStopUndData.add(name);
            }
        }
        return ohneStopUndData;
    }

}
