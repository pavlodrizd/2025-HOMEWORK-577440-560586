package it.uniroma3.diadia.giocatore;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {
	
	private Borsa b;
	private Attrezzo a, l, tp;

	@Before
	public void setUp() throws Exception {
		b = new Borsa();
		a = new Attrezzo("A", 1);
		l = new Attrezzo("L", 5);
		tp = new Attrezzo ("TP", 50);
	}

	@Test
	public void testAddAttrezzoLeggero() {
		assertTrue(b.addAttrezzo(l));
	}
	
	@Test
	public void testAddAttrezzoTroppoPesante() {
		assertFalse(b.addAttrezzo(tp));
	}
	
	@Test
	public void testGetPeso() {
		b.addAttrezzo(a);
		b.addAttrezzo(l);
		assertEquals(6, b.getPeso());
	}

}
