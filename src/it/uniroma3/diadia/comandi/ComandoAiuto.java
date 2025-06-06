package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

/**
 * Stampa informazioni di aiuto.
 */

public class ComandoAiuto extends AbstractComando implements Comando{
	
	static final private String[] elencoComandi = { "vai", "guarda", "prendi", "posa", "saluta", "interagisci", "regala"};
	private IO io;
	
	@Override
	public void esegui(Partita partita) {
		for (int i = 0; i < elencoComandi.length; i++) {
			this.io.mostraMessaggio(elencoComandi[i] + " ");
		}
		this.io.mostraMessaggio("");
	}

	@Override
	public String getNome() {
		return "aiuto";
	}


	@Override
	public void setIO(IO io) {
		this.io = io;
	}
		

}
