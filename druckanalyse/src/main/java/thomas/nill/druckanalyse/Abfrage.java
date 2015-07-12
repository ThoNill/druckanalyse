package thomas.nill.druckanalyse;

import java.util.List;
import java.util.Vector;

public class Abfrage extends FeldReihe {
	private String statement;
	private Vector<FeldReihe> childs;
	List<String> felder = null;

	public Abfrage(String name, String statement) {
		super(name);
		this.statement = statement;
		this.childs = new Vector<>();
	}

	public String getStatement() {
		return statement;
	}

	public void addElement(FeldReihe e) {
		if (!childs.contains(e)) {
			childs.addElement(e);
		}
	}

	public boolean remove(Object o) {
		return childs.remove(o);
	}

	@Override
	public String toString() {
		return "Abfrage [name = " + getName() + " statement=" + statement
				+ ", childs=" + childs + "]";
	}

	public Vector<FeldReihe> getChilds() {
		return childs;
	}

	public List<String> getFelder() {
		if (felder == null) {
			String stmtString = getStatement().toLowerCase();
			stmtString.replaceAll("[\\n\\r\\t]", " ");
			int start = stmtString.indexOf("select ");
			if (start >= 0) {
				start += "select ".length();
				stmtString = stmtString.substring(start);
				int stop = stmtString.indexOf(" from");
				if (stop > 0) {
					stmtString = stmtString.substring(0, stop);
					felder = getFelderZwischenSelectUndFrom(stmtString);
				}
			} else {
				felder = new Vector<>();
			}
		}
		return felder;
	}

	private List<String> getFelderZwischenSelectUndFrom(String stmtString) {
		Vector<String> felder = new Vector<>();
		int letzterStartIndex = 0;
		char s[] = stmtString.toCharArray();
		int anzahlRundeKlammern = 0;
		int anzahlEckigeKlammern = 0;
		int anzahlAnführungsZeichen = 0;
		for (int i = 0; i < s.length; i++) {
			switch (s[i]) {
			case '(':
				anzahlRundeKlammern++;
				break;
			case ')':
				anzahlRundeKlammern--;
				break;
			case '[':
				anzahlEckigeKlammern++;
				break;
			case ']':
				anzahlEckigeKlammern--;
				break;
			case '\'':
				anzahlAnführungsZeichen++;
				break;
			case '\"':
				anzahlAnführungsZeichen++;
				break;
			case ',':
				if (anzahlAnführungsZeichen % 2 == 0
						&& anzahlEckigeKlammern == 0
						&& anzahlRundeKlammern == 0) {
					felder.addElement(stmtString
							.substring(letzterStartIndex, i).trim());
					letzterStartIndex = i + 1;
				}
				break;
			default:
				break;
			}
		}
		felder.add(stmtString.substring(letzterStartIndex).trim());
		return felder;
	}
	
	public String getFeld(int i) {
		return getFelder().get(i);
	}

}
