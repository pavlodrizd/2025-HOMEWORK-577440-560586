package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaTest {
	
	private Stanza s1, s2;
	private Attrezzo a;
	
	@Before
	public void setUp() throws Exception {
		s1 = new Stanza ("S1");
		s2 = new Stanza ("S2");
		a = new Attrezzo("A", 1);
	}
	


	@Test
	public void testGetStanzaAdiacente() {
		assertNull(s1.getStanzaAdiacente("nord"));
	}
	
	@Test
	public void testImpostaStanzaAdiacente() {
		s1.impostaStanzaAdiacente("nord", s2);
		assertEquals(s2, s1.getStanzaAdiacente("nord"));
	}
	
	@Test
	public void testAddAttrezzo() {
		assertTrue(s1.addAttrezzo(a));		
	}

}
