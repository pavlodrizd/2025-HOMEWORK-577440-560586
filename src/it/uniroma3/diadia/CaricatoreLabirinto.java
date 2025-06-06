package it.uniroma3.diadia;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Direzione;

// Rimosso import it.uniroma3.diadia.ambienti.Direzione;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class CaricatoreLabirinto {

	private static final String STANZE_MARKER = "Stanze:";             
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";    
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";  	
	private static final String STANZE_BUIE_MARKER = "Buia:";  
	private static final String STANZE_BLOCCATE_MARKER = "Bloccata:";  
	private static final String STANZE_MAGICHE_MARKER = "Magica:";  
	private static final String PERSONAGGI_MARKER_MAGO = "Mago:";
	private static final String PERSONAGGI_MARKER_STREGA = "Strega:";
	private static final String PERSONAGGI_MARKER_CANE = "Cane:";
	private static final String ATTREZZI_MARKER = "Attrezzi:";
	private static final String USCITE_MARKER = "Uscite:";

	private BufferedReader reader;

	private Map<String, Stanza> nome2stanza;

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;

	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
		this.nome2stanza = new HashMap<>();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}
	
	public CaricatoreLabirinto(StringReader reader) throws FileNotFoundException {
		this.nome2stanza = new HashMap<>();
		this.reader = new LineNumberReader(reader);
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiECreaStanzeMagiche();
			this.leggiECreaStanzeBuie();
			this.leggiECreaStanzeBloccate();
			this.leggiInizialeEvincente();
			this.leggiECreaMaghi();
			this.leggiECreaCani();
			this.leggiECreaStreghe();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			String riga = this.reader.readLine();
			check(riga != null && riga.startsWith(marker),"era attesa una riga che cominciasse per "+marker);
			return riga.substring(marker.length());
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			Stanza stanza = new Stanza(nomeStanza.trim()); // Aggiunto trim() per pulizia
			this.nome2stanza.put(nomeStanza.trim(), stanza);
		}
	}
	
	private void leggiECreaStanzeMagiche() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MAGICHE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			// Assumendo che StanzaMagica abbia un costruttore con solo il nome
			// Se ha anche la soglia, il formato del file o il costruttore devono adattarsi
			Stanza stanza = new StanzaMagica(nomeStanza.trim()); 
			this.nome2stanza.put(nomeStanza.trim(), stanza);
		}
	}
	
	private void leggiECreaStanzeBuie() throws FormatoFileNonValidoException {
		String specificheStanze = this.leggiRigaCheCominciaPer(STANZE_BUIE_MARKER);
		for(String specifica : separaStringheAlleVirgole(specificheStanze)) {
			try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {	
				String nomeStanza = null;
				String attrezzoPerVedere = null;

				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza buia in "+ specifica+"\n"));
				nomeStanza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("l'attrezzo per vedere la stanza "+nomeStanza+"\n"));
				attrezzoPerVedere = scannerDiLinea.next();

				Stanza stanza = new StanzaBuia(nomeStanza, attrezzoPerVedere);
				this.nome2stanza.put(nomeStanza, stanza);
			}
		} 
	}
	
	private void leggiECreaStanzeBloccate() throws FormatoFileNonValidoException {
		String specificheStanze = this.leggiRigaCheCominciaPer(STANZE_BLOCCATE_MARKER);
		for(String specifica : separaStringheAlleVirgole(specificheStanze)) {
			try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {	
				String nomeStanza = null;
				String direzioneBloccata = null;
				String attrezzoSbloccante = null;

				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza bloccata in "+ specifica+"\n"));
				nomeStanza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la direzione bloccata della stanza "+nomeStanza+"\n"));
				direzioneBloccata = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("l'attrezzo per sbloccare la stanza "+nomeStanza+"\n"));
				attrezzoSbloccante = scannerDiLinea.next();

				Stanza stanza = new StanzaBloccata(nomeStanza, direzioneBloccata, attrezzoSbloccante);
				this.nome2stanza.put(nomeStanza, stanza);
			}
		} 
	}
	
	private void leggiECreaMaghi() throws FormatoFileNonValidoException {
		String specifichePersonaggi = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_MAGO);
		for(String specifica : separaStringheAlleVirgole(specifichePersonaggi)) {
			try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {	
				String nomeStanza = null;
				String nomeMago = null;
				String presentazione = null;
				String nomeAttrezzo = null;
				// int pesoAttrezzo = 0; // Se vuoi specificare il peso dell'attrezzo per il mago nel file

				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la stanza per aggiungere il mago in "+ specifica+"\n"));
				nomeStanza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il nome del mago in "+ specifica+"\n"));
				nomeMago = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la presentazione del mago "+nomeMago+"\n"));
				presentazione = scannerDiLinea.next();					
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("l'attrezzo che il mago possiede in "+nomeMago+"\n"));
				nomeAttrezzo = scannerDiLinea.next();
				// Se vuoi il peso dell'attrezzo del mago dal file
				// check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo per il mago "+nomeMago+"\n"));
				// pesoAttrezzo = Integer.parseInt(scannerDiLinea.next());

				check(isStanzaValida(nomeStanza), "Stanza '"+nomeStanza+"' per il mago '"+nomeMago+"' non definita");

				// Assumendo che il mago dia un attrezzo con peso fisso o non specificato nel file per semplicità
				Attrezzo attrezzoMago = new Attrezzo(nomeAttrezzo, 4); // Peso fisso 4 come nel tuo originale
				AbstractPersonaggio personaggio = new Mago(nomeMago, presentazione, attrezzoMago);
				this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
			}
		} 
	}
	
	private void leggiECreaStreghe() throws FormatoFileNonValidoException {
		String specifichePersonaggi = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_STREGA);
		for(String specifica : separaStringheAlleVirgole(specifichePersonaggi)) {
			try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {	
				String nomeStanza = null;
				String nomeStrega = null;
				String presentazione = null;

				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la stanza per aggiungere la strega in "+ specifica+"\n"));
				nomeStanza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il nome della strega in "+ specifica+"\n"));
				nomeStrega = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la presentazione della strega "+nomeStrega+"\n"));
				presentazione = scannerDiLinea.next();					

				check(isStanzaValida(nomeStanza), "Stanza '"+nomeStanza+"' per la strega '"+nomeStrega+"' non definita");
				AbstractPersonaggio personaggio = new Strega(nomeStrega, presentazione);
				this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
			}
		} 
	}
	
	private void leggiECreaCani() throws FormatoFileNonValidoException {
		String specifichePersonaggi = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_CANE);
		for(String specifica : separaStringheAlleVirgole(specifichePersonaggi)) {
			try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {	
				String nomeStanza = null;
				String nomeCane = null;
				String presentazione = null;

				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la stanza per aggiungere il cane in "+ specifica+"\n"));
				nomeStanza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("il nome del cane in "+ specifica+"\n"));
				nomeCane = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la presentazione del cane "+nomeCane+"\n"));
				presentazione = scannerDiLinea.next();					

				check(isStanzaValida(nomeStanza), "Stanza '"+nomeStanza+"' per il cane '"+nomeCane+"' non definita");
				AbstractPersonaggio personaggio = new Cane(nomeCane, presentazione);
				this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
			}
		} 
	}

	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		try (Scanner scannerDiParole = scanner) {
			while(scannerDiParole.hasNext()) {
				result.add(scannerDiParole.next());
			}
		}
		return result;
	}

	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER).trim();
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER).trim();
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.stanzaIniziale = this.nome2stanza.get(nomeStanzaIniziale);
		this.stanzaVincente = this.nome2stanza.get(nomeStanzaVincente);
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);

		for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			String nomeStanza = null; 
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo.trim())) {
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo in "+ specificaAttrezzo +"."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+" in "+ specificaAttrezzo +"."));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+" in "+ specificaAttrezzo +"."));
				nomeStanza = scannerLinea.next();
			}				
			posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			this.nome2stanza.get(nomeStanza).addAttrezzo(attrezzo);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}

	private boolean isStanzaValida(String nomeStanza) {
		return this.nome2stanza.containsKey(nomeStanza);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
	    String righeUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
	    for (String specifica : separaStringheAlleVirgole(righeUscite)) {
	        try (Scanner scannerDiLinea = new Scanner(specifica.trim())) {
	            String nomeStanzaPartenza = null;
	            String nomeStanzaDestinazione = null;
	            String direzioneStr = null;
	            
	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza di partenza in " + specifica + "\n"));
	            nomeStanzaPartenza = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la direzione in " + specifica + "\n"));
	            direzioneStr = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza di destinazione in " + specifica + "\n"));
	            nomeStanzaDestinazione = scannerDiLinea.next();

	            check(isStanzaValida(nomeStanzaPartenza), "Stanza di partenza '" + nomeStanzaPartenza + "' non definita");
	            check(isStanzaValida(nomeStanzaDestinazione), "Stanza di destinazione '" + nomeStanzaDestinazione + "' non definita");

	            Stanza stanzaPartenza = this.nome2stanza.get(nomeStanzaPartenza);
	            Stanza stanzaDestinazione = this.nome2stanza.get(nomeStanzaDestinazione);

	            Direzione direzione = Direzione.fromString(direzioneStr);

	            stanzaPartenza.impostaStanzaAdiacente(direzione, stanzaDestinazione);
	        }
	    }
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	private void impostaUscita(String stanzaDa, Direzione dir, String nomeA) throws FormatoFileNonValidoException {
	    check(isStanzaValida(stanzaDa), "Stanza di partenza sconosciuta " + stanzaDa);
	    check(isStanzaValida(nomeA), "Stanza di destinazione sconosciuta " + nomeA);

	    Stanza partenzaDa = this.nome2stanza.get(stanzaDa);
	    Stanza arrivoA = this.nome2stanza.get(nomeA);

	    partenzaDa.impostaStanzaAdiacente(dir, arrivoA);
	}


	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + ((LineNumberReader) this.reader).getLineNumber() + "] "+messaggioErrore);		
	}

	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}
}