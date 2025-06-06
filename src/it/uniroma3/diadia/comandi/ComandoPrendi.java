package it.uniroma3.diadia.comandi;
import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	private String nomeAttrezzo;
	private IO io;
	
	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}

	@Override
	public void esegui(Partita partita) {
		
		if (nomeAttrezzo == null)
			this.io.mostraMessaggio("Inserisci il nome dell'attrezzo da prendere.");
		
		if(nomeAttrezzo!=null && !partita.getLabirinto().getStanzaCorrente().hasAttrezzo(nomeAttrezzo)) {
			this.io.mostraMessaggio("Nessun attrezzo con questo nome nella stanza.");
		}
		
		if (partita.getLabirinto().getStanzaCorrente().hasAttrezzo(nomeAttrezzo)) {
			Attrezzo attrezzo = partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);

			if (partita.getGiocatore().getBorsa().addAttrezzo(attrezzo)) {
				partita.getLabirinto().getStanzaCorrente().removeAttrezzo(attrezzo);
				this.io.mostraMessaggio(nomeAttrezzo + " posto in borsa.");
			}
			else {
				io.mostraMessaggio("Peso max borsa raggiunto");
			}

			
			

		}	
	}

	@Override
	public String getNome() {
		return "prendi";
	}

	@Override
	public String getParametro() {
		return nomeAttrezzo;
	}

	@Override
	public void setIO(IO io) {
		this.io=io;
		
	}


}
