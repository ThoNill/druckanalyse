package thomas.nill.druckanalyse;

public abstract class ZeilenRegister {

	public ZeilenRegister() {
		super();
	}

	public void zeileBearbeiten(String line) {
		String values[] = line.split("\\:");
		if (values.length>=3) {
			if ("input".equals(values[0])) {
				 String name = values[1];
				 String command = values[2];
				 String parameter= (values.length==4) ? values[3] : "";
				 bearbeite(name,command,parameter);
			}
		} else {
			DatenZeile zeile= new DatenZeile(line.substring(0, 10).trim(),line.substring(10));
			bearbeite(zeile);
		}
	}
	
	protected abstract void bearbeite(String name, String command, String parameter) ;
	protected abstract void bearbeite(DatenZeile zeile) ;

}