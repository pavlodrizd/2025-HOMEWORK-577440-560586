package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoSaluta extends AbstractComando implements Comando {
    
    private IO io;
    private static final String MESSAGGIO_NESSUN_PERSONAGGIO = "Non c'è alcun personaggio in questa stanza!";

    @Override
    public void esegui(Partita partita) {
        if (partita.getLabirinto().getStanzaCorrente().getPersonaggio() != null) {
            io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getPersonaggio().saluta());
        } else {
            io.mostraMessaggio(MESSAGGIO_NESSUN_PERSONAGGIO);
        }
    }

    @Override
    public String getNome() {
        return "saluta";
    }

	@Override
	public String getParametro() {
		return null;
	}

	@Override
	public void setIO(IO io) {
		this.io=io;
		
	}
}