package thomas.nill.druckanalyse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.List;
import java.util.Vector;

public class DruckAnalyse extends ZeilenRegister {
	AbfrageRegister abfrageRegister = new AbfrageRegister();
	FeldReihenRegister connectionRegister = new FeldReihenRegister();
	List<String> ohneStopOhneData = new Vector<String>();
	List<String> ohneStopMitData = new Vector<String>();

	public DruckAnalyse() {

	}

		
	public void ausgabe(OutputStream out) {
		Ausgabe ausgabe = new Ausgabe(out,
				connectionRegister.getMatchingConnections(), abfrageRegister);
		ausgabe.print(abfrageRegister.getWurzelAbfragen());
	}

	public static void main(String args[]) {
		try {
			if (args.length >= 2) {
				if (args.length >= 3) {
					ZeilenRegister.PREFIXLÄNGE = Integer.parseInt(args[2]);
				}
				ErgänzeFehlendeStopsRegister ergänzeStops = new ErgänzeFehlendeStopsRegister();
				ergänzeStops.readFile(args[0]);
				
				DruckAnalyse analyse = new DruckAnalyse();
				analyse.ohneStopMitData = ergänzeStops.getAbfragenOhneStopMitData();
				analyse.ohneStopOhneData = ergänzeStops.getAbfragenOhneStopUndData();
				analyse.readFile(args[0]);
				analyse.writeFile(args[1]);
			} else {
				System.out
						.println("java -jar druckanalyse.jar thomas.nill.druckanalyse.DruckAnalyse <Protokolldatei> <Ergebnisdatei> ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeFile(String fileName) throws IOException {
		FileOutputStream out = new FileOutputStream(fileName);
		ausgabe(out);
		out.close();

	}

	@Override
	protected void bearbeite(String name, String command, String parameter) {
		abfrageRegister.bearbeite(name,command,parameter);
		connectionRegister.bearbeite(name,command,parameter);
		if ("start".equals(command) && ohneStopOhneData.contains(name)) {
			stopDazu(name, parameter);
		}
		if ("data".equals(command) && ohneStopMitData.contains(name)) {
			stopDazu(name, parameter);
		}
	}


	protected void stopDazu(String name, String parameter) {
		abfrageRegister.bearbeite(name,"stop",parameter);
		connectionRegister.bearbeite(name,"stop",parameter);
	}

	@Override
	protected void bearbeite(DatenZeile zeile) {
		abfrageRegister.bearbeite(zeile);
		connectionRegister.bearbeite(zeile);
		
	}



}
