
//package CreateNeo4j;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;

public class testFrance {
	public static final String NL = System.getProperty("line.separator") ;
	public static GraphDatabaseService graphdbservice;

	public static void main(String[] args) {

		//GetAll noeuds = new GetAll();
		Model m = ModelFactory.createOntologyModel();
		France gr = new France();
		gr.createNodeSpace();
		
		gr.printNeoVoisins(m);
		m.read("statistiques.rdf");
		
	    
	        //Direct way: Make a TDB-back Jena model in the named directory.
	    	    	   	        
	        
			  String statistiques_ns="http://www.statistiques.fr#";
			  String prolog1 = "PREFIX v: <"+statistiques_ns+">" ;
		      String prolog2 = "PREFIX rdf: <"+RDF.getURI()+">" ;
			  String prolog3 = "PREFIX stat: <"+statistiques_ns+">" ;
			  
			  String sparqlQueryString  =prolog1 + NL + prolog2 + NL + prolog3 + NL + " SELECT  ?c ?code "
	      	+ "WHERE { " +
	      	"?dep stat:pop ?c." +
	      	"?dep stat:code ?code." +
	      	
	      
	      	
	          	"}";
	        // See http://incubator.apache.org/jena/documentation/query/app_api.html
	        
	        Query query = QueryFactory.create(sparqlQueryString) ;
	        QueryExecution qexec = QueryExecutionFactory.create(query, m) ;
	        ResultSet results = qexec.execSelect() ;
	        ResultSetFormatter.out(results) ;
	        qexec.close() ;

	        
	       
		
	}

	
	
}


