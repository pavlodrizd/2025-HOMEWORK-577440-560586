package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

public class LabirintoTest {
	
	private Labirinto l;
	private Stanza s1, s2, biblioteca;

	@Before
	public void setUp() throws Exception {
		l = new Labirinto();
		s1 = new Stanza("S1");
		s2 = new Stanza("S2");
		biblioteca = new Stanza("Biblioteca");
		l.setStanzaCorrente(s1);
	}
	
	@Test
	public void testGetStanzaCorrente() {
		assertEquals(s1, l.getStanzaCorrente());
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
