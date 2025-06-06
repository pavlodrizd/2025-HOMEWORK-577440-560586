package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendiTest {
	
	private Partita p;
	private Comando c;
	private IO io;
	private Attrezzo a1, a2;

	@Before
	public void setUp() throws Exception {
        Labirinto labirinto = Labirinto.newBuilder()
                .addStanzaIniziale("s")
                .addAttrezzo("a1", 1, "s")
                .addAttrezzo("a2", 2, "s")
                .getLabirinto();
		
		p = new Partita(labirinto);
		c = new ComandoPrendi();
		io = new IOConsole(null);
		
		c.setIO(io);
		
		a1 = new Attrezzo("a1", 1);
		a2 = new Attrezzo("a2", 2);
	}

	@Test
	public void testComandoPrendiSenzaParametro() {
		c.setParametro(null);
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1.getNome(), p.getLabirinto().getStanzaCorrente().getAttrezzo("a1").getNome());
		assertEquals(a2.getNome(), p.getLabirinto().getStanzaCorrente().getAttrezzo("a2").getNome());
	}
	
	@Test
	public void testComandoPrendiConParametroErrato() {
		c.setParametro("a3");
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a2"));
		assertEquals(a1.getNome(), p.getLabirinto().getStanzaCorrente().getAttrezzo("a1").getNome());
		assertEquals(a2.getNome(), p.getLabirinto().getStanzaCorrente().getAttrezzo("a2").getNome());
	}
	
	@Test
	public void testComandoPrendiA1() {
		c.setParametro("a1");
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("a1"));
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("a1"));
	}
	
	@Test
	public void testComandoPrendiA1A2() {
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
