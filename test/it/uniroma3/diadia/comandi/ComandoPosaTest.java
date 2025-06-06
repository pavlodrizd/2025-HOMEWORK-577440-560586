package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosaTest {
	
	private Partita p;
	private Comando c;
	private Attrezzo a1, a2;
	private IO io;

	@Before
	public void setUp() throws Exception {
		
        Labirinto labirinto = Labirinto.newBuilder()
                .addStanzaIniziale("s")
                .getLabirinto();
        
		a1 = new Attrezzo("a1", 1);
		a2 = new Attrezzo("a2", 2);
		
		io = new IOConsole(null);
		p = new Partita(labirinto);
		c = new ComandoPosa();
		
		p.getGiocatore().getBorsa().addAttrezzo(a1);
		p.getGiocatore().getBorsa().addAttrezzo(a2);
		
		c.setIO(io);
	}

	@Test
	public void testComandoPosaSenzaParametro() {
		c.setParametro(null);
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1, p.getGiocatore().getBorsa().getAttrezzo("a1"));
		assertEquals(a2, p.getGiocatore().getBorsa().getAttrezzo("a2"));
	}
	
	@Test
	public void testComandoPosaConParametroErrato() {
		c.setParametro("a3");
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1, p.getGiocatore().getBorsa().getAttrezzo("a1"));
		assertEquals(a2, p.getGiocatore().getBorsa().getAttrezzo("a2"));
	}
	
	@Test
	public void testComandoPosaA1() {
		c.setParametro("a1");
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("a1"));
	}
	
	@Test
	public void testComandoPosaA1A2() {
		c.setParametro("a1");
		c.esegui(p);
		
		c.setParametro("a2");
		c.esegui(p);
		
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("a1"));
		
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("a2"));
		}

}
