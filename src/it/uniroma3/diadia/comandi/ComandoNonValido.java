package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando implements Comando {
	
	private IO io;

	@Override
	public void esegui(Partita partita) {
		this.io.mostraMessaggio("Comando inesistente!");

	}

	@Override
	public String getNome() {
		return "non valido";
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
