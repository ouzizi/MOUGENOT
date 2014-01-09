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
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;


// il faut lier les deux shema de modeles de geonames et insee par une relation rdfs:subclssOf 
// puis exploiter cette liaison au niveau des données de ces deux shema de modele
public class Req2 {
	
	public static final String departement = "departement.rdf";
	public static final String canton = "canton.rdf";
	public static final String region = "region.rdf";
	public static final String arrondissement = "arrondissement.rdf";
	public static final String geonames = "ontology_v3.1.rdf";
	public static final String insee = "insee-geo-onto.ttl";
	
	
	   public static void main(String[] args)
	    {
	       
		   
		   // Make a TDB-back Jena model in the named directory.
	        String directory = "/home/ouafae/Documents/Mougenotprojet" ;
	        Dataset ds = TDBFactory.createDataset(directory) ;
	        Model m1 = ds.getNamedModel("geo" );
	        Model m2 = ds.getNamedModel("ins" ); 
	       
	       
	        
	     	String nsm2 = "http://www.geonames.org/ontology#";
	    	String nsm1 = "http://rdf.insee.fr/def/geo/";
	    	
	    	//String CompueterS ="http://www.test.fr/filiere#";
	    	
	    	
	    	
			m1.setNsPrefix("insee", nsm1);
			m2.setNsPrefix("geonames", nsm2);
	        
	        
	        FileManager.get().readModel( m2, geonames );
	        FileManager.get().readModel( m1, insee );
	        FileManager.get().readModel( m1, departement );
	        FileManager.get().readModel( m1, canton );
	        FileManager.get().readModel( m1, region );
	        FileManager.get().readModel( m1, arrondissement );
	        
	        m2.add(m1);
	        
	        InfModel infm = ModelFactory.createRDFSModel(m2);
	        
	        Resource region=m1.getResource(nsm1+"Region");
	        
	        Resource classDepartelent=m1.getResource(nsm1+"Departement");
	        Resource classFeature=m2.getResource(nsm2+"Feature");
	        
	        infm.add(classDepartelent,RDFS.subClassOf,classFeature);
	        
	        
	        //infm.add(dep,OWL.equivalentClass,classAdmin);
	        
	       // Resource x=infm.getResource(nsm2+"Class");
	        
	        
//	        ResIterator res_i = infm.listSubjectsWithProperty( RDFS.subClassOf,x );
//	        
//	        while (res_i.hasNext())
//	    	{ Resource s = res_i.nextResource();
//	    	System.out.println(s);}
//	        
       
	        String req1=
		        		"PREFIX b:<http://rdf.insee.fr/geo/>" +
		        		"SELECT ?x ?y ?z " +
		        		"WHERE {?x b:code_region ?y . ?x b:nom ?z }" +
		        		"LIMIT 10";
	        
	        String req2=
	        			"PREFIX b:<http://rdf.insee.fr/geo/>" +
	        			"PREFIX g:<http://www.geonames.org/ontology#>" +
	        			"PREFIX skos:<http://www.w3.org/2004/02/skos/core#>" +
	        			
	        			"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
	        			"SELECT ?z ?x " +
	        			" WHERE {  ?z rdf:type g:Code . OPTIONAL { ?z b:code_departement ?x } }" ;
	        
	        

	        
	        Query query = QueryFactory.create(req2);
	    	QueryExecution qexec = QueryExecutionFactory.create(query, infm) ;
	    	
	    	
	    	
	    	try {
	    	
	    	ResultSet rs = qexec.execSelect() ;
	    	ResultSetFormatter.out(System.out, rs, query);
	    	
	    	
	    	
	    	
	    	}
	    	finally
	    	{
	    	  qexec.close() ;
	    	}
	        
	        
	   
	    
	    
	    
	    }
	   

}
