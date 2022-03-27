package fr.ul.miage.evocovid.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import fr.ul.miage.evocovid.utils.Utils;

/**
 * @author Manel Nasri
 *
 */
public class StatistiquesCovid {


	//déclaration des fichiers de données (décès et vaccination)
	final static String filenameDC = "data/décèsCOVID.csv"; 
	final static  String filenameVac = "data/vaccinationCOVID.csv"; 
	
	//déclaration des variables pour les statistiques globales 
	
	//nombre de décès global 
	public static int nbDecesGlobal;
	
	//nombre d'hospitalisation global
	public static int nbHosGlobal;
	
	//nombre de patients en réanimation global
	public static int nbReaGlobal;
	
	//nombre de vaccinations global (Injection 1, Injection 2) 
	public static int nbVacGlobalInj1;
	public static int nbVacGlobalInj2;

	
	/*
	 * Déclaraition d'un HashMap pour stocker les nombres de décès par département
	 */
	public static HashMap<String, Integer> DecesParDep = new HashMap<String, Integer>();
	/*
	 * Déclaraition d'un HashMap pour stocker les nombres de décès par département (triés)
	 */
	public static HashMap<String, Integer> SortedDecesParDep = new HashMap<String, Integer>();
	/*
	 * Déclaraition d'un HashMap pour stocker les nombres d'hospitalisation par département
	 */
	public static HashMap<String, Integer> HospitalisationParDep = new HashMap<String, Integer>();

	/*
	 * Déclaraition d'un HashMap pour stocker les nombres d'hospitalisation par département (triés)
	 */
	public static HashMap<String, Integer> SortedHospitalisationParDep = new HashMap<String, Integer>();

	/*
	 * Déclaraition d'un HashMap pour stocker les nombres de patients en réanimation par département (triés)
	 */
	public static HashMap<String, Integer> ReaParDep = new HashMap<String, Integer>();
	
	/*
	 * Déclaraition d'un HashMap pour stocker les nombres de vaccinations par département 
	 */
	public static HashMap<String, Integer> VacParDep = new HashMap<String, Integer>();
	
	/*
	 * Déclaraition d'un HashMap pour stocker les nombres de vaccinations par département (triés)
	 */
	public static HashMap<String, Integer> SortedVacParDep = new HashMap<String, Integer>();

	
	/*
	 * Méthode statique pour le calcul des statistiques globales sur le décès et la vaccination
	 * Les statistiques seront stockées dans les variables statiques de type hashMaps   
	 */
	public static void getStatistiqueGlobalesParDep() throws IOException, ParseException
	{
		CSVParser p = Utils.buildCVSParser(filenameDC, ';');
		int indLigne=0;
		int totalDCParDep=0;
		int totalHosParDep=0;
		int totalReaParDep=0;

		//Parcours du csv 
		for(CSVRecord r : p) {
			if (indLigne>0)
			{
				//récupérer les différentes informations 
				String numDep =r.get(0);
				int sexe = Integer.parseInt(r.get(1));
				String date = r.get(2);
				int hosp = Integer.parseInt(r.get(3));
				int rea = Integer.parseInt(r.get(4));	
				int nbDec = Integer.parseInt(r.get(6));

				//incrémenter le nombre de décès 
				totalDCParDep+= nbDec;
				//incrémenter le nombre d'hospitalisation 
				totalHosParDep+=hosp;
				//incrémenter le nombre de patients en réanimation  
				totalReaParDep+=rea;
				
				/*
				 * Les données du fichier CSV des décès sont présentées comme suit : pour un département donné, un jour donné et un sexe (0, 1 ou 2), donc les données quotidiennes de chaque département sont présentées en trois lignes (sexe =0, sexe =1 et sexe=2). 
				 * Ici, on vérifie si la ligne du CSV traitée correspond à la 3èmee ligne (sexe =2) qui correspond à un département et un jour donné, et on ajoute les informations aux différents hMaps. 
				 * 
				 */
				if(sexe ==2)
				{
					DecesParDep.put(numDep, totalDCParDep);
					HospitalisationParDep.put(numDep, totalHosParDep);
					ReaParDep.put(numDep, totalReaParDep);
					totalReaParDep=0;
					totalDCParDep=0;
					totalHosParDep=0;
				}	
			}
			indLigne++;
		}
		
		/*
		 * Traitement des données sur les vaccinations
		 */
		p = Utils.buildCVSParser(filenameVac, ',');
		
		//Parcours du csv 
		indLigne=0;
		for(CSVRecord r : p) {
			if (indLigne>0)
			{
				/*
				 * Récupération des valeurs de chaque attribut (numéro du département, date début de semaine, rang vaccinal et nombre de rdv confirmés
				 */
				String numDep = r.get(2);
				String dateDebSemaine = r.get(4);
				int rangVaccinal = Integer.parseInt(r.get(3));
				int nbVacc = Integer.parseInt(r.get(5));	

				
				/*
				 * Calcul du nombre total par département 
				 */
				int ancienneValeurVac1_et_2=0;
				
				if(VacParDep.containsKey(numDep))
				{
					ancienneValeurVac1_et_2=VacParDep.get(numDep);
				}
				
				VacParDep.put(numDep, ancienneValeurVac1_et_2+nbVacc);
				
				if (rangVaccinal==1)
					nbVacGlobalInj1+=nbVacc;
				else
					nbVacGlobalInj2+=nbVacc;
			}
			indLigne++;
		}
		
		//calculer des informations globales par département
		nbDecesGlobal=getNombreTotalParDep(DecesParDep);
		nbHosGlobal=getNombreTotalParDep(HospitalisationParDep);
		nbReaGlobal=getNombreTotalParDep(ReaParDep);
		
		/*
		 * Trie des informations calculées par département
		 */
		SortedDecesParDep = Utils.sortByValues(DecesParDep);
		SortedHospitalisationParDep = Utils.sortByValues(HospitalisationParDep);
		SortedVacParDep = Utils.sortByValues(VacParDep);
	}
	
	
	/*
	 * Cette méthode renvoie le nombre de patients global pa département
	 * Cette méthode est utilisée pour déterminer le nombre de décès total, le nombre de patients en réanimation global, le nombre de vaccinations, ...
	 */
	public static int getNombreTotalParDep(HashMap<String, Integer> InfoParDep)
	{
		int nb=0;
		
		for (Entry<String, Integer> mapentry : InfoParDep.entrySet()) {
	           //System.out.println("dep: "+mapentry.getKey() + " | nbDC: " + mapentry.getValue());
	           nb+=mapentry.getValue();
			}	
		
		return nb;
	}
	
	

