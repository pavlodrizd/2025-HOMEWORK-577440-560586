package it.uniroma3.diadia;

public class IOSimulator implements IO {

	private String[] righeDaLeggere;
	private String[] messaggiScritti;
	private int indiceRighe;
	private int indiceMessaggi;

	public IOSimulator(String[] righeDaLeggere) {
		this.righeDaLeggere = righeDaLeggere;
		this.messaggiScritti = new String[100];
		this.indiceRighe = 0;
		this.indiceMessaggi = 0;
	}

	@Override
	public void mostraMessaggio(String messaggio) {
		if (indiceMessaggi < messaggiScritti.length) {
			messaggiScritti[indiceMessaggi] = messaggio;
			indiceMessaggi++;
		}
	}

	@Override
	public String leggiRiga() {
		if (indiceRighe < righeDaLeggere.length) {
			String riga = righeDaLeggere[indiceRighe];
			indiceRighe++;
			return riga;
		} else {
			return "fine";
		}
	}

	public String[] getMessaggi() {
		return this.messaggiScritti;
	}
}
