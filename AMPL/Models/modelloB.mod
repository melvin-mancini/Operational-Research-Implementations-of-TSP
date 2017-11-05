# Modello per TSP
# Senza vincoli per l'eliminazione dei sottocicli

# Numero dei nodi del grafo
param n > 0, integer;

# Insieme dei nodi
set V := 1..n;

# Matrice dei costi
param c{V,V} >= 0;

# Definizioni delle variabili come variabili binarie
var x{V,V} binary;

# Funzione obiettivo
minimize costociclo : sum{i in V, j in V : i != j} c[i,j]*x[i,j];

# Vincoli di grado
subject to successore {i in V} : sum{j in V : i != j} x[i,j] = 1;

subject to predecessore {j in V} : sum{i in V : i != j} x[i,j] = 1;