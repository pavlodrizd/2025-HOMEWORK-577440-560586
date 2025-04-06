package it.uniroma3.diadia;
 
 import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
 
 /**
  * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
  * Per giocare crea un'istanza di questa classe e invoca il letodo gioca
  *
  * Questa e' la classe principale crea e istanzia tutte le altre
  *
  * @author  docente di POO 
  *         (da un'idea di Michael Kolling and David J. Barnes) 
  *          
  * @version base
  */
 
 public class DiaDia {
 
 	static final private String MESSAGGIO_BENVENUTO = ""+
 			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
 			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
 			"I locali sono popolati da strani personaggi, " +
 			"alcuni amici, altri... chissa!\n"+
 			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
 			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
 			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
 			"Per conoscere le istruzioni usa il comando 'aiuto'.";
 
 	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};
 
 	private Partita partita;
 	private IOConsole console;
 
 	public DiaDia(IOConsole console) {
 		this.partita = new Partita();
 		this.console = console;
 	}
 
 	public void gioca() {
 		String istruzione; 
 		console.mostraMessaggio(MESSAGGIO_BENVENUTO);		
 		do		
 			istruzione = console.leggiRiga();
 		while (!processaIstruzione(istruzione));
 	}   
 
 
 	/**
 	 * Processa una istruzione 
 	 *
 	 * @return true se l'istruzione e' eseguita e il gioco continua, false altrimenti
 	 */
 	private boolean processaIstruzione(String istruzione) {
 		Comando comandoDaEseguire = new Comando(istruzione);
 		if(comandoDaEseguire.getNome()==null)
 			console.mostraMessaggio("Comando sconosciuto");
 		else {
 			if (comandoDaEseguire.getNome().equals("fine")) {
 				this.fine(); 
 				return true;
 			} else if (comandoDaEseguire.getNome().equals("vai"))
 				this.vai(comandoDaEseguire.getParametro());
 			else if (comandoDaEseguire.getNome().equals("aiuto"))
 				this.aiuto();
 			else if(comandoDaEseguire.getNome().equals("prendi"))
 				this.prendi(comandoDaEseguire.getParametro());
 			else if(comandoDaEseguire.getNome().equals("posa"))
 				this.posa(comandoDaEseguire.getParametro());
 			else	
 				console.mostraMessaggio("Comando sconosciuto");
 		}
 		if (this.partita.vinta()) {
 			console.mostraMessaggio("Hai vinto!");
 			return true;
 		} else
 			return false;
 	}   
 
 	// implementazioni dei comandi dell'utente:
 
 	/**
 	 * Stampa informazioni di aiuto.
 	 */
 	private void aiuto() {
 		for(int i=0; i< elencoComandi.length; i++) 
 			console.mostraMessaggio(elencoComandi[i]+" ");
 	}
 
 	/**
 	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
 	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
 	 */
 
 	private void vai(String direzione) {
 		if(direzione==null)
 			console.mostraMessaggio("Dove vuoi andare? (nord, sud, ovest, est)");
 
 		Stanza prossimaStanza = null;
 		prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
 		if (prossimaStanza == null)
 			console.mostraMessaggio("Direzione inesistente");
 		else {
 			this.partita.getLabirinto().setStanzaCorrente(prossimaStanza);
 			int cfu = this.partita.getGiocatore().getCfu();
 			this.partita.getGiocatore().setCfu(cfu--);
 		}
 		console.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
 	}
 
 	/**
 	 * Comando "Fine".
 	 */
 
 	private void fine() {
 		console.mostraMessaggio("Grazie di aver giocato!");  // si desidera smettere
 	}
 
 	//Comando "prendi"
 
 	private void prendi(String nomeAttrezzo) {
 
 		if(nomeAttrezzo==null)
 			console.mostraMessaggio("Quale attrezzo vuoi prendere?");
 		else {
 
 			if(!this.partita.getLabirinto().getStanzaCorrente().hasAttrezzo(nomeAttrezzo)) {
 				console.mostraMessaggio("In questa stanza non c'è questo attrezzo!");
 			}
 			else {
 				Attrezzo att = this.partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);
 				this.partita.getLabirinto().getStanzaCorrente().removeAttrezzo(att);
 				this.partita.getGiocatore().getBorsa().addAttrezzo(att);
 				console.mostraMessaggio("L'attrezzo è stato posto nella borsa!");
 			}
 		}
 
 	}
 
 
 	//Comando "posa"
 
 	private void posa(String nomeAttrezzo) {
 		if(nomeAttrezzo==null)
 			console.mostraMessaggio("Quale attrezzo vuoi posare?");
 		else {
 
 			if(!this.partita.getGiocatore().getBorsa().hasAttrezzo(nomeAttrezzo)) {
 				console.mostraMessaggio("Nella borsa non c'è questo attrezzo!");
 			}
 			else {
 				Attrezzo att = this.partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
 				this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
 				this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(att);
 				console.mostraMessaggio("L'oggetto è stato rimesso nella stanza e rimosso dalla borsa!");
 			}
 		}
 	}
 
 	public static void main(String[] argc) {
 		IOConsole console = new IOConsole();
 		DiaDia gioco = new DiaDia(console);
 		gioco.gioca();
 	}
 }