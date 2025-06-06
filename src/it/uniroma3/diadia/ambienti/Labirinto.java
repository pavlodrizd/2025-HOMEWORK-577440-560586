package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.io.IOException; // Importa IOException
import java.util.HashMap;
import java.util.Map;

import it.uniroma3.diadia.CaricatoreLabirinto;
import it.uniroma3.diadia.FormatoFileNonValidoException;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio; // Se usi personaggi nel LabirintoBuilder
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class Labirinto {

	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;

	private Labirinto() {
	}

	private Labirinto(CaricatoreLabirinto caricatore) throws FormatoFileNonValidoException, IOException {
		caricatore.carica(); 
		this.stanzaCorrente = caricatore.getStanzaIniziale();
		this.stanzaVincente = caricatore.getStanzaVincente();
	}

	public Stanza getStanzaCorrente() {
		return stanzaCorrente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}

	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}

	/**
	 * Metodo statico per iniziare la costruzione di un labirinto
	 * programmaticamente.
	 * 
	 * @return Una nuova istanza di LabirintoBuilder.
	 */
	public static LabirintoBuilder newBuilder() {
		return new LabirintoBuilder();
	}

	/**
	 * Metodo statico per caricare un labirinto da un file. Questo metodo ritorna
	 * direttamente il Labirinto, non il builder, perché la costruzione da file è
	 * solitamente un'operazione completa.
	 * 
	 * @param nomeFile Il percorso del file da cui caricare il labirinto.
	 * @return Un'istanza di Labirinto costruita dal file.
	 * @throws FileNotFoundException         se il file non esiste.
	 * @throws FormatoFileNonValidoException se il formato del file non è corretto.
	 * @throws IOException                   se si verifica un errore di I/O durante
	 *                                       la lettura del file.
	 */
	public static Labirinto caricaDaFile(String nomeFile)
			throws FileNotFoundException, FormatoFileNonValidoException, IOException {
		CaricatoreLabirinto caricatore = new CaricatoreLabirinto(nomeFile);
		// Questo costruttore di Labirinto ora delega il caricamento e si popola
		return new Labirinto(caricatore);
	}

	/**
	 * Classe interna Builder per creare e configurare un Labirinto.
	 */
	public static class LabirintoBuilder {

		private Labirinto labirinto;
		private Map<String, Stanza> nome2stanza; 
		private Stanza ultimaStanzaAggiunta;

		public LabirintoBuilder() {
			this.labirinto = new Labirinto();
			this.nome2stanza = new HashMap<>();
		}

		/**
		 * Metodo interno per aggiungere una stanza alla mappa e aggiornare l'ultima
		 * stanza aggiunta. Utile per centralizzare la logica di aggiunta e
		 * aggiornamento.
		 * 
		 * @param stanza La stanza da aggiungere e impostare come ultima aggiunta.
		 */
		private void aggiungiStanzaEAggiornaUltima(Stanza stanza) {
			this.nome2stanza.put(stanza.getNome(), stanza);
			this.ultimaStanzaAggiunta = stanza;
		}

		/**
		 * Aggiunge una nuova stanza generica al labirinto. Se la stanza esiste già, non
		 * fa nulla. L'ultima stanza aggiunta viene impostata su questa stanza.
		 * 
		 * @param nomeStanza Il nome della stanza da aggiungere.
		 * @return Il LabirintoBuilder stesso per il chaining delle chiamate.
		 */
		public LabirintoBuilder addStanza(String nomeStanza) {
			if (!this.nome2stanza.containsKey(nomeStanza)) {
				Stanza s = new Stanza(nomeStanza);
				aggiungiStanzaEAggiornaUltima(s);
			}
			return this;
		}

		/**
		 * Aggiunge una stanza iniziale al labirinto. Se la stanza non esiste, la crea.
		 * Imposta la stanza corrente del labirinto.
		 * 
		 * @param nomeStanzaIniziale Il nome della stanza iniziale.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaIniziale(String nomeStanzaIniziale) {
			Stanza s = this.nome2stanza.get(nomeStanzaIniziale);
			if (s == null) { 
				s = new Stanza(nomeStanzaIniziale);
				aggiungiStanzaEAggiornaUltima(s);
			}
			this.labirinto.setStanzaCorrente(s);
			return this;
		}

		/**
		 * Aggiunge una stanza vincente al labirinto. Se la stanza non esiste, la crea.
		 * Imposta la stanza vincente del labirinto.
		 * 
		 * @param nomeStanzaVincente Il nome della stanza vincente.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaVincente(String nomeStanzaVincente) {
			Stanza s = this.nome2stanza.get(nomeStanzaVincente);
			if (s == null) { 
				s = new Stanza(nomeStanzaVincente);
				aggiungiStanzaEAggiornaUltima(s);
			}
			this.labirinto.setStanzaVincente(s);
			return this;
		}

		/**
		 * Aggiunge una stanza bloccata al labirinto. Se la stanza esiste già, non fa
		 * nulla.
		 * 
		 * @param nome                   Il nome della stanza bloccata.
		 * @param nomeDirezioneBloccata  La direzione bloccata.
		 * @param nomeAttrezzoNecessario L'attrezzo necessario per sbloccare.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaBloccata(String nome, String nomeDirezioneBloccata,
				String nomeAttrezzoNecessario) {
			if (!this.nome2stanza.containsKey(nome)) {
				Stanza s = new StanzaBloccata(nome, nomeDirezioneBloccata, nomeAttrezzoNecessario);
				aggiungiStanzaEAggiornaUltima(s);
			}
			return this;
		}

		/**
		 * Aggiunge una stanza magica al labirinto. Se la stanza esiste già, non fa
		 * nulla.
		 * 
		 * @param nome   Il nome della stanza magica.
		 * @param soglia La soglia della stanza magica.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaMagica(String nome, int soglia) {
			if (!this.nome2stanza.containsKey(nome)) {
				Stanza s = new StanzaMagica(nome, soglia);
				aggiungiStanzaEAggiornaUltima(s);
			}
			return this;
		}

		/**
		 * Aggiunge una stanza magica al labirinto con una soglia predefinita (es. 0).
		 * Utile se la soglia non è sempre necessaria o è implicita.
		 * 
		 * @param nome Il nome della stanza magica.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaMagica(String nome) {
			return this.addStanzaMagica(nome, 0);
		}

		/**
		 * Aggiunge una stanza buia al labirinto. Se la stanza esiste già, non fa nulla.
		 * 
		 * @param nome                   Il nome della stanza buia.
		 * @param nomeAttrezzoNecessario L'attrezzo necessario per illuminare la stanza.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addStanzaBuia(String nome, String nomeAttrezzoNecessario) {
			if (!this.nome2stanza.containsKey(nome)) {
				Stanza s = new StanzaBuia(nome, nomeAttrezzoNecessario);
				aggiungiStanzaEAggiornaUltima(s);
			}
			return this;
		}

		public LabirintoBuilder addMago(String nomeStanza, String nomeMago, String presentazione, String nomeAttrezzo,
				int pesoAttrezzo) {
			Stanza stanza = this.nome2stanza.get(nomeStanza);
			if (stanza != null) {
				Attrezzo attrezzoMago = new Attrezzo(nomeAttrezzo, pesoAttrezzo);
				AbstractPersonaggio mago = new Mago(nomeMago, presentazione, attrezzoMago);
				stanza.setPersonaggio(mago);
			}
			return this;
		}

		public LabirintoBuilder addStrega(String nomeStanza, String nomeStrega, String presentazione) {
			Stanza stanza = this.nome2stanza.get(nomeStanza);
			if (stanza != null) {
				AbstractPersonaggio strega = new Strega(nomeStrega, presentazione);
				stanza.setPersonaggio(strega);
			}
			return this;
		}

		public LabirintoBuilder addCane(String nomeStanza, String nomeCane, String presentazione) {
			Stanza stanza = this.nome2stanza.get(nomeStanza);
			if (stanza != null) {
				AbstractPersonaggio cane = new Cane(nomeCane, presentazione);
				stanza.setPersonaggio(cane);
			}
			return this;
		}

		/**
		 * Aggiunge un attrezzo all'ultima stanza aggiunta.
		 * 
		 * @param nomeAttrezzo Il nome dell'attrezzo.
		 * @param peso         Il peso dell'attrezzo.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addAttrezzo(String nomeAttrezzo, int peso) {
			if (this.ultimaStanzaAggiunta != null) {
				Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
				this.ultimaStanzaAggiunta.addAttrezzo(attrezzo);
			}
			return this;
		}

		/**
		 * Aggiunge un attrezzo ad una specifica stanza.
		 * 
		 * @param nomeAttrezzo Il nome dell'attrezzo.
		 * @param peso         Il peso dell'attrezzo.
		 * @param nomeStanza   Il nome della stanza a cui aggiungere l'attrezzo.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addAttrezzo(String nomeAttrezzo, int peso, String nomeStanza) {
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			Stanza stanza = this.nome2stanza.get(nomeStanza);
			if (stanza != null) {
				stanza.addAttrezzo(attrezzo);
			}
			return this;
		}

		/**
		 * Imposta un'adiacenza tra due stanze in una data direzione.
		 * 
		 * @param stanzaDa  Il nome della stanza di partenza.
		 * @param stanzaA   Il nome della stanza di arrivo.
		 * @param direzione La direzione per andare da stanzaDa a stanzaA.
		 * @return Il LabirintoBuilder stesso.
		 */
		public LabirintoBuilder addAdiacenza(String stanzaDa, String stanzaA, Direzione direzione) {
			Stanza da = this.nome2stanza.get(stanzaDa);
			Stanza a = this.nome2stanza.get(stanzaA);
			if (da != null && a != null) {
				da.impostaStanzaAdiacente(direzione, a);
			}
			return this;
		}

		/**
		 * Restituisce il labirinto costruito.
		 * 
		 * @return L'oggetto Labirinto.
		 */
		public Labirinto getLabirinto() {
			return this.labirinto;
		}

		/**
		 * Restituisce la mappa delle stanze create dal builder. Questo metodo è utile
		 * per scopi di debug o test, ma solitamente il labirinto dovrebbe essere
		 * manipolato solo tramite le sue API.
		 * 
		 * @return La mappa delle stanze.
		 */
		public Map<String, Stanza> getMappaStanze() {
			return this.nome2stanza;
		}
	}
}