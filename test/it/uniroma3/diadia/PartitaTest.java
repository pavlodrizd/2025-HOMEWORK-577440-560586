package it.uniroma3.diadia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PartitaTest {
	
	private Partita p;

	@Before
	public void setUp() throws Exception {
		p = new Partita();
	}
	
	@Test
	public void testVinta() {
		assertFalse(p.vinta());
	}
	
	@Test
	public void testSetFinita() {
		p.setFinita();
		assertTrue(p.isFinita());
	}
	
	@Test
	public void testIsFinitaPerCfu() {
		p.getGiocatore().setCfu(0);
		assertTrue(p.isFinita());
	}
	
}
