package it.uniroma3.diadia.personaggi;

import java.util.Map;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {

    private static final String MESSAGGIO_SALUTATA = "Solo perche' mi hai salutato, ti mando in una bella stanza piena di attrezzi!";
    private static final String MESSAGGIO_NON_SALUTATA = "Sei proprio una persona cattiva, prima si saluta! Ti mando nella stanza con meno attrezzi!";

    public Strega(String nome, String presentaz) {
        super(nome, presentaz);
    }

    @Override
    public String agisci(Partita partita) {
        String msg;
        Stanza stanzaCorrente = partita.getLabirinto().getStanzaCorrente();
        Map<Direzione, Stanza> stanzeAdiacenti = stanzaCorrente.getStanzeAdiacenti();

        Stanza maxAttrezzi = null;
        Stanza minAttrezzi = null;

        for (Stanza stanza : stanzeAdiacenti.values()) {
            if (maxAttrezzi == null || stanza.getAttrezzi().size() > maxAttrezzi.getAttrezzi().size()) {
                maxAttrezzi = stanza;
            }
            if (minAttrezzi == null || stanza.getAttrezzi().size() < minAttrezzi.getAttrezzi().size()) {
                minAttrezzi = stanza;
            }
        }

        if (this.haSalutato()) {
            partita.getLabirinto().setStanzaCorrente(maxAttrezzi);
            msg = MESSAGGIO_SALUTATA;
        } else {
            partita.getLabirinto().setStanzaCorrente(minAttrezzi);
            msg = MESSAGGIO_NON_SALUTATA;
        }

        return msg;
    }

    @Override
    public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
        return "AHAHAHAHAHAHHA";
    }
}
