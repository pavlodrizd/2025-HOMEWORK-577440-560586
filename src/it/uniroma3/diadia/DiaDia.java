package it.uniroma3.diadia;

import java.io.FileNotFoundException;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

public class DiaDia {

    static final private String MESSAGGIO_BENVENUTO = ""
            + "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n"
            + "Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"
            + "I locali sono popolati da strani personaggi, " + "alcuni amici, altri... chissa!\n"
            + "Ci sono attrezzi che potrebbero servirti nell'impresa:\n"
            + "puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n"
            + "o regalarli se pensi che possano ingraziarti qualcuno.\n\n"
            + "Per conoscere le istruzioni usa il comando 'aiuto'.";

    private Partita partita;
    private IO io;

    public DiaDia(IO io, Labirinto labirinto) {
        this.io = io;
        this.partita = new Partita(labirinto);
    }

    public DiaDia(Partita partita, IO io) {
        this.partita = partita;
        this.io = io;
    }

    public DiaDia(Labirinto labirinto, IO io) {
        this.partita = new Partita(labirinto);
        this.io = io;
    }

    public void gioca() {
        String istruzione;

        this.io.mostraMessaggio(MESSAGGIO_BENVENUTO);

        do {
            istruzione = this.io.leggiRiga();
        } while (!processaIstruzione(istruzione));
    }

    private boolean processaIstruzione(String istruzione) {
        Comando comandoDaEseguire;
        try {
            FabbricaDiComandiRiflessiva factory = new FabbricaDiComandiRiflessiva();
            comandoDaEseguire = factory.costruisciComando(istruzione);
            comandoDaEseguire.setIO(this.io);
            comandoDaEseguire.esegui(this.partita);
        } catch (Exception e) {
            this.io.mostraMessaggio("Comando non valido");
            return false;
        }

        if (this.partita.vinta()) {
            io.mostraMessaggio("Hai vinto!");
        }
        if (this.partita.getGiocatore().getCfu() <= 0) {
            io.mostraMessaggio("Hai perso, hai esaurito i CFU...");
        }

        return this.partita.isFinita();
    }

    public static void main(String[] argc) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            IO console = new IOConsole(scanner);
            Labirinto labirinto = null;

            try {
                labirinto = Labirinto.caricaDaFile("labirinto.txt");
                DiaDia gioco = new DiaDia(console, labirinto);
                gioco.gioca();
            } catch (FileNotFoundException e) {
                console.mostraMessaggio("Errore: il file del labirinto non è stato trovato!");
                e.printStackTrace();
            } catch (FormatoFileNonValidoException e) {
                console.mostraMessaggio("Errore nel formato del file del labirinto: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Partita getPartita() {
        return this.partita;
    }
}