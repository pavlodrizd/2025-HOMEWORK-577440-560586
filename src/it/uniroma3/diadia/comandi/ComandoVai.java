package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

/**
 * Cerca di andare in una direzione. Se c'e' una stanza ci entra e ne stampa il
 * nome, altrimenti stampa un messaggio di errore
 */

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
		Stanza prossimaStanza = null;
		if (direzione == null) {
			this.io.mostraMessaggio("Dove vuoi andare? Devi specificare una direzione");
			return;
		}

		prossimaStanza = stanzaCorrente.getStanzaAdiacente(this.direzione);
		if (prossimaStanza == null) {
			this.io.mostraMessaggio("Direzione inesistente");
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
		this.io=io;
		
	}
	
	

}

