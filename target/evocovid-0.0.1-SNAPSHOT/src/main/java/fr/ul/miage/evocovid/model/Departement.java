package fr.ul.miage.evocovid.model;

import java.io.IOException;
import java.util.Vector;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import fr.ul.miage.evocovid.utils.Utils;

public class Departement {

	/*
	 * Attributs de la classe Département
	 */
	final static String filenameDepatements = "data/departements-francais.csv";
	private String numDep; 
	private String nomDept;
	
	/*
	 * Les getters et setters de la classe Département
	 */
	public String getNumDep() {
		return numDep;
	}
	
	public void setNumDep(String numDep) {
		this.numDep = numDep;
	}
	
	public String getNomDept() {
		return nomDept;
	}
	
	public void setNomDept(String nomDept) {
		this.nomDept = nomDept;
	}
	
	public Departement(String numDep, String nomDept) {
		this.numDep = numDep;
		this.nomDept = nomDept;
	} 

	
	/*
	 * méthode qui retourne la liste des départements français à partir d'un fichier CSV 
	 */	
	public static Vector<Departement> getListeDepartements() throws IOException {
			Vector<Departement> v = new Vector <Departement>();
			CSVParser p = Utils.buildCVSParser(filenameDepatements, ';');
			int indLigne=0;
			for(CSVRecord r : p) {
				if(indLigne >0)
				{
					String numD=r.get(0);
					String nomD = r.get(1);
					Departement d = new Departement(numD.toUpperCase(), nomD.toUpperCase()); 
					v.add(d);
				}
				indLigne++;
			}
			return v;
		}
}
