package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {

	private IO io;

	@Override
	public void setParametro(String parametro) {
	}
	
	@Override
	public void esegui(Partita partita) {
		this.io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		this.io.mostraMessaggio("CFU rimanenti: " + partita.getGiocatore().getCfu());
	}

	@Override
	public String getNome() {
		return "guarda";
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
