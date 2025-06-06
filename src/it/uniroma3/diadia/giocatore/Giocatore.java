package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Configuratore;

public class Giocatore {
	static final private int CFU_INIZIALI = Configuratore.getCFU();

	
	private Borsa borsa;
	private int cfu;
	
	public Giocatore() {
		this.cfu = CFU_INIZIALI;
		this.borsa = new Borsa();
	}
	
	public Borsa getBorsa() {
		return this.borsa;
	}
	
	public int getCfu() {
		return this.cfu;
	}
	
	public void setCfu(int cfu) {
		this.cfu = cfu;
	}
}
