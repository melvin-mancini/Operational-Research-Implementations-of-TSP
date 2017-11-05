import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * La classe Main è la classe principale. La sua istanza viene creata non appena il programma viene
 * mandato in esecuzione. Al suo interno sono presenti metodi e strutture dati che consentono di
 * memorizzare i dati di input inseriti dall'utente.
 */
public class Main {

    /**
     * Il main è il primo metodo che viene richiamato. In questa sezione ci sono gli oggetti e le
     * variabili che permettono di prelevare i dati di input inseriti dall'utente e successivamente
     * verranno richiamati i metodi relativi alla determinazione della soluzione in base alla
     * tecnica scelta.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Variabili e strutture dati richiesti per la risoluzione del problema
        int numeroNodi;
        int nodoPartenza = 0;
        int metodo;
        double matrix[][];
        //Oggetto per prelevare i dati di input inseriti dall'utente
        Scanner scanner = null;
        //In questa parte è presente il codice che permette di prelevare i dati di input da schermo
        // e successivamente vengono chiamati i metodi relative alle tecniche euristiche
        // implementate
        System.out.println("\n\t####################\t TSP PROBLEM\t #################### \n\n");
        try {
            System.out.println("Inserisci il numero di nodi presenti nel grafo: ");
            scanner = new Scanner(System.in);
            numeroNodi = scanner.nextInt();
            System.out.println("Scegli l'algoritmo che vuoi applicare: \n 1- Nearest Neighbour \n" +
                                       " 2- Ripetitive Nearest Neighbour");
            scanner = new Scanner(System.in);
            metodo = scanner.nextInt();

            //L'if in esame serve nel cosa in cui l'utente abbia scelto il NN e di conseguenza
            // verrà richiesto anche il
            //di partenza
            if (metodo == 1) {
                System.out.println("\n Inserisci il nodo da cui partire: ");
                scanner = new Scanner(System.in);
                nodoPartenza = scanner.nextInt();
                nodoPartenza = nodoPartenza - 1;
            }

            // Matrix contiene la matrice dei costi. La variabile matrix viene riempita
            // richiamando il metodo acquisisciMatrice() descritto successivamente
            matrix = acquisisciMatrice(numeroNodi);
            TSP tsp = new TSP(numeroNodi, matrix);

            //L'if in esame serve per richiamare il metodo opportuno in base alla tecnica euristica
            // scelta.
            if (metodo == 1) {
                System.out.println("\n\t####################\t SOLUZIONE DETERMINATA\t #################### \n");
                // La riga di codice successiva richiama il metodo nearestNeighbor() dell'oggetto
                // tsp precedentemente
                // creato
                tsp.nearestNeighbor(nodoPartenza);
            } else {
                // La riga di codice successiva richiama il metodo repetitiveNearestNeighbor()
                // dell'oggetto tsp precedentemente creato
                System.out.println("\n\t####################\t SOLUZIONI DETERMINATE\t #################### \n");
                tsp.repetiveNearestNeighbor();
            }
            scanner.close();
        } catch (InputMismatchException inputMismatch) {
            System.out.println("Errore di input");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo prende come paramentro il numero di nodi inseriti dall'utente e successivamente
     * consente di prelevare i dati relativi alla matrice dei costi
     *
     * @param nodi
     * @return
     */
    public static double[][] acquisisciMatrice(int nodi) {
        Scanner scanner = null;
        scanner = new Scanner(System.in);
        double matrice[][] = new double[nodi][nodi];
        System.out.println("\n\t#################### INSERIMENTO MATRICE EI COSTI ####################\n");
        System.out.println("Inserisci la matrice come un vettore riga. Ogni elemento deve essere separato da uno spazio.");
        System.out.println("ATTENZIONE: Data la formulazione matematica del TSP, i valori della diagonale verranno sostituiti con degli zeri. \n");

        // Ogni valore inserito dall'utente viene inserito nell'opportuna posizione della matrice
        for (int i = 0; i < nodi; i++) {
            for (int j = 0; j < nodi; j++) {
                matrice[i][j] = scanner.nextDouble();
            }
        }
        // dato che il TSP non permette di avere nodi con autoanelli vengono sostituiti tutti i
        // valori della diagonale della matrice con lo 0. Questo procedimento viene effettuato
        // per evitare errori nella risolzione del problema
        for (int i = 0; i < nodi; i++) {
            for (int j = 0; j < nodi; j++) {
                if (i == j) {
                    matrice[i][j] = 0;
                }
            }
        }
        stampaMatrice(nodi, matrice);
        // Il valore di ritorno del metodo è la matrice inserita
        return matrice;
    }
    
    public static void stampaMatrice(int nodi, double[][] matrice){
        System.out.println("\n\t###################\t MATRICE INSERITA\t ####################\n");
        for (int i = 0; i < nodi; i++) {
            for (int j = 0; j < nodi; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
