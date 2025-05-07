package it.uniroma3.diadia;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class IOSimulatorTest {
	
	private Partita p;
	private Stanza s1, s2, vincente;
	private Attrezzo chiave;
	private DiaDia gioco;
	private IOSimulator ioSim;

	@Before
	public void setUp() throws Exception {
		
		p = new Partita();
		
		
		s1 = new Stanza("s1");
		s2 = new StanzaBloccata("s2", "nord", "chiave");
		vincente = new Stanza("vincente");
		chiave = new Attrezzo("chiave", 1);

		s1.addAttrezzo(chiave);
		s1.impostaStanzaAdiacente("est", s2);
		s2.impostaStanzaAdiacente("nord", vincente);
		p.getLabirinto().setStanzaCorrente(s1);
		p.getLabirinto().setStanzaVincente(vincente);
				
	}

	@Test
	public void testGiocatoreRaggiungeStanzaVincente() {
	    String[] comandi = {
	    	"prendi chiave",
	        "vai est",
	        "posa chiave",
	        "vai nord"
	    };
	   
	    ioSim = new IOSimulator(comandi);
	    gioco = new DiaDia(p, ioSim);
	    
	    gioco.gioca();
	    
	    //Primo messaggio: BENVENUTO
	    assertTrue(ioSim.getMessaggi()[0].contains("Ti trovi nell'Universita'"));
	    assertTrue(ioSim.getMessaggi()[2].contains("s2"));
	    assertTrue(ioSim.getMessaggi()[4].contains("vincente"));

	    assertEquals("vincente", p.getLabirinto().getStanzaCorrente().getNome());
	}
	
	@Test
	public void testGiocatoreRaggiungeStanzaBloccata(){
		String[] comandi = {
				"vai est",
				"guarda",
				"vai nord"
		};
		
		ioSim = new IOSimulator(comandi);
	    gioco = new DiaDia(p, ioSim);
	    
	    gioco.gioca();
	    
	    //Primo messaggio: BENVENUTO
	    assertTrue(ioSim.getMessaggi()[0].contains("Ti trovi nell'Universita'"));
	    assertTrue(ioSim.getMessaggi()[1].contains("s2"));
	    assertTrue(ioSim.getMessaggi()[2].contains("nord è bloccata"));

	    assertEquals("s2", p.getLabirinto().getStanzaCorrente().getNome());	
	}

}
