package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;


public class StanzaBuiaTest {
	
	StanzaBuia s;
	Attrezzo lanterna;


	@Before
	public void setUp() throws Exception {		
		lanterna = new Attrezzo("lanterna", 2);
		s = new StanzaBuia("stanza buia", "lanterna");
	}

	@Test
	public void testStanzaBuiaSenzaAttrezzoSpeciale() {
		assertEquals("qui c'è un buio pesto", s.getDescrizione());
		assertFalse(s.getDescrizione().contains("lanterna"));
	}
	
	@Test
	public void testStanzaBuiaConAttrezzoSpeciale() {
		
		s.addAttrezzo(lanterna);
		
		assertTrue(s.getDescrizione().contains("lanterna"));
	}

}
