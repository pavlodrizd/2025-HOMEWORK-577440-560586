package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;

public class ComandoVai implements Comando {
	private String direzione;
	private IO io;

	@Override
	public void setParametro(String parametro) {
		this.direzione = parametro;
	}

	@Override
	public void esegui(Partita partita) {
		Stanza stanzaCorrente = partita.getLabirinto().getStanzaCorrente();
		if (direzione == null) {
			this.io.mostraMessaggio("Dove vuoi andare? Devi specificare una direzione");
			return;
		}

		Direzione direzioneEnum;
		try {
			direzioneEnum = Direzione.valueOf(this.direzione.toUpperCase());
		} catch (IllegalArgumentException e) {
			this.io.mostraMessaggio("Direzione non valida.");
			return;
		}

		Stanza prossimaStanza = stanzaCorrente.getStanzaAdiacente(direzioneEnum);

		if (prossimaStanza == null) {
            String messaggio = "Direzione inesistente.";
            
            if (stanzaCorrente instanceof StanzaBloccata) {
                StanzaBloccata sb = (StanzaBloccata) stanzaCorrente;
                if (sb.getDirezioneBloccata().equals(direzioneEnum)) { 
                    messaggio = "La direzione " + direzioneEnum + " è bloccata! Devi posare l'attrezzo '" + sb.getAttrezzoSbloccante() + "' in questa stanza per sbloccarla.";
                }
            }
            this.io.mostraMessaggio(messaggio);
            return;
		}

		partita.getLabirinto().setStanzaCorrente(prossimaStanza);
		this.io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getNome());
		partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
	}

	@Override
	public String getNome() {
		return "vai";
	}

	@Override
	public String getParametro() {
		return direzione;
	}

	@Override
	public void setIO(IO io) {
		this.io = io;
	}
}