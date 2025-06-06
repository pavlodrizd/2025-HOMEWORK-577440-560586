package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.List;

public class IOSimulator implements IO {

	private List<String> righeDaLeggere;
	private List<String> messaggiStampati;

	private int indiceLettura;
	private int indiceStampa;

	public IOSimulator(List<String> istruzioni) {
		this.righeDaLeggere = istruzioni;
		this.messaggiStampati = new ArrayList<String>(istruzioni.size() + 50);
		this.indiceLettura = 0;
		this.indiceStampa = 0;
	}

	@Override
	public void mostraMessaggio(String messaggio) {
		if(this.messaggiStampati != null) {
			this.messaggiStampati.add(indiceStampa, messaggio);
			this.indiceStampa++;
		}
	}

	@Override
	public String leggiRiga() {
		if (indiceLettura < this.righeDaLeggere.size()) {
			String riga = this.righeDaLeggere.get(indiceLettura);
			indiceLettura++;
			return riga;
		} else {
			return "fine";
		}
	}

	public List<String> getMessaggiStampati() {
		return this.messaggiStampati;
	}
}
