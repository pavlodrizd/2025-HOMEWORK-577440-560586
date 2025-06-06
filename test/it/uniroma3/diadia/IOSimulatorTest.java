package it.uniroma3.diadia;

import static org.junit.Assert.*;


import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;

public class IOSimulatorTest {

	private Partita p;
	private DiaDia gioco;
	private IOSimulator ioSim;

	@Before
	public void setUp() throws Exception {
		Labirinto lab = Labirinto.newBuilder()
				.addStanzaIniziale("s1")
				.addStanzaBloccata("s2", "nord", "chiave")
				.addStanzaVincente("vincente")
				.addAttrezzo("chiave", 1)
				.addAttrezzo("chiave", 1, "s1")
				.addAdiacenza("s1", "s2", Direzione.EST)
				.addAdiacenza("s2", "vincente", Direzione.NORD)
				.getLabirinto();
				
		p = new Partita(lab);
	}

	@Test
	public void testGiocatoreRaggiungeStanzaVincente() {
		List<String> comandi = Arrays.asList(
			"prendi chiave",
			"vai est",
			"posa chiave",
			"vai nord"
		);

		ioSim = new IOSimulator(comandi);
		gioco = new DiaDia(p, ioSim);

		gioco.gioca();

		List<String> outputMessages = ioSim.getMessaggiStampati();

		assertTrue(outputMessages.get(0).contains("Ti trovi nell'Universita'"));
		assertTrue(outputMessages.get(2).contains("s2"));
		assertTrue(outputMessages.get(4).contains("vincente"));

		assertEquals("vincente", p.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	public void testGiocatoreRaggiungeStanzaBloccata(){
		List<String> comandi = Arrays.asList(
			"vai est",
			"guarda",
			"vai nord"
		);

		ioSim = new IOSimulator(comandi);
		gioco = new DiaDia(p, ioSim);

		gioco.gioca();

		List<String> outputMessages = ioSim.getMessaggiStampati();

		assertTrue(outputMessages.get(0).contains("Ti trovi nell'Universita'"));
		assertTrue(outputMessages.get(1).contains("s2"));

		assertEquals("s2", p.getLabirinto().getStanzaCorrente().getNome());
	}



}
