import java.util.ArrayList;

/**
 * La classe TSP è la classe in cui al suo interno sono definiti attributi e metodi che permettono
 * di implementare gli algoritmi di NN e RNN
 */
public class TSP {
    /**
     * Attributo che memorizza il numero di nodi inseriti dall'utente precedentemente
     */
    private int nodi;

    /**
     * struttura dati che memorizza la matrice dei costi
     */
    private double matrice[][];

    /**
     * L'array visitati è una struttura dati che permette di tener conto quali nodi sono
     * stati visitati e quelli ancora da visitare
     */
    private Boolean[] visitati;

    /**
     *  Metodo costruttore. Metodo richiamato nel momento in cui nel main viene creato l'oggetto
     *  di tipo TSP.Questo metodo permette di inzializzare gli attributi e strutture dati
     *  necessarie per la risoluzione del problema
      */
    public TSP(int nodes, double matrice[][]) {
        nodi = nodes;
        this.matrice = new double[nodi][nodi];
        this.matrice = matrice;
        this.visitati = new Boolean[nodi];
    }

    /**
     * Metodo che implementa l'algoritmo di Nearest Neighbor. Prende come parametro il nodo
     * di partenza inserito precedentemente dall'utente.
     * @param nodoPartenza
     */
    public void nearestNeighbor(int nodoPartenza) {
        // L'oggetto sequenza è un array che permette di memorizzare il cammino hamiltoiano
        // determinato
        ArrayList<Integer> sequenza = new ArrayList<Integer>();
        // La variabile costo tiene conto del valore della funzione obiettivo.
        // All'inizio viene settato il suo valore a 0
        double costo = 0;

        //Richiamo il metodo che permette di settare l'array visitati in maniera opportuna
        this.resetVisitati(nodoPartenza);
        // Aggiungo al ciclo il nodo di partenza
        sequenza.add(nodoPartenza);
        //Richiamo il metodo sequenceBuilder che determina la soluzione del problema e restituisce
        // il valore della funzione obiettivo
        costo = this.sequenceBuilder(sequenza, nodoPartenza);

        // Stmpa a video della soluzione determinata e il relativo valore della funzione obiettivo
        System.out.print("Ciclo Hamiltoiano determinato: [");
        for(int n : sequenza) {
            System.out.print((n+1) + " " );
        }
        System.out.println("]");
        // Stampa a video il valore della funzione obiettivo
        System.out.println("Il costo associato: " + costo);

    }

    /**
     * Metodo che implementa l'algoritmo di Repetitive Nearest Neighbor. Prende come parametro la
     * matrice dei costi inserita precedentemente dall'utente.
     */
    public void repetiveNearestNeighbor() {
        // Allocazione delle strutture dati per la memorizzazione dei cicli hamiltoiani (determinati
        // considerando ogni volta un nodo di partenza diverso) e della soluzione finale.
        ArrayList<Integer> sequenza= new ArrayList<Integer>();
        ArrayList<Integer> soluzione= new ArrayList<Integer>();

        // La variabile costoMinimo è utilizzata per la memorizzazione del valore ottimo della
        // funzione obiettivo
        double costoMinimo = Double.MAX_VALUE;

        // Loop che consente di applicare il NN considerando ogni volta un nodo di partenza diverso.
        // Il loop termina quando tutti i nodi sono stati considerati come nodi di partenza.
        // La variabile nodoPartenza corrisponde al nodo di partenza. All'inizio viene impostato
        // come nodo di partenza il primo e ad ogni iterazione viene incrementato
        for(int nodoPartenza = 0; nodoPartenza < nodi; nodoPartenza++) {
            //Imposto il valore della funzione obiettivo a 0. In questo caso la variabile 'costo'
            // corrisponde al valore della funzione obiettivo considerando 'nodoPartenza' come
            // nodo inziale
            double costo;

            //Ad ogni iterazione la variabile sequenza tiene conto dei vari cammini hamiltoiani
            // determinati considerando un nodo di partenza differente.
            // Ogni volta la variabile viene svuotata. Questo permette di creare meno strutture
            // dati e avere una complessità spaziale migliore.
            sequenza.clear();
            //Aggiungo come nodo di partenza del ciclo la variabile 'nodoPartenza' aggiungendolo
            // alla variabile 'sequenza'
            sequenza.add(nodoPartenza);

            // Metodo che permette di settare i valore dell'array visitati in maniera opportuna.
            // Ad ogni iterazione il vettore viene aggiornato mettendo a true solo il valore del
            // nodo di partenza passato come parametro
            this.resetVisitati(nodoPartenza);

            // Richiamo il metodo che implementa il NN. Questo metodo viene richiamato tante volte
            // quanti sono i nodi del grafo. Infatti, ad ogni iterazione, si determina il cammino
            // hamiltoiano considerando un diverso nodo di partenza. I vari nodi di partenza
            // vengono passati come parametro
            costo = this.sequenceBuilder(sequenza, nodoPartenza);

            // Stampa a video dei vari cicli hamiltoiani determinati e i relativi valore della
            // funzione obiettivo
            System.out.print("Ciclo hamiltoiano considerando " + (nodoPartenza+1) + " come nodo " +
                                     "di partenza: "+"[");
            for(int n : sequenza) {
                System.out.print((n+1) + " " );
            }
            System.out.println("]");
            System.out.println("Il costo associato: " + (costo) + "\n");

            // If che permette di tener conto della migliore soluzione
            if (costo < costoMinimo) {
                costoMinimo = costo;
                soluzione = new ArrayList<Integer>(sequenza);
            }

        }

        // Stampa a video della migliore soluzione determinata
        System.out.print("La soluzione determinata: [");
        for(int n : soluzione) {
            System.out.print((n+1) + " " );
        }
        System.out.println("]");
        System.out.println("Il costo del cammino: " + costoMinimo);
    }

