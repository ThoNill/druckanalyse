package thomas.nill.druckanalyse;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DruckanalyseTest {
    private static final Logger LOG = LogManager
            .getLogger(DruckanalyseTest.class);

    @Test
    public void test1() {
        DruckAnalyse register = new DruckAnalyse();

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
        register.zeileBearbeiten("");

        register.zeileBearbeiten("input:ab1:stop");
        String erwartet = "sql: ab1 'select name , gebdatum, ort from kunde' into: (ab1_name,ab1_gebdatum,ab1_ort) { sql: ab2 'select  ort from adresse ' into: (ab2_ort) { export: name (ab1_name,unbestimmt,ab2_ort)   }  }";

        vergleichen(register, erwartet);

    }

    protected void vergleichen(DruckAnalyse register, String erwartet) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            register.ausgabe(output);
            String ergebnis;
            ergebnis = output.toString("UTF-8");
            ergebnis = aufbereitenZumVergleich(ergebnis);

            String aufbereitetesErwartet = aufbereitenZumVergleich(erwartet);
            assertEquals(aufbereitetesErwartet, ergebnis);
        } catch (UnsupportedEncodingException e) {
            LOG.error("Ungewollter Fehler", e);
        }
    }

    protected String aufbereitenZumVergleich(String startErgebnis) {
        String ergebnis = startErgebnis;
        ergebnis = ergebnis.replaceAll("\n", " ");
        ergebnis = ergebnis.replaceAll("\r", " ");
        return ergebnis.replaceAll(" ", "");
    }

    @Test
    public void test2() {
        DruckAnalyse register = new DruckAnalyse();
        register.ohneStopMitData.add("ab2");

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

        register.zeileBearbeiten("input:ab1:stop");

        String erwartet = "sql: ab1 'select name , gebdatum, ort from kunde' into: (ab1_name,ab1_gebdatum,ab1_ort) { sql: ab2 'select  ort from adresse ' into: (ab2_ort) {} export: name (ab1_name,unbestimmt,ab2_ort)  }";

        vergleichen(register, erwartet);

    }

}