package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoRegala extends AbstractComando implements Comando{
	private IO io;
	String nomeAttrezzo;

	@Override
	public void esegui(Partita partita) {
	    if (this.nomeAttrezzo == null || this.nomeAttrezzo.isEmpty()) {
	        io.mostraMessaggio("Cosa vuoi regalare? Specifica un attrezzo.");
	        return;
	    }
	    
	    Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(this.nomeAttrezzo);
	    if (attrezzo == null) {
	        io.mostraMessaggio("Non hai '" + this.nomeAttrezzo + "' nella borsa!");
	        return;
	    }
	    
	    if (partita.getLabirinto().getStanzaCorrente().getPersonaggio() == null) {
	        io.mostraMessaggio("Non c'è nessuno a cui regalare qualcosa qui!");
	        return;
	    }
	   
	    
	    String risposta = partita.getLabirinto().getStanzaCorrente().getPersonaggio()
	                       .riceviRegalo(attrezzo, partita);
	    io.mostraMessaggio(risposta);
	    partita.getGiocatore().getBorsa().removeAttrezzo(this.nomeAttrezzo);
	}


	@Override
	public String getNome() {
		return "regala";
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}
	
	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}

	@Override
	public void setIO(IO io) {
		this.io = io;
	}

}
