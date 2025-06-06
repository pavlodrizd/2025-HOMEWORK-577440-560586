package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio {
    
    private final static String RINGHIO = "Grr (HAI PERSO 1 CFU)";
    private final static String MESSAGGIO_OSSO = "Bau bau! (Il cane prende l'osso e lascia cadere un attrezzo)";
    private final static String CIBO_PREFERITO = "osso";

    public Cane(String nome, String presentazione) {
        super(nome, presentazione);
    }

    @Override
    public String agisci(Partita partita) {
        partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);
        return RINGHIO;
    }

    @Override
    public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
        if(attrezzo.getNome().equals(CIBO_PREFERITO)) {
            if(!partita.getGiocatore().getBorsa().isEmpty()) {
                Attrezzo attrezzoCasuale = new Attrezzo("Guinzaglio", 2);
                partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoCasuale);
            }
            return MESSAGGIO_OSSO;
        } else {
            partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);
            return RINGHIO;
        }
    }
}