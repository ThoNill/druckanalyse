package thomas.nill.druckanalyse;

import org.junit.Test;
import static org.junit.Assert.*;


public class VerbindungsStatistikTest {
	
	@Test
	public void test1() {
		VerbindungsStatistik statistik = new VerbindungsStatistik();
		DatenZeilenHash inRows = new DatenZeilenHash();
		inRows.put( new DatenZeile("test1","das|ist|ein|test"));
		inRows.put( new DatenZeile("test2","ein|mal|ist|keinmal"));
	
		DatenZeile out = new DatenZeile("test2","ein|mal|ist|nix");
		statistik.compare(inRows, out);
		
		assertEquals(5,statistik.getMatchingConnections().size());
	}
	
	
	@Test
	public void test2() {
		VerbindungsStatistik statistik = new VerbindungsStatistik();
		DatenZeilenHash inRows = new DatenZeilenHash();
		inRows.put( new DatenZeile("test1","das|IST|ein|test"));
		inRows.put( new DatenZeile("test2","ein|mal|ist|keinmal"));
	
		DatenZeile out = new DatenZeile("test2","ein|mal|ist|nix");
		
		statistik.compare(inRows, out);
		
		assertEquals(5,statistik.getMatchingConnections().size());
	}
	
	@Test
	public void test3() {
		VerbindungsStatistik statistik = new VerbindungsStatistik();
		DatenZeilenHash inRows = new DatenZeilenHash();
		inRows.put( new DatenZeile("test1","das|IST|ein|test"));
		inRows.put( new DatenZeile("test2","ein|1,20|ist|keinmal"));
	
		DatenZeile out = new DatenZeile("test2","ein|1.20|ist|nix");
		
		statistik.compare(inRows, out);
		
		assertEquals(5,statistik.getMatchingConnections().size());
	}
	
	
	@Test
	public void test4() {
		VerbindungsStatistik statistik = new VerbindungsStatistik();
		DatenZeilenHash inRows = new DatenZeilenHash();
		inRows.put( new DatenZeile("test1","das|ist|ein|test"));
		inRows.put( new DatenZeile("test2","ein|mal|ist|keinmal"));
	
		DatenZeile out = new DatenZeile("test2","ein|mal|ist|nix");
		statistik.compare(inRows, out);
		out = new DatenZeile("test2","ein|mal|ist|nix");
		statistik.compare(inRows, out);
		
		assertEquals(5,statistik.getMatchingConnections().size());
	}
	
}