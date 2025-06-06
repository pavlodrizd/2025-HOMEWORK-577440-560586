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
        
        Labirinto labirinto = Labirinto.newBuilder()
                .addAttrezzo(chiave.getNome(), chiave.getPeso()) 
                .addStanzaBloccata("Giardino", "nord", "chiave")
                .addStanza("Casa")
                .addAdiacenza("Giardino", "Casa", Direzione.NORD)
                .addStanzaIniziale("Giardino")
                .getLabirinto();

        p = new Partita(labirinto);

        giardino = (StanzaBloccata) p.getLabirinto().getStanzaCorrente();
        casa = p.getLabirinto().getStanzaCorrente().getStanzaAdiacente(Direzione.NORD);
    }

    @Test
    public void testGetStanzaAdiacenteDirezioneBloccata() {
        assertEquals(null, giardino.getStanzaAdiacente(Direzione.NORD));
    }

    @Test
    public void testGetStanzaAdiacenteDirezioneSbloccataConAttrezzo() {
        Labirinto.newBuilder().addAttrezzo("chiave", 1, "giardino");
        assertEquals(casa, giardino.getStanzaAdiacente(Direzione.NORD));
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
