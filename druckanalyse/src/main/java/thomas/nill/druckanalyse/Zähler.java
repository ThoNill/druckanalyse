package thomas.nill.druckanalyse;

import java.util.HashMap;
import java.util.Set;

public class Zähler<K> {
    HashMap<K, Integer> zahlerHash = new HashMap<>();

    public Zähler() {
    }

    public int getAnzahl(K key) {
        Integer i = zahlerHash.get(key);
        if (i == null) {
            return 0;
        }
        return i.intValue();
    }

    public void inc(K key) {
        Integer i = zahlerHash.get(key);
        if (i == null) {
            zahlerHash.put(key, new Integer(1));
        } else {
            zahlerHash.put(key, new Integer(i.intValue() + 1));
        }
    }

    public Set<K> values() {
        return zahlerHash.keySet();
    }

}
