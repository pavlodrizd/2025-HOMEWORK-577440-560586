package it.uniroma3.diadia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Labirinto;

public class PartitaTest {
	
	private Partita p;

	@Before
	public void setUp() throws Exception {
		p = new Partita(Labirinto.newBuilder().getLabirinto());
	}
	
	@Test
	public void testVinta() {
		assertTrue(p.vinta());
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
