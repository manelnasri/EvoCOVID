# Documentation technique

## Données utilisées 
Deux sources de données ont été utilisées : 

* Données hospitalières relatives à l'épidémie de COVID-19 `https://www.data.gouv.fr/fr/datasets/donnees-hospitalieres-relatives-a-lepidemie-de-covid-19/`


* Données des rendez-vous pris dans des centres de vaccination contre la COVID-19 `https://www.data.gouv.fr/fr/datasets/donnees-des-rendez-vous-pris-dans-des-centres-de-vaccination-contre-la-covid-19/`

Dans ces sources de données, les départements sont décrits seulement par leurs numéros. Pour des raisons érgonomiques (d'affichage) et pour faciliter l'utilisation de notre application, j'ai utilisé les noms des départements. Pour obtenir la liste complète des départements, j'ai utilisé le fichier CSV suivant : 

* `https://www.regions-et-departements.fr/fichiers/departements-francais.csv`


## Structure du code source 

Le code source de **EVO COVID** est organisé en quatres packages : 

### Package `fr.ul.miage.evocovid` : 

Ce package contient les classes de la partie VUE du modèle MVC. Il contient pricipalement la classe `App` qui permet de construire l'interface graphique de EVO COVID. J'ai fait le choix d'utiliser plusieurs composants graphiques pour construire l'interface graphique de EVO COVID : 

- **`Border Pane`** pour le conteneur principal de EVO COVID

- **`TabPane`** pour les différents onglets (EVO COVID, EVO Décès, EVO Vaccination, EVO Statistiques et A propos)

- **`BarChart`** et **`PieChart`** pour l'affichage des statistiques générale par département (Onglet EVO COVID). Le `BarChart`est un histogramme qui affiche les différents indicateurs sous forme de barres. Le `PieChart` est utilisé pour afficher les mêmes indicateurs mais sous forme de diagramme circulaire (plus compréhensible dans certains cas). 

- **`LineChart`** pour l'affichage des différentes courbes d'évolution du nombre de décès par département (Onglet EVO Décès) et les courbes d'évolution des injections vaccinales COVID19 (Onglet EVO Vaccinations).  

- **`ListView`** pour l'affichage des listes des départements les plus touchés en terme de décès et du nombre d'injections vaccinales (Onglet EVO Statistiques). 

- **`ChoiceBox`** pour le choix du département, de la période de décès et pour choisir le type d'injection (Injection 1 ou 2 ou les deux).

- **`Label`** pour afficher les valeurs des statistiques au niveau national (Onglet EVO Statistiques) et pour les affichages des informations textuelles. 

### Package `fr.ul.miage.evocovid.model` : 

Ce package contient les classes du modèle à savoir la classe Département et la classe StatistiquesCovid. 
La classe **`Département`** encapsule les informations relatives aux différents départements français (nom du département, numéro du département, population, ...). 
la classe **`StatistiquesCovid`** encapsule les informations des statistiques globales et par département. 

Voici une liste de quelques attributs de la classe **`StatistiquesCovid `** :  

- **`nbDecesGlobal`** : le nombre de décès global (au niveau national)

- **`nbHosGlobal`** : le nombre d'hospitalisation global (au niveau national)

- **`nbReaGlobal`** : le nombre de patient en réanimation global (au niveau national)

- **`nbVacGlobalInj1`** : le nombre de RDV pour la première injection vaccinal du COVID 19. 

- **`nbVacGlobalInj2`** : le nombre de RDV pour la deuxième injection vaccinal du COVID 19. 

- **`DecesParDep`** : HashMap qui contient le nombre de décès par département 

- **`ReaParDep`** : HashMap qui contient le nombre de patients en réanimation par département 

- **`HospitalisationParDep`** : HashMap qui contient le nombre d'hospitalisation par département 

- **`VacParDep`** : HashMap qui contient le nombre de vaccination par département 


Voici une liste des méthodes de la classe **`StatistiquesCovid`** : 

- **`getStatistiqueGlobalesParDep()`** : une méthode pour chercher les statistiques globales (Onglet EVO Statistiques)

- **`getStatistiqueDeces()`** : une méthode pour chercher le nombre de décès par département (Onglet EVO Décès)


- **`getStatistiqueVaccination()`** : une méthode pour chercher le nombre d'injection vaccinal par département (Onglet EVO Vaccinations)


### Package `fr.ul.miage.evocovid.controller` : 

Ce package contient un seule classe **(`EvoCOVIDController.java`)** qui joue le rôle de controlleur. Cette classe permet de gérer les différents évennements sur l'interface graphique (validation des différents bouttons, récupération des valeurs des différentes listes de choix, traçage des différentes courbes dans les différents composants JAVAFX comme le `LineChart`, le `PieChart` et le `BarChart`). 

- Package **`fr.ul.miage.evocovid.utils`** : 
Ce package contient une seule classe `Utils` qui définie des méthodes utilisées principalement pour le parsing des fichiers CSV et pour faire quelques opérations sur les hashMaps utilisés pour stocker les différentes statistiques. 

Voici la liste des méthodes de la classe  **`Utils`** : 

- 	**`public static HashMap<String, Integer> sortByKeys(HashMap<String, Integer> map)`** : cette méthode permet de trier le contenu d'un HashMap en utilisant les clés. 

- **`public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hash)`** : cette méthode permet de trier le contenu d'un HashMap en utilisant les valeurs. 


- **`public static  CSVParser buildCVSParser(String filename, char delimeter)`** : cette méthode permet de traiter les fichiers CSV. 

- **`String getMonthForInt(int m)`** : cette méthode retourne le nom du mois en utilisant le numéro du mois. 
