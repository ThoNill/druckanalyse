package thomas.nill.druckanalyse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public abstract class ZeilenRegister {
	public static int PREFIXLÄNGE = 10;
	
	public ZeilenRegister() {
		super();
	}

	public void zeileBearbeiten(String line) {
		String values[] = line.split("\\:");
		if (values.length >= 3) {
			if ("input".equals(values[0])) {
				String name = values[1];
				String command = values[2];
				String parameter = (values.length == 4) ? values[3] : "";
				bearbeite(name, command, parameter);
			}
		} else {
			if (line.length() >= PREFIXLÄNGE && !"".equals(line.trim())) {
				DatenZeile zeile = new DatenZeile(line.substring(0, PREFIXLÄNGE).trim(),
						line.substring(PREFIXLÄNGE));
				bearbeite(zeile);
			}
		}
	}

	protected abstract void bearbeite(String name, String command,
			String parameter);

	protected abstract void bearbeite(DatenZeile zeile);
	
	private void readInputStream(Reader input) {
		BufferedReader bStream = new BufferedReader(input);
		bStream.lines().forEach(line -> zeileBearbeiten(line));
	}

	public void readFile(String filename) throws IOException {
		Reader fileStream = new FileReader(filename);
		readInputStream(fileStream);
		fileStream.close();
	}

}