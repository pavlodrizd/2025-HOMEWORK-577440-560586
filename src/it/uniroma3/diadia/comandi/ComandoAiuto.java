package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

/**
 * Stampa informazioni di aiuto.
 */

public class ComandoAiuto implements Comando{
	
	static final private String[] elencoComandi = { "vai", "aiuto", "fine", "prendi", "posa", "guarda" };
	private IO io;
	
	@Override
	public void setParametro(String parametro) {
	}
	
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
	public String getParametro() {
		return null;
	}

	@Override
	public void setIO(IO io) {
		this.io = io;
	}
		

}
