package it.uniroma3.diadia.ambienti;


public class StanzaBuia extends Stanza{
	
	private String attrezzoSpeciale;
	
	public StanzaBuia(String nome, String attrezzoSpeciale) {
		super(nome);
		this.attrezzoSpeciale = attrezzoSpeciale;
	}
	
	@Override
	public String getDescrizione() {
		if(this.hasAttrezzo(attrezzoSpeciale)) {
			return super.getDescrizione();
		}
		else
			return "qui c'è un buio pesto";
	}
	
	
	
	

}
