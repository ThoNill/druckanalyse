package thomas.nill.druckanalyse;

public class DatenZeile {
    private String name;
    private String[] values;

    public DatenZeile(String name, String values) {
        super();
        this.name = name;
        this.values = dieWerteAusZeileExtrahieren(values);
    }

    private String[] dieWerteAusZeileExtrahieren(String values) {
        if (values == null) {
            return new String[] {};
        }
        return values.split("\\|");
    }

    public String getName() {
        return name;
    }

    public String getValue(int i) {
        return values[i];
    }

    public int size() {
        return values.length;
    }

}
