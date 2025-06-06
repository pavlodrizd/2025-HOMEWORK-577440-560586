package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {

	private String nomeAttrezzo;
	private IO io;

	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}

	@Override
	public void esegui(Partita partita) {
		if (nomeAttrezzo == null)
			this.io.mostraMessaggio("Inserisci il nome dell'attrezzo da posare.");
		
		if(nomeAttrezzo!=null && !partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
			this.io.mostraMessaggio("Nessun attrezzo con questo nome in borsa.");
		}
		
		if (partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
			Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
			partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo);

			this.io.mostraMessaggio(nomeAttrezzo + " riposto nella stanza.");
		} 
		
	}

	@Override
	public String getNome() {
		return "posa";
	}

	@Override
	public String getParametro() {
		return nomeAttrezzo;
	}

	@Override
	public void setIO(IO io) {
		this.io = io;
		
	}

}
