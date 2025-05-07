package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza{
	private String direzioneBloccata;
	private String attrezzoSbloccante;
	

	public StanzaBloccata(String nome, String direzioneBloccata, String attrezzoSbloccante) {
		super(nome);
		this.direzioneBloccata = direzioneBloccata;
		this.attrezzoSbloccante = attrezzoSbloccante;
	}
	
	@Override
	public Stanza getStanzaAdiacente(String dir) {
		if (dir.equals(direzioneBloccata) && !this.hasAttrezzo(attrezzoSbloccante)){
			return this;
		}
		
		else return super.getStanzaAdiacente(dir);
	}
	
	@Override
	public String getDescrizione() {
		if(this.getStanzaAdiacente(direzioneBloccata)==this) {
			return super.getDescrizione() + "\n!!! L'uscita "
					+this.direzioneBloccata+" è bloccata, per sbloccare"
							+ " nella stanza deve essere presente l'attrezzo: " + attrezzoSbloccante+" !!!";
		}
		else return super.getDescrizione();
	}

}
