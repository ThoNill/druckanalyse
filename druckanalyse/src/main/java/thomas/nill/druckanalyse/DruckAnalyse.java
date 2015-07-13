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
				DruckAnalyse analyse = new DruckAnalyse();
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

	private void readInputStream(Reader input) {
		BufferedReader bStream = new BufferedReader(input);
		bStream.lines().forEach(line -> inputLine(line));
	}

	private void readFile(String filename) throws IOException {
		Reader fileStream = new FileReader(filename);
		readInputStream(fileStream);
		fileStream.close();
	}

}
