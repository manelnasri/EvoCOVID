package fr.ul.miage.evocovid.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import fr.ul.miage.evocovid.model.Departement;
import fr.ul.miage.evocovid.model.StatistiquesCovid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class EvoCOVIDController {

	
/**
 * Déclaration des variables pour les différents composants graphiques de l'interface  
 */
	
@FXML
private Button bt;

@FXML
private Button btDCVac;

@FXML
private Button btEffacerDCVac;


@FXML
private Button btEffacer1;

@FXML
private Button btEffacer2;

@FXML
private Button btEffacer3;

@FXML
private Button bt2;

@FXML 
private ChoiceBox<String> numDepDC;

@FXML 
private ChoiceBox<String> numDepDCVac;

@FXML 
private ChoiceBox<String> numDepDC1;

@FXML 
private ChoiceBox<String> choiceBoxPeriodeDC;

@FXML 
private ChoiceBox<String> choiceBoxPeriodeDCVac;

@FXML
private ListView<String> listViewDepDC ; 

@FXML
private PieChart pieChart ; 

@FXML
private ListView<String> listViewDepVac ;

@FXML 
private Label labelNbDC, labelNbHos, labelNbRea,labelNbVacInj1,labelNbVacInj2 ; 

@FXML 
private LineChart lineChartDC; 

@FXML 
private LineChart lineChartDCVac; 

@FXML 
private LineChart lineChartDCVac1; 

@FXML 
private LineChart lineChartVaccinations; 

@FXML 
private BarChart histo; 

@FXML 
private ChoiceBox<String> inj;

@FXML 
private ChoiceBox<String> inj1;


@FXML
private ChoiceBox<String> numDepVac;

//Définition des listes des départements déjà affichés dans les courbes pour éviter de tracer la même courbe plusieurs fois 
private static Vector departmentsAffichésHos; 
private static Vector departmentsAffichésHosVac; 
private static Vector departmentsAffichésHisto; 
private static Vector departmentsAffichésVac; 

/*
 * Fonction d'initialisation des différents composants graphiques
 */
@FXML
public void initialize() throws IOException, ParseException {
	
	/*
	 * 	Contenu des listes de choix pour les départements concernés
	 */
	 ObservableList<String> departements = FXCollections.observableArrayList(); 
	 
	 /*
	  * 	Contenu des listes de choix pour les périodes concernées
	  */	 
	 ObservableList<String> périodes = FXCollections.observableArrayList(); 

	 /*
	  * 	Contenu des listes de choix pour le numéro de l'injection 
	  */
	 ObservableList<String> injections = FXCollections.observableArrayList(); 

	 /*
	  * parcourrs des départements et remplissage du vecteur departements
	  */
	 Vector listeDepartement = Departement.getListeDepartements();
	 for (int i=0 ; i<listeDepartement.size();i++)
	 {
		 Departement d = (Departement)listeDepartement.elementAt(i);
		 departements.add(d.getNumDep()+"-"+d.getNomDept());
	 }
	 
	 //remplissage des choiceBox des départements 
	 numDepDC.setItems(departements);
	 numDepDC1.setItems(departements);
	 numDepVac.setItems(departements);
	 numDepDCVac.setItems(departements);
	 
	 //Mettre le département 54 par défault 
	 Departement d = (Departement)listeDepartement.elementAt(54);
	 numDepDC.setValue(d.getNumDep()+"-"+d.getNomDept());
	 numDepDC1.setValue(d.getNumDep()+"-"+d.getNomDept());
	 numDepVac.setValue(d.getNumDep()+"-"+d.getNomDept());
	 numDepDCVac.setValue(d.getNumDep()+"-"+d.getNomDept());

	 
	 //Ajout des périodes possibles
	 périodes.add("Toute la période"); 
	 périodes.add("Année 2021"); 
	 périodes.add("Année 2020"); 

	 //Remplissage des choiceBox des périodes pour l'Onglet EVO Décès 
	 choiceBoxPeriodeDC.setItems(périodes);
	 choiceBoxPeriodeDC.setValue("Toute la période");
	 
	 
	 //Remplissage des choiceBox des périodes pour l'Onglet EVO Décès vs. Vac
	 choiceBoxPeriodeDCVac.setItems(périodes);
	 choiceBoxPeriodeDCVac.setValue("Toute la période");

	 
	 //numDepVac.setValue("54");
	 //Remplissage des choiceBox des injections pour l'Onglet EVO Vaccination
	 injections.add("1");
	 injections.add("2");
	 injections.add("1 & 2");
	 inj.setItems(injections);
	 inj.setValue("1");
	 inj1.setItems(injections);
	 inj1.setValue("1");

	 //Création des vecteurs pour la détection des cas d'erreurs lors des validations dans les différents Onglets
	 departmentsAffichésHos= new Vector();
	 departmentsAffichésHosVac= new Vector();
	 departmentsAffichésHisto= new Vector();
	 departmentsAffichésVac= new Vector();
	 
	 
	 
	 /**
	  * Remplissage des statisques globales 
	  */
	 
	 //Chercher les statistiques globales
	 StatistiquesCovid.getStatistiqueGlobalesParDep();
	 
	 //Remplissage des Label des différentes statistiques globales 
	 labelNbDC.setText(StatistiquesCovid.nbDecesGlobal+"");
	 labelNbHos.setText(StatistiquesCovid.nbHosGlobal+"");
	 labelNbRea.setText(StatistiquesCovid.nbReaGlobal+"");
	 labelNbVacInj1.setText(StatistiquesCovid.nbVacGlobalInj1+"");
	 labelNbVacInj2.setText(StatistiquesCovid.nbVacGlobalInj2+"");
	 
	 //Remplissage des listes des 5 départements les plus touchés en terme de décès
     int rang=1;
     for (Entry<String, Integer> mapentry : StatistiquesCovid.SortedDecesParDep.entrySet()) {
         listViewDepDC.getItems().add(rang + " - Département "+mapentry.getKey()+ " avec "+mapentry.getValue()+" Décès");
         rang++;
         if(rang>5)
        	 break;
		}
     
	 //Remplissage des listes des 5 départements les plus touchés en terme de vaccination
     rang=1;
     for (Entry<String, Integer> mapentry : StatistiquesCovid.SortedVacParDep.entrySet()) {
          listViewDepVac.getItems().add(rang + " - Département "+mapentry.getKey()+ " avec "+mapentry.getValue()+" Vaccinations");
          rang++;
          if(rang>5)
         	 break;
 		}
}

/*
 * Effacer les courbes des décès 
 */
@FXML
private void effacerCourbesDC() throws IOException, ParseException
{
	lineChartDC.getData().clear();
	departmentsAffichésHos.clear();
}

/*
 * Effacer les courbes de l'Onglet Décès vs. Vac
 */
@FXML
private void effacerCourbesDCVac() throws IOException, ParseException
{
	lineChartDCVac.getData().clear();
	lineChartDCVac1.getData().clear();

	departmentsAffichésHosVac.clear();
}

/*
 * Effacer les courbes de l'Onglet EVO Vaccination
 */
@FXML
private void effacerCourbesVac() throws IOException, ParseException
{
	lineChartVaccinations.getData().clear();
	departmentsAffichésVac.clear();
}

/*
 * Effacer l'histogramme et le PieChart de l'Onglet EVO COVID
 */

@FXML
private void effacerHisto() throws IOException, ParseException
{
	histo.getData().clear();
	pieChart.getData().clear();
	departmentsAffichésHisto.clear();
}


/*
 * Afficher les courbes de l'Onglet EVO Décès
 */

@FXML
	private void afficherCourbeDC() throws IOException, ParseException
	{
		//récupérer la période sélectionnée
		String période = choiceBoxPeriodeDC.getValue();
		int année =0;
		if (période.equals("Année 2021"))
		{
			année=2021;
		}
		else if (période.equals("Année 2020"))
		{
			année=2020;
		}
		
		//récupérer le numéro du département sélectionné
		String[] splitSelectedDep = numDepDC.getValue().split("-");
		String slectedDep= splitSelectedDep[0];
		
		if(!departmentsAffichésHos.contains(slectedDep+" Per. "+année))
		{
			lineChartDC.autosize();
			Map<Integer, Integer> decesParJour = StatistiquesCovid.getStatistiqueDeces(slectedDep, "semaine", année);
			
			//création de la série de données à insérer 
			XYChart.Series series = new XYChart.Series();
			
			//Ajout des données à la serie 
			for (Entry<Integer, Integer> mapentry : decesParJour.entrySet()) {
	           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
			}	
		

			 departmentsAffichésHos.add(slectedDep+" Per. "+année);
			 lineChartDC.getData().add(series);
			 lineChartDC.getXAxis().setLabel("Semaine");
			 lineChartDC.getYAxis().setLabel("NB Personnes");

			 if(année !=0)
				 series.setName("Dep. "+slectedDep+" Per. "+année);
			 else
				 series.setName("Dep. "+slectedDep);


		}
		else
		{
			//Afficher un message d'erreur si l'utilisateur choisi le même département 

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erreur de choix de département");
			alert.setHeaderText("Erreur de choix de département");
			alert.setContentText("La courbe du département "+slectedDep+" est déjà affichée !");

			alert.showAndWait();
		}
	  
	}

/*
 * Afficher les courbes de l'Onglet EVO Décès vs.Vac
 */

@FXML
private void afficherCourbeDCVac() throws IOException, ParseException
{
	//récupérer la période sélectionnée
	String période = choiceBoxPeriodeDCVac.getValue();
	int année =0;
	if (période.equals("Année 2021"))
	{
		année=2021;
	}
	else if (période.equals("Année 2020"))
	{
		année=2020;
	}
	
	//récupérer le numéro du département sélectionné
	String[] splitSelectedDep = numDepDCVac.getValue().split("-");
	String slectedDep= splitSelectedDep[0];
	
	if(!departmentsAffichésHosVac.contains(slectedDep+" Per. "+année))
	{
		
		 //Tracer la courbe des vaccinations 
		 Map<Integer, Integer> vaccParJour = StatistiquesCovid.getStatistiqueVaccination(slectedDep, inj1.getValue(), "semaine");
			
			XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		
			//Ajout des données à la serie 
			for (Entry<Integer, Integer> mapentry : vaccParJour.entrySet()) {
	           series2.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
			}	

			lineChartDCVac1.getData().add(series2);
			lineChartDCVac1.getXAxis().setLabel("Semaine");
			lineChartDCVac1.getYAxis().setLabel("NB Personnes");
			series2.setName("Vacc. Dep. "+slectedDep);

			
		 
			
		Map<Integer, Integer> decesParJour = StatistiquesCovid.getStatistiqueDeces(slectedDep, "semaine", année);
		
		//création de la série de données à insérer 
		XYChart.Series series = new XYChart.Series();
		
		//Ajout des données à la serie 
		for (Entry<Integer, Integer> mapentry : decesParJour.entrySet()) {
           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
		}	
	

		 departmentsAffichésHosVac.add(slectedDep+" Per. "+année);

		 lineChartDCVac.getData().add(series);
		 lineChartDCVac.getXAxis().setLabel("Semaine");
		 lineChartDCVac.getYAxis().setLabel("NB Personnes");
		 
		 if(année !=0)
			 series.setName("Décès Dep. "+slectedDep+" Per. "+année);
		 else
			 series.setName("Décès Dep. "+slectedDep);

		 lineChartDCVac.getXAxis().setAutoRanging(true);
		 
	}
	else
	{
		//Afficher un message d'erreur si l'utilisateur choisi le même département 

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erreur de choix de département");
		alert.setHeaderText("Erreur de choix de département");
		alert.setContentText("La courbe du département "+slectedDep+" est déjà affichée !");

		alert.showAndWait();
	}
  
}


/*
 * Afficher les courbes de l'Onglet EVO Vaccination
 */


@FXML
private void afficherCourbeVaccination() throws IOException, ParseException
{
	//récupérer le numéro du département sélectionné
	String[] splitSelectedDep = numDepVac.getValue().split("-");
	String slectedDep= splitSelectedDep[0];
	
	if(!departmentsAffichésVac.contains(slectedDep+"-"+inj.getValue()))
	{
		Map<Integer, Integer> vaccParJour = StatistiquesCovid.getStatistiqueVaccination(slectedDep, inj.getValue(), "semaine");	
		
		//création de la série de données à insérer 
		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		
		//Ajout des données à la serie 
		for (Entry<Integer, Integer> mapentry : vaccParJour.entrySet()) {
           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
		}	
		series.setName("Dep. "+slectedDep+"-Inj. "+inj.getValue());

		departmentsAffichésVac.add(slectedDep+"-"+inj.getValue());
		lineChartVaccinations.getData().add(series);
		lineChartVaccinations.getXAxis().setAutoRanging(true);
		lineChartVaccinations.getXAxis().setLabel("Semaine");
		lineChartVaccinations.getYAxis().setLabel("NB Personnes");
	}
	else
	{
		//Afficher un message d'erreur si l'utilisateur choisi le même département 
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erreur de choix de département");
		alert.setHeaderText("Erreur de choix de département");
		alert.setContentText("La courbe du département "+slectedDep+" est déjà affichée !");
		alert.showAndWait();
	}
}
/*
 * Afficher l'histogramme et e PieCHart dans l'Onglet EVOCOVID
 */

@FXML
private void afficherHistoGlobal() throws IOException, ParseException
{
	//récupérer le numéro du département sélectionné
	String[] splitSelectedDep = numDepDC1.getValue().split("-");
	String slectedDep= splitSelectedDep[0];
	if(!departmentsAffichésHisto.contains(slectedDep+"-"+inj.getValue()))
	{

		//Effacer le contenu de l'histogramme et du PieCHart
		histo.getData().clear();
		pieChart.getData().clear();
		
		//Ajout des titres des histogrammes
		histo.getYAxis().setLabel("NB Personnes");
		histo.setTitle("Statistique COVID du département "+slectedDep);
		
		//création de la série de données à insérer 
		XYChart.Series series = new XYChart.Series();
		
		/*
		 * Ajout des données à la serie 
		 */
		if (StatistiquesCovid.DecesParDep.containsKey(slectedDep))
			series.getData().add(new XYChart.Data<String, Number>("Décès", StatistiquesCovid.DecesParDep.get(slectedDep)));
		if (StatistiquesCovid.ReaParDep.containsKey(slectedDep))
			series.getData().add(new XYChart.Data<String, Number>("Réanimation", StatistiquesCovid.ReaParDep.get(slectedDep)));
		if (StatistiquesCovid.VacParDep.containsKey(slectedDep))
			series.getData().add(new XYChart.Data<String, Number>("Vaccination", StatistiquesCovid.VacParDep.get(slectedDep)));
		if (StatistiquesCovid.HospitalisationParDep.containsKey(slectedDep))
			series.getData().add(new XYChart.Data<String, Number>("Hospitalistions", StatistiquesCovid.HospitalisationParDep.get(slectedDep)));

		// ajouter les données à l'histogamme
		histo.getData().add(series);		
		histo.setBarGap(1);
		
		//création de ObservableList qui va conteenir les données à afficher avec le pieChart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        // ajout des données à la liste ObservableList
		if (StatistiquesCovid.DecesParDep.containsKey(slectedDep))
			pieChartData.add(new PieChart.Data("Décès", StatistiquesCovid.DecesParDep.get(slectedDep)));
		if (StatistiquesCovid.HospitalisationParDep.containsKey(slectedDep))
			pieChartData.add(new PieChart.Data("Hospitalistions", StatistiquesCovid.HospitalisationParDep.get(slectedDep)));
		if (StatistiquesCovid.VacParDep.containsKey(slectedDep))
			pieChartData.add(new PieChart.Data("Vaccination", StatistiquesCovid.VacParDep.get(slectedDep)));
		if (StatistiquesCovid.ReaParDep.containsKey(slectedDep))
			pieChartData.add(new PieChart.Data("Réanimation", StatistiquesCovid.ReaParDep.get(slectedDep)));

		// ajouter les données à l'histogamme circulaire PieChart

		pieChart.setLabelLineLength(10);
		pieChart.setData(pieChartData);	 
		
		departmentsAffichésHisto.add(slectedDep+"-"+inj.getValue());

	}
	else
	{
			//Afficher un message d'erreur si l'utilisateur choisi le même département 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erreur de choix de département");
			alert.setHeaderText("Erreur de choix de département");
			alert.setContentText("Les infomations du département "+slectedDep+" sont déjà affichées !");
			alert.showAndWait();
	}
		
	
}

}