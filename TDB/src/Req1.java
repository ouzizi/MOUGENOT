
import com.hp.hpl.jena.query.Dataset ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.tdb.store.DatasetGraphTDB;

import java.util.*;
import com.hp.hpl.jena.util.FileManager;

public class Req1
{
	public static final String departement = "departement.rdf";
	public static final String canton = "canton.rdf";
	public static final String region = "region.rdf";
	public static final String arrondissement = "arrondissement.rdf";
	
    public static void main(String[] args)
    {
        // Make a TDB-back Jena model in the named directory.
    	
    	
        String directory = "/home/ouafae/Documents/Mougenotprojet" ;
        Dataset ds = TDBFactory.createDataset(directory) ;
        Model reg = ds.getNamedModel("geo" );       
        FileManager.get().readModel( reg, departement );
       FileManager.get().readModel( reg, canton );
        FileManager.get().readModel( reg, region );
        FileManager.get().readModel( reg, arrondissement );
        
        
        String req1=
		        		"PREFIX b:<http://rdf.insee.fr/geo/>" +
		        		"SELECT ?x ?y ?z " +
		        		"WHERE {?x b:code_region ?y . ?x b:nom ?z }" +
		        		"LIMIT 10";
        
        String req2=
        				"PREFIX b:<http://rdf.insee.fr/geo/>" +
        				"SELECT ?z ?coded ?nomd " +
        				"WHERE { " +
        				"		 ?x b:nom ?z . " +
        				"		 ?x b:subdivision ?s ." +
        				"		 ?s b:code_departement ?coded ." +
        				"	     ?s b:nom ?nomd " +
        				" 		 FILTER regex(?z ,\"Languedoc-Roussillon\" )"+
        			"} ";
        
        String req3=
        				 "PREFIX b:<http://rdf.insee.fr/geo/>" +
        		 		 "SELECT ?ville " +
        				 "WHERE {{ ?a b:code_region ?b . ?a b:chef-lieu ?c . ?c b:nom ?ville }" +
        				 " UNION" +
        				 "{?a b:code_departement ?b . ?a b:chef-lieu ?c . ?c b:nom ?ville } }" ;
        				
        
        String req4=
        				 "PREFIX b:<http://rdf.insee.fr/geo/>" +
        				 "SELECT ?dep (COUNT(?arr) as ?NBR_Arrondissement)" +
        				 "WHERE {" +
        				 " ?x b:code_departement ?y . ?x b:nom ?dep . ?x b:subdivision ?arr " +
        				 "FILTER regex(?dep ,\"Ain\" )" +
        				 "}" +
        				 "GROUP BY ?dep" ;
       
        Query query = QueryFactory.create(req1);
    	QueryExecution qexec = QueryExecutionFactory.create(query, reg) ;
    	
    	
    	
    	try {
    	
    	ResultSet rs = qexec.execSelect() ;
    	ResultSetFormatter.out(System.out, rs, query);
    	
    	
    	
    	
    	}
    	finally
    	{
    	  qexec.close() ;
    	}
        
//        Model	model = null;
//        Iterator<String> graphNames = ds.listNames();
//        while (graphNames.hasNext()) {
//            String graphName = graphNames.next();       
//      model = ds.getNamedModel(graphName);
//      		System.out.println("Named graph " + graphName + " size: " + model.size());
//       	}  	       
//        ds.close();
        
    }
}