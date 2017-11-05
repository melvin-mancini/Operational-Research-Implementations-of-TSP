# Modello per TSP

# Numero dei nodi del grafo
param n > 0, integer;

# Insieme dei nodi
set V := 1..n;

# I costi associati ai vari archi
param c{V,V} >= 0;

# Parametro contatore che consente di tener conto del numero dei sottocicli
param numerocicli >= 0, integer, default 0;

# Insieme i cui elementi sono i nodi che corrispondono ai sotto-cicli individuati.
# Questo insieme avra' tanti elementi quanti sono i cicli (numerocicli)
set ciclo{1..numerocicli};

# Definizioni delle variabili come variabili binarie
var x{V,V} binary;

# Funzione obiettivo
minimize costociclo : sum{i in V, j in V : i != j} c[i,j]*x[i,j];

# Vincoli di grado
subject to successore {i in V} : sum{j in V : i != j} x[i,j] = 1;

subject to predecessore {j in V} : sum{i in V : i != j} x[i,j] = 1;

#Vincoli di eliminazione dei sotto-cicli
subject to nocicli {k in 1..numerocicli} :
	sum{i in ciclo[k], j in V diff ciclo[k]} x[i,j] >= 1;
