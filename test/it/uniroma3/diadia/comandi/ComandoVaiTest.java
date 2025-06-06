package it.uniroma3.diadia.comandi;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;

public class ComandoVaiTest {

    private Partita partita;
    private ComandoVai comandoVai;
    private IO io;

    @Before
    public void setUp() throws Exception {
        Labirinto labirinto = Labirinto.newBuilder()
            .addStanzaIniziale("Atrio")
            .addStanza("Aula N10")
            .addStanzaVincente("Biblioteca")
            .addAdiacenza("Atrio", "Aula N10", Direzione.SUD)
            .addAdiacenza("Aula N10", "Atrio", Direzione.NORD)
            .addAdiacenza("Atrio", "Biblioteca", Direzione.NORD)
            .getLabirinto();
        
        this.partita = new Partita(labirinto);
        
        this.comandoVai = new ComandoVai();
        List<String> listaVuota = new ArrayList<String>();
        this.io = new IOSimulator(listaVuota);
        comandoVai.setIO(io);
    }
    @Test
    public void testVaiDirezioneValida() {
        comandoVai.setParametro("nord");
        comandoVai.esegui(partita);
        
        assertEquals("Biblioteca", partita.getLabirinto().getStanzaCorrente().getNome());
    }

    @Test
    public void testVaiDirezioneNonValida() {
        comandoVai.setParametro("est");
        comandoVai.esegui(partita);
        
        assertEquals("Atrio", partita.getLabirinto().getStanzaCorrente().getNome());
    }

    @Test
    public void testVaiSenzaParametro() {
        comandoVai.setParametro(null);
        comandoVai.esegui(partita);
        
        assertEquals("Atrio", partita.getLabirinto().getStanzaCorrente().getNome());
    }

    @Test
    public void testVaiMultipliMovimenti() {
        comandoVai.setParametro("sud");
        comandoVai.esegui(partita);
        assertEquals("Aula N10", partita.getLabirinto().getStanzaCorrente().getNome());
        
        comandoVai.setParametro("nord");
        comandoVai.esegui(partita);
        assertEquals("Atrio", partita.getLabirinto().getStanzaCorrente().getNome());

        comandoVai.setParametro("nord");
        comandoVai.esegui(partita);
        assertEquals("Biblioteca", partita.getLabirinto().getStanzaCorrente().getNome());
    }

    @Test
    public void testVaiConIOSimulator() {
        List<String> comandi = new ArrayList<String>();
        comandi.add("vai nord");
        
        IOSimulator io = new IOSimulator(comandi);
        comandoVai.setIO(io);
        
        comandoVai.setParametro("nord");
        comandoVai.esegui(partita);
        
        assertEquals("Biblioteca", partita.getLabirinto().getStanzaCorrente().getNome());
        assertEquals(1, io.getMessaggiStampati().size());
        assertEquals("Biblioteca", io.getMessaggiStampati().get(0));
    }
}