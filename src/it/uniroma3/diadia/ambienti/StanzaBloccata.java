package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {
	private String direzioneBloccata;
	private String attrezzoSbloccante;

	public StanzaBloccata(String nome, String direzioneBloccata, String attrezzoSbloccante) {
		super(nome);
		this.direzioneBloccata = direzioneBloccata;
		this.attrezzoSbloccante = attrezzoSbloccante;
	}

	@Override
	public Stanza getStanzaAdiacente(Direzione dir) {
	    Direzione dirBloccataEnum = Direzione.valueOf(this.direzioneBloccata.toUpperCase());

	    if (dir.equals(dirBloccataEnum) && !this.hasAttrezzo(attrezzoSbloccante)) {
	        return null; 
	    } else {
	        return super.getStanzaAdiacente(dir);
	    }
	}

	@Override
	public String getDescrizione() {

	    boolean eBloccata = !this.hasAttrezzo(attrezzoSbloccante);

	    StringBuilder sb = new StringBuilder(super.getDescrizione());

	    if (eBloccata) { 
	        sb.append("\n!!! ATTENZIONE: L'uscita in direzione ")
	          .append(this.direzioneBloccata.toUpperCase())
	          .append(" è bloccata. Per sbloccarla, devi posare l'attrezzo '")
	          .append(this.attrezzoSbloccante)
	          .append("' in questa stanza. !!!");
	    }


	    return sb.toString();
	}

    public Direzione getDirezioneBloccata() {
	    return Direzione.valueOf(this.direzioneBloccata.toUpperCase());
	}
    
    public String getAttrezzoSbloccante() {
        return this.attrezzoSbloccante;
    }

}