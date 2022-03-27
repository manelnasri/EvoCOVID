package fr.ul.miage.evocovid;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class HelloController {
	
@FXML
	private Button bt;

@FXML
private Button btEffacer1;

@FXML
private Button btEffacer2;

@FXML
private Button btEffacer3;

@FXML
private Button bt2;

@FXML 
private ChoiceBox<String> numDepHos;
@FXML 
private ChoiceBox<String> numDepHos1;

@FXML 
private ChoiceBox<String> choiceBoxPeriodeHos;

@FXML 
private ChoiceBox<String> choiceBoxPeriodeVac;




@FXML 
private ChoiceBox<String> inj;

@FXML
private ChoiceBox<String> numDepVac;

private static Vector departmentsAffichésHos; 
private static Vector departmentsAffichésHisto; 
private static Vector departmentsAffichésVac; 


@FXML
public void initialize() {
	 ObservableList<String> languages = FXCollections.observableArrayList(); 
	 ObservableList<String> périodes = FXCollections.observableArrayList(); 

	 ObservableList<String> injections = FXCollections.observableArrayList(); 

	 for (int i=0 ; i<90;i++)
	 {
		 languages.add(""+i);

	 }
	 numDepHos.setItems(languages);
	 numDepHos1.setItems(languages);
	 numDepVac.setItems(languages);
	 numDepHos.setValue("54");
	 numDepHos1.setValue("54");

	 
	 périodes.add("Toute la période");
	 périodes.add("1 mois");
	 périodes.add("3 mois");
	 périodes.add("12 mois");


	 
	 choiceBoxPeriodeHos.setItems(périodes);
	 choiceBoxPeriodeHos.setValue("Toute la période");
	 
	 choiceBoxPeriodeVac.setItems(périodes);
	 choiceBoxPeriodeVac.setValue("Toute la période");

	 
	 numDepVac.setValue("54");
	 injections.add("1");
	 injections.add("2");
	 injections.add("1 & 2");
	 inj.setItems(injections);
	 inj.setValue("1");
	 departmentsAffichésHos= new Vector();
	 departmentsAffichésHisto= new Vector();
	 departmentsAffichésVac= new Vector();

}

@FXML 
	private LineChart lineChartHospitalisation; 

@FXML 
	private LineChart lineChartVaccinations; 

@FXML 
private BarChart histo; 


@FXML
private void effacerCourbes() throws IOException, ParseException
{
	lineChartHospitalisation.getData().clear();
	departmentsAffichésHos.clear();
}

@FXML
private void effacerCourbesVac() throws IOException, ParseException
{
	lineChartVaccinations.getData().clear();
	departmentsAffichésVac.clear();
}

@FXML
private void effacerHisto() throws IOException, ParseException
{
	histo.getData().clear();
	departmentsAffichésHisto.clear();
}


@FXML
	private void afficherCourbeHospitalisation() throws IOException, ParseException
	{

	String période = choiceBoxPeriodeHos.getValue();
	if (période.equals("Toute la période"))
	{
		System.out.println("TOUTE LA PERIODE");
	}
	else
	{
		System.out.println(période);
	}
	if(!departmentsAffichésHos.contains(numDepHos.getValue()))
	{
		lineChartHospitalisation.autosize();
		Map<String, Integer> decesParJour = StatistiquesCovid.getStatistiqueDeces(numDepHos.getValue(), "semaine");
		XYChart.Series series = new XYChart.Series();
		for (Entry<String, Integer> mapentry : decesParJour.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
	           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
	    }	
		

			 departmentsAffichésHos.add(numDepHos.getValue());

			 lineChartHospitalisation.getData().add(series);
			 lineChartHospitalisation.getXAxis().setLabel("Semaine");
			 lineChartHospitalisation.getYAxis().setLabel("NB Personnes");

			 series.setName("Dep. "+numDepHos.getValue());

		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erreur de choix de département");
			alert.setHeaderText("Erreur de choix de département");
			alert.setContentText("La courbe du département "+numDepHos.getValue()+" est déjà affichée !");

			alert.showAndWait();
		}
	  
	}

@FXML
private void afficherCourbeVaccination() throws IOException, ParseException
{
	if(!departmentsAffichésVac.contains(numDepVac.getValue()+"-"+inj.getValue()))
	{
	System.out.println(numDepVac.getValue());
	HashMap<Integer, Integer> vaccParJour = StatistiquesCovid.getStatistiqueVaccination(numDepVac.getValue(), inj.getValue(), "semaine");
		
	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
	
	System.out.println("Dep. "+numDepVac.getValue()+"-Inj. "+inj.getValue());

	for (Entry<Integer, Integer> mapentry : vaccParJour.entrySet()) {
           //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
    }	
	series.setName("Dep. "+numDepVac.getValue()+"-Inj. "+inj.getValue());
	
	

		departmentsAffichésVac.add(numDepVac.getValue()+"-"+inj.getValue());
		lineChartVaccinations.getData().add(series);
		lineChartVaccinations.getXAxis().setAutoRanging(true);
		lineChartVaccinations.getXAxis().setLabel("Semaine");
		lineChartVaccinations.getYAxis().setLabel("NB Personnes");

		//lineChartVaccinations.getXAxis().autosize();
	}
	else
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erreur de choix de département");
		alert.setHeaderText("Erreur de choix de département");
		alert.setContentText("La courbe du département "+numDepVac.getValue()+" est déjà affichée !");

	alert.showAndWait();
	}

	

}

@FXML
private void afficherHistoGlobal() throws IOException, ParseException
{
	if(!departmentsAffichésHisto.contains(numDepHos1.getValue()))
	{
		Map<String, Integer> decesParJour = StatistiquesCovid.getStatistiqueDeces(numDepHos1.getValue(), "mois");

		histo.getData().clear();
		histo.getXAxis().setLabel("Mois");
		histo.getYAxis().setLabel("NB Personnes");

		histo.setTitle("Statistique COVID du département "+numDepHos1.getValue());
		XYChart.Series series = new XYChart.Series();
		for (Entry<String, Integer> mapentry : decesParJour.entrySet()) {
	           System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
	           series.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
	    }	
		

		departmentsAffichésHisto.add(numDepHos1.getValue());

		histo.getData().add(series);
		series.setName("NB Décès "+numDepHos.getValue());
			 
			//Afficher les Vaccins Total
			 HashMap<Integer, Integer> vaccParJour3 = StatistiquesCovid.getStatistiqueVaccination(numDepVac.getValue(), "1 & 2", "mois");
				
				XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
				

				for (Entry<Integer, Integer> mapentry : vaccParJour3.entrySet()) {
			           //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
			           series4.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
			    }	
				series4.setName("Vaccin-Inj. 1 & 2");

					//departmentsAffichésVac.add(numDepVac.getValue()+"-"+inj.getValue());
					histo.getData().add(series4);
					
			 
			 //Afficher les Vaccins Inj 1 
			 HashMap<Integer, Integer> vaccParJour = StatistiquesCovid.getStatistiqueVaccination(numDepVac.getValue(), "1", "mois");
				
				XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
				

				for (Entry<Integer, Integer> mapentry : vaccParJour.entrySet()) {
			           //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
			           series2.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
			    }	
				series2.setName("Vaccin-Inj. 1");

					//departmentsAffichésVac.add(numDepVac.getValue()+"-"+inj.getValue());
					histo.getData().add(series2);
					
					 //Afficher les Vaccins Inj 2 
					 HashMap<Integer, Integer> vaccParJour2 = StatistiquesCovid.getStatistiqueVaccination(numDepVac.getValue(), "2", "mois");
						
						XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
						

						for (Entry<Integer, Integer> mapentry : vaccParJour2.entrySet()) {
					           //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
					           series3.getData().add(new XYChart.Data<String, Number>(mapentry.getKey().toString(), mapentry.getValue()));
					    }	
						series3.setName("Vaccin-Inj. 2");

							//departmentsAffichésVac.add(numDepVac.getValue()+"-"+inj.getValue());
							histo.getData().add(series3);
							histo.setBarGap(1);
	}
	else
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erreur de choix de département");
		alert.setHeaderText("Erreur de choix de département");
		alert.setContentText("L'histogramme du département "+numDepVac.getValue()+" est déjà affiché !");

		alert.showAndWait();
	}

	
		 
}
String getMonthForInt(int m) {
    String month = "invalid";
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    if (m >= 0 && m <= 11 ) {
        month = months[m];
    	//System.out.println(month);
    }
    return month;
}


}