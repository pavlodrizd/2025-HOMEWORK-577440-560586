package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaBloccataTest {
	private Stanza casa;
	private StanzaBloccata giardino;
	private Partita p;
	private Attrezzo chiave;

	@Before
	public void setUp() throws Exception {
		chiave = new Attrezzo("chiave", 1);
		casa = new Stanza("Casa");
		giardino = new StanzaBloccata("Giardino", "nord", "chiave");
		p = new Partita();
		
		
		p.getLabirinto().setStanzaCorrente(giardino);
		giardino.impostaStanzaAdiacente("nord", casa);
	}

	@Test
	public void testGetStanzaAdiacenteDirezioneBloccata() {
		assertEquals(giardino, giardino.getStanzaAdiacente("nord"));	
	}
	
	@Test
	public void testGetStanzaAdiacenteDirezioneSbloccataConAttrezzo() {
		giardino.addAttrezzo(chiave);
		assertEquals(casa, giardino.getStanzaAdiacente("nord"));
	}
	
	@Test
	public void testGetDescrizioneDirezioneBloccata() {
		assertTrue(giardino.getDescrizione().contains("bloccata"));
	}
	
	@Test
	public void testGetDescrizioneDirezioneSbloccataConAttrezzo() {
		giardino.addAttrezzo(chiave);
		assertFalse(giardino.getDescrizione().contains("bloccata"));
	}

}
