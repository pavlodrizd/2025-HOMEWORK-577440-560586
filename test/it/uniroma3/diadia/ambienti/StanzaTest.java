package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaTest {
	
	private Stanza s1, s2;
	private Attrezzo a, b, c, d;

	
	@Before
	public void setUp() throws Exception {
		s1 = new Stanza ("S1");
		s2 = new Stanza ("S2");
		a = new Attrezzo("A", 1);
		b = new Attrezzo("B", 1);
		c = new Attrezzo("C", 1);
		d = new Attrezzo("D", 1);
	}
	


	@Test
	public void testGetStanzaAdiacente() {
		assertNull(s1.getStanzaAdiacente(Direzione.NORD));
	}
	
	@Test
	public void testImpostaStanzaAdiacente() {
		s1.impostaStanzaAdiacente(Direzione.NORD, s2);
		System.out.println(s1.toString());
		assertEquals(s2, s1.getStanzaAdiacente(Direzione.NORD));
	}
	
	@Test
	public void testAddAttrezzo() {
		assertTrue(s1.addAttrezzo(a));		
	}
	
	@Test
	public void testHasAttrezzo() {
		s1.addAttrezzo(a);
		assertTrue(s1.hasAttrezzo("A"));
	}
	
	@Test
	public void testRimuoviAttrezzo() {
		s1.addAttrezzo(a);
		assertTrue(s1.removeAttrezzo(a));
	}
	
	
	@Test
	public void testRimuoviAttrezzoInMezzo() {
		s1.addAttrezzo(a);
		s1.addAttrezzo(b);
		s1.addAttrezzo(c);
		s1.addAttrezzo(d);
		
		assertTrue(s1.removeAttrezzo(b));
		assertFalse(s1.hasAttrezzo("B"));
		
		
		assertTrue(s1.hasAttrezzo("A"));
		assertTrue(s1.hasAttrezzo("C"));
		assertTrue(s1.hasAttrezzo("D"));
		
		s1.addAttrezzo(b);
		assertTrue(s1.hasAttrezzo("B"));
	}
	
	@Test
	public void testGetAttrezzo() {
		s1.addAttrezzo(a);
		assertEquals(a, s1.getAttrezzo("A"));
	}
	

}
