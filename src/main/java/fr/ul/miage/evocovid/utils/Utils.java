package fr.ul.miage.evocovid.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Manel Nasri
 *
 */
public class Utils {
	
	//méthode qui renvoie le nom du mois à partir du numéro du mois (entre 1 t 12)
	public static String getMonthForInt(int m) {
	    String month = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (m >= 0 && m <= 11 ) {
	        month = months[m];
	    }
	    return month;
	}
	
	//méthode qui crée un CSVParser pour parser des fichiers CSV
	public static  CSVParser buildCVSParser(String filename, char delimeter) throws IOException{
		CSVParser res = null;
		Reader in;
		in = new FileReader(filename);
		CSVFormat csvf = CSVFormat.DEFAULT.withCommentMarker('#').withDelimiter(delimeter).withNullString("");
		res = new CSVParser(in, csvf);
		return res;
	}
	
	/*
	 * Cette méthode permet de trier le contenu d'un hashMap en utlisant les clés
	 */
	public static HashMap<String, Integer> sortByKeys(HashMap<String, Integer> map) {
		HashMap <Integer,Integer> unSorted = new  HashMap<Integer, Integer>();
		HashMap <String,Integer> sorted = new  HashMap<String, Integer>();
        for (HashMap.Entry<String, Integer> entry : map.entrySet())
        {
            try{
                int foo = Integer.parseInt(entry.getKey());            
                unSorted.put(foo, entry.getValue());                
            }catch (Exception e){

            }
        }
        Map<Integer, Integer> newMap = new TreeMap<Integer, Integer>(unSorted); 
        Set set = newMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
            sorted.put(me.getKey().toString(),  (Integer)me.getValue());
       }

        return sorted;
    }
	
	/*
	 * Cette méthode permet de trier le contenu d'un hashMap en utlisant les valeurs des éléments du hashMap
	 */
    public static HashMap<String, Integer> sortByValues(final HashMap<String, Integer> hash) {

        return hash.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

	}
	
	


