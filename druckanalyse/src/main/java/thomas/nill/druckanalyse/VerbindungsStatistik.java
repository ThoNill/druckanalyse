package thomas.nill.druckanalyse;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;

public class VerbindungsStatistik implements Predicate<FeldVerbindung>{
	private Zähler<FeldVerbindung> verbindungen = new Zähler<>();
	private Zähler<MöglicheTreffer> zähler = new Zähler<>();
	private float grenze = 0.9f;


	public VerbindungsStatistik() {
	}

	public void compare(HashMap<String, DatenZeile> inMap, DatenZeile out) {
		for (DatenZeile in : inMap.values()) {
			compare(in,out);
		}
	}

	private void compare(DatenZeile in, DatenZeile out) {
		zähleMöglicheTreffer(in, out);
		for (int rin = 0; rin < in.size(); rin++) {
			for (int rout = 0; rout < out.size(); rout++) {
				compare(in, rin, out, rout);
			}
		}
	}

	private void compare(DatenZeile in, int rin, DatenZeile out, int rout) {
		
		
		String vin = in.getValue(rin);
		String vout = out.getValue(rout);
		if (compare(vin, vout)) {
			FeldVerbindung conTest = new FeldVerbindung(in.getName(), rin,
					out.getName(), rout);
			 verbindungen.inc(conTest);
			
		}
	}

	protected void zähleMöglicheTreffer(DatenZeile in, DatenZeile out) {
		MöglicheTreffer zf= new MöglicheTreffer(in.getName(), out.getName());
		zähler.inc(zf);
		
	}

	private boolean compare(String vin, String vout) {
		if (vin.equals(vout)) {
			return true;
		}
		vin = vin.toLowerCase();
		vout = vout.toLowerCase();
		if (vin.equals(vout)) {
			return true;
		}
		vin = vin.replaceAll("[\\.\\,]", "");
		vout = vout.replaceAll("[\\.\\,]", "");
		if (vin.equals(vout)) {
			return true;
		}
		return false;
	}

	public List<FeldVerbindung> getMatchingConnections() {
		Vector<FeldVerbindung> möglicheVerbindungen = new Vector<>();
		Object objs[] = verbindungen.values().stream().filter(this).toArray();
		for(Object o : objs) {
			möglicheVerbindungen.add((FeldVerbindung)o);
		}
		return möglicheVerbindungen;
	}

	@Override
	public boolean test(FeldVerbindung t) {
		MöglicheTreffer z= new MöglicheTreffer(t.getInName(), t.getOutName());
		int iz = zähler.getAnzahl(z);
		int it = verbindungen.getAnzahl(t);
		
		return ((float)it) / ((float)iz) > grenze ;
		
	}
	
}
