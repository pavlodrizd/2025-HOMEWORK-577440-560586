package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class LabirintoTest {
	
	private Labirinto l;
	private Stanza s2;

	@Before
	public void setUp() throws Exception {
		l = Labirinto.newBuilder()
				.addStanzaIniziale("S1")
				.addStanzaVincente("Biblioteca")
				.addStanza("S2")
				.getLabirinto();
	}
	
	
	@Test
	public void testGetStanzaCorrente() {
		assertEquals((new Stanza("S1").getNome()), l.getStanzaCorrente().getNome());
	}

	@Test
	public void testSetStanzaCorrente() {
		l.setStanzaCorrente(s2);
		assertEquals(s2, l.getStanzaCorrente());
	}
	
	@Test
	public void testGetStanzaVincente() {
		assertEquals("Biblioteca", l.getStanzaVincente().getNome());
	}
	
	

}
