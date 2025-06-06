package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoInteragisci extends AbstractComando implements Comando{
	
	private static final String MESSAGGIO_CON_CHI = "Con chi dovrei interagire?...";
	
	private String messaggio;
	private IO io;

	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		
		if (personaggio != null) {
			this.messaggio = personaggio.agisci(partita);
			io.mostraMessaggio(messaggio);
		}
		else {
			io.mostraMessaggio(MESSAGGIO_CON_CHI);
		}
		
	}
	
	

	@Override
	public String getNome() {
		return "interagisci";
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