    /**
     *  Questo metodo permette di settare in maniera oppurtuna il vettore visitati.
     *  Questa struttura dati tiene conto dei nodi visitati e quelli ancora non visitati. Prende
     *  come parametro il nodo di partenza in modo tale da
     *  impostare il relativo indice del vettore come visitato.
     * @param nodoPartenza
     */
    private void resetVisitati(int nodoPartenza) {
        // Impongo tutti i nodi come non visitati
        for (int i = 0; i < nodi; i++) {
            visitati[i] = false;
        }
        //Impongo l'elemento di indice 'nodoPartenza' a true in modo tale da considerarlo come visitato
        visitati[nodoPartenza] = true;
    }

    /**
     *  Metodo che permette di ricavare il ciclo Hamiltoiano seguendo i passi dell'algoritmo NN.
     *  Prende come parametro la struttura dati 'sequenza' e il nodo da cui partire per applicare
     *  l'algoritmo. Questo metodo viene richiamato sia nel caso in cui bisogna applicare il NN
     *  sia nel caso in cui bisogna utilizzare il RNN. Nel primo caso questo metodo viene
     *  richiamato una sola volta considerando un solo nodo di partenza.
     *  Nel secondo caso questo metodo viene richiamato tante volte quante sono i nodi nel grafo.
     *  Ad ogni iterazione la variabile nodoCorrente assume un valore differente.
     * @param sequenza
     * @param nodoCorrente
     * @return
     */
    private double sequenceBuilder(ArrayList<Integer> sequenza, int nodoCorrente) {
        // La variabile next consente di memorizzare il nodo  più vicino determinato
        int next = 0;
        // La variabile min consente di memorizzare il costo minimo del nodo più vicino determinato.
        // La variabile costo tiene conto del valore della funzione obiettivo.
        double min, costo = 0;

        // Loop che implementa l'algoritmo del NN. Il ciclo termina quando la sequenza determinata
        // ha un numero di nodi pari al numero di nodi presenti nel grafo
        while (sequenza.size() != nodi) {
            // imposto la variabile min con il massimo valore che un double può assumere
            min = Double.MAX_VALUE;
            // Ciclo che permette di determinare il nodo più vicino non ancora visitato rispetto al
            // nodo considerato (nodoCorrente).
            // Per nodo più vicino si intende il nodo il cui costo per raggiungerlo è minore di
            // tutti gli altri. Corrisponde al nodo, non ancora visitato, il cui valore della
            // matrice dei costi è il minore di tutti.
            for (int j = 0; j < nodi; j++) {
                if (nodoCorrente != j &&
                        (matrice[nodoCorrente][j] < min) &&
                        !visitati[j]) {
                    next = j;
                    min = matrice[nodoCorrente][j];
                }
            }
            // Una volta deterinato il nodo più vicino si aggiunge alla sequenza
            sequenza.add(next);
            // Si aggiorna il nodo da considerare per la prossima iterazione
            nodoCorrente = next;
            // Si imposta il nodo determinato come visitato
            visitati[next] = true;
            // Si aggiorna il valore della funzione obiettivo
            costo += min;
        }
        // Una volta determinata la sequenza si aggiorna il valore della funzione obiettivo
        // aggiungendo il costo per ritornare al nodo di partenza
        costo += matrice[sequenza.get(sequenza.size() - 1)][sequenza.get(0)];
        // Si aggiunge il nodo di partenza come ultimo nodo visitato
        sequenza.add(sequenza.get(0));
        // Ritorna il valore della funzione obiettivo
        return costo;
    }
}