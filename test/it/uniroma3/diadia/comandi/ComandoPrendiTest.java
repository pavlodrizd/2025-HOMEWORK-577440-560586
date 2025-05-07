package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendiTest {
	
	private Partita p;
	private Comando c;
	private Stanza s;
	private Attrezzo a1, a2;
	private IO io;

	@Before
	public void setUp() throws Exception {
		p = new Partita();
		c = new ComandoPrendi();
		s = new Stanza("S");
		a1 = new Attrezzo("a1", 1);
		a2 = new Attrezzo("a2", 2);
		io = new IOConsole();
		
		p.getLabirinto().setStanzaCorrente(s);
		p.getLabirinto().getStanzaCorrente().addAttrezzo(a1);
		p.getLabirinto().getStanzaCorrente().addAttrezzo(a2);
		
		c.setIO(io);
	}

	@Test
	public void testComandoPrendiSenzaParametro() {
		c.setParametro(null);
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1, p.getLabirinto().getStanzaCorrente().getAttrezzo("a1"));
		assertEquals(a2, p.getLabirinto().getStanzaCorrente().getAttrezzo("a2"));
	}
	
	@Test
	public void testComandoPrendiConParametroErrato() {
		c.setParametro("a3");
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1, p.getLabirinto().getStanzaCorrente().getAttrezzo("a1"));
		assertEquals(a2, p.getLabirinto().getStanzaCorrente().getAttrezzo("a2"));
	}
	
	@Test
	public void testComandoPrendiA1() {
		c.setParametro("a1");
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("a1"));
	}
	
	@Test
	public void testComandoPosaA1A2() {
		c.setParametro("a1");
		c.esegui(p);
		
		c.setParametro("a2");
		c.esegui(p);
		
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("a1"));
		
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("a2"));
		}

}
