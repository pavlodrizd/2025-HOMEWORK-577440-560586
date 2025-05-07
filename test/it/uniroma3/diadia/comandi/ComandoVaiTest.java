package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVaiTest {
	
	private Partita p;
	private Stanza s1, s2;
	private Comando c;
	private IO io;

	@Before
	public void setUp() throws Exception {
		
		p = new Partita();
		
		s1 = new Stanza("s1");
		s2 = new Stanza("s2");
		
		p.getLabirinto().setStanzaCorrente(s1);
		p.getLabirinto().getStanzaCorrente().impostaStanzaAdiacente("up", s2);
		
		io = new IOConsole();
		
		c = new ComandoVai();
		c.setIO(io);
		
	}

	@Test
	public void testComandoVaiSenzaParametro() {
		c.setParametro(null);
		c.esegui(p);
		assertEquals(s1, p.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	public void testComandoVaiConParametroErrato() {
		c.setParametro("down");
		c.esegui(p);
		assertEquals(s1, p.getLabirinto().getStanzaCorrente());
		
	}
	
	@Test
	public void testComandoVaiUp() {
		c.setParametro("up");
		c.esegui(p);
		assertEquals(s2, p.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	public void testCfuDiminuitiDopoCambioStanza() {
		p.getGiocatore().setCfu(1);
		
		c.setParametro("up");
		c.esegui(p);
		
		assertEquals(0, p.getGiocatore().getCfu());
	}
	


}
