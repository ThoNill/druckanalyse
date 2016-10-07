package thomas.nill.druckanalyse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DatenZeilenTest {

    @Test
    public void test1() {
        DatenZeile r = new DatenZeile("test", "das|ist|ein|test");
        assertEquals("test", r.getName());
        assertEquals(4, r.size());
        assertEquals("das", r.getValue(0));
        assertEquals("ist", r.getValue(1));
        assertEquals("ein", r.getValue(2));
        assertEquals("test", r.getValue(3));
    }

    @Test
    public void test2() {
        DatenZeile r = new DatenZeile("test", "");
        assertEquals("test", r.getName());
        assertEquals(1, r.size());
        assertEquals("", r.getValue(0));

    }

    @Test
    public void test3() {
        DatenZeile r = new DatenZeile("test", null);
        assertEquals("test", r.getName());
        assertEquals(0, r.size());

    }

}