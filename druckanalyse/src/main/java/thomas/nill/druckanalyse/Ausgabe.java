package thomas.nill.druckanalyse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Ausgabe {
	OutputStream outStream;
	List<FeldVerbindung> verbindungen;
	AbfrageRegister register = null;
	

	public Ausgabe(List<FeldVerbindung> connections,AbfrageRegister register) {
		this(System.out, connections,register);
	}

	public Ausgabe(OutputStream outStream, List<FeldVerbindung> connections,AbfrageRegister register) {
		this.outStream = outStream;
		this.verbindungen = connections;
		this.register = register;
	}

	public void print(Collection<Abfrage> abfragen) {
		for (Abfrage a : abfragen) {
			print(a, 1);
		}
	}

	private void print(String text) {
		try {
			outStream.write(text.getBytes(Charset.defaultCharset()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(int stufe) {
		try {
			while (stufe > 0) {
				outStream.write(' ');
				outStream.write(' ');
				stufe--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(Abfrage a, int stufe) {
		print(stufe);
		print("sql: " + a.getName() + " '" + a.getStatement() + "' \n");
		print(stufe);
		print("into: (");

		StringBuffer parameterliste = new StringBuffer();
		a.getFelder().stream().forEach(p -> parameterliste.append("," + a.getName() + "_" + p) );
		
		String sParameterListe = parameterliste.toString();
		
		if (sParameterListe.length()>1) {
			print(sParameterListe.substring(1));
		}
		print(") {\n");

		for (FeldReihe io : a.getChilds()) {
			if (io instanceof Abfrage) {
				print((Abfrage) io, stufe + 1);
			} else {
				printOutput(io, stufe + 1);
			}
		}
		print(stufe);
		print("}\n");

	}

	private void printOutput(FeldReihe io, int stufe) {
		print(stufe);
		print("export: " + io.getName() + " (");

		Object o[] = verbindungen.stream()
				.filter(p -> p.getOutName().equals(io.getName()))
				.sorted(Comparator.comparing(e -> e.getOutIndex())).toArray();
		int pos = 0;
		for (int i = 0; i < o.length; i++) {
			FeldVerbindung c = (FeldVerbindung) o[i];
			while (pos < c.getOutIndex()) {
				if (pos > 0) {
					print(",");
				}
				print("unbestimmt");
				pos++;
			}
			if (pos > 0 ) {
				print(",");
			}
			
			print(c.getInName() + "_" + register.getFeldName(c));
			pos++;
		}
		print(")\n");

	}

}
