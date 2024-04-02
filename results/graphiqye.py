# Importation des bibliothèques nécessaires
import pandas as pd
import matplotlib.pyplot as plt

# Chemin vers le fichier CSV
csv_file_path = '../results/result1.csv'  # Remplacez avec le chemin réel

# Lecture du fichier CSV dans un DataFrame
# Puisque le CSV n'a pas d'en-têtes, nous devons définir les noms de colonnes nous-mêmes
col_names = ['Day'] + ['Susceptible', 'Exposed', 'Infected', 'Recovered']  # À ajuster selon la structure de votre fichier
data = pd.read_csv(csv_file_path, header=None, names=col_names)

days = range(0, 750)  # Crée une liste de jours de 0 à 749
# Tracer chaque état au fil du temps
plt.figure(figsize=(10, 6))
plt.plot(data['Day'], data['Susceptible'], label='Susceptible', color='blue')
plt.plot(data['Day'], data['Exposed'], label='Exposed', color='orange')
plt.plot(data['Day'], data['Infected'], label='Infected', color='red')
plt.plot(data['Day'], data['Recovered'], label='Recovered', color='green')

# Ajout des étiquettes et légendes
plt.title('Population des agents S, E, I et R au fil du temps')
plt.xlabel('Temps (en jours)')
plt.ylabel('Ratio d\'agents dans chaque état')
plt.legend()
plt.grid(True)

# Affichage du graphique
plt.show()
