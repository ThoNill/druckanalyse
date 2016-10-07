package thomas.nill.druckanalyse;

import java.util.HashMap;

public class DatenZeilenHash extends HashMap<String, DatenZeile> {

    public DatenZeilenHash() {
    }

    public void put(DatenZeile r) {
        put(r.getName(), r);
    }
}