	/*
	 * Méthode statique pour le calcul des statistiques globales sur les décès 
	 * Les statistiques seront stockées dans les variables statiques de type hashMaps  
	 * La méthode offre la possibilité de faire les calculs par semaine ou par mois   
	 */

	public static Map<Integer, Integer> getStatistiqueDeces(String numD, String clé, int année) throws IOException, ParseException
	{
		HashMap<Integer, Integer> hMapDeces = new HashMap<Integer, Integer>();
		CSVParser p = Utils.buildCVSParser(filenameDC, ';');
		int indLigne=0;
		int totalDCParDep=0;

		//Parcours du csv 
		for(CSVRecord r : p) {
				if (indLigne>0)
				{
					//récupérer les différentes informations 
					String numDep = r.get(0);
					int sexe = Integer.parseInt(r.get(1));
					String date = r.get(2);
					String nbDec = r.get(6);						
					
					//test sur le département 
					if(numDep.equals(numD))
					{
						Calendar c = Calendar.getInstance() ;
				        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				       
				        Date d=formatter.parse(date);
				        c.setTime(d);
				        
				        int week = c.get(c.WEEK_OF_YEAR );
				        int mois = c.get(c.MONTH )+1;
				        int ann = c.get(c.YEAR );

				        totalDCParDep+=Integer.parseInt(nbDec);
				        if((sexe==2) && ((ann == année ) || (année ==0)))
				        {
				        	
				        	if (clé.equals("semaine"))
							{
								hMapDeces.put(week, totalDCParDep);
							}
							else if(clé.equals("mois")) {
								//System.out.println("Mois =  " +mois +" total DC =  "+totalDCParDep);
								hMapDeces.put(mois, totalDCParDep);
							}
				        	
				        	totalDCParDep=0;
				        }
					}
				}
				indLigne++;
			}
		
		//trie du HashMap des décès à l'aide d'un TreeMap
		Map sortedMap = new TreeMap(hMapDeces);		 

		return sortedMap;
	}
	
	/*
	 * cette méthode retourne lees statistiques des vaccinations en fonction du numéro du dépatement, du numérro de l'injection et aussi de la période (jour, semaine ou mois) 
	 */
	public static Map<Integer, Integer> getStatistiqueVaccination(String numD, String inj, String clé) throws IOException, ParseException
	{
		HashMap<Integer, Integer> hMapVaccination = new HashMap<Integer, Integer>();
		CSVParser p = Utils.buildCVSParser(filenameVac, ',');
		
		//Parcours du csv 
		int indLigne=0;
		for(CSVRecord r : p) {
				if (indLigne>0)
				{
					
					//récupérer les différentes informations 
					String numDep = r.get(2);
					String dateDebSemaine = r.get(4);
					String injection = r.get(3);
					String nbVacc = r.get(5);	
					Calendar c = Calendar.getInstance() ;

			        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			       
			        Date d=formatter.parse(dateDebSemaine);
			        c.setTime(d);
			        int week = c.get(c.WEEK_OF_YEAR );
			        int mois = c.get(c.MONTH )+1;
					if (numDep!= null && numDep.equals(numD))
					{

						if(inj.equals(injection) || inj.equals("1 & 2"))
						{
							if (clé.equals("semaine"))
							{
								int ancienneValeurVacc=0;
								if(hMapVaccination.containsKey(week))
								{
									ancienneValeurVacc=hMapVaccination.get(week);
								}
								
								hMapVaccination.put(week, ancienneValeurVacc+Integer.parseInt(nbVacc));
							}
							else if (clé.equals("mois"))
							{
								int ancienneValeurVacc=0;
								if(hMapVaccination.containsKey(mois))
								{
									ancienneValeurVacc=hMapVaccination.get(mois);
								}
								
								hMapVaccination.put(mois, ancienneValeurVacc+Integer.parseInt(nbVacc));
							}
							
						}
						
					}
	
				}
				indLigne++;
			}
		
		Map sortedMap = new TreeMap(hMapVaccination);	
		
		return sortedMap;
	}
}
