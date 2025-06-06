package it.uniroma3.diadia.ambienti;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaProtected extends StanzaProtected{
	
	private int contatoreAttrezziPosati;
	private int sogliaMagica;
	private static final int SOGLIA_MAGICA_DEFAULT = 3;
	
	
	public StanzaMagicaProtected(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);
	}
	
	public StanzaMagicaProtected(String nome, int soglia) {
		super(nome);
		this.contatoreAttrezziPosati = 0;
		this.sogliaMagica = soglia;
	}
	
	protected Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
		char[] nomeDaModificare = attrezzo.getNome().toCharArray();
		
		for(int i = 0; i < nomeDaModificare.length/2; i++) {
			char temp = nomeDaModificare[i];
			nomeDaModificare[i] = nomeDaModificare[nomeDaModificare.length-1-i];
			nomeDaModificare[nomeDaModificare.length-1-i] = temp;
		}
		
		return new Attrezzo(new String(nomeDaModificare), attrezzo.getPeso()*2);
		
	}
	
	@Override
	public boolean addAttrezzo(Attrezzo attrezzo) {
		contatoreAttrezziPosati ++;
		if(this.contatoreAttrezziPosati>sogliaMagica) {
			attrezzo = modificaAttrezzo(attrezzo);
		}
		
		return super.addAttrezzo(attrezzo);
	}
	
}
