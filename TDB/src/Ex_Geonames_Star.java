import java.util.Iterator;

import com.hp.hpl.jena.sparql.core.ResultBinding;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;


public class Ex_Geonames_Star
{

    public static final String NL = System.getProperty("line.separator") ;

	  public static void main(String[] args)
	    {
		  Model m = ModelFactory.createOntologyModel();
		  String fil_URL = "file:geonames_v3.rdf";
		  String geonames_ns="http://www.geonames.org/ontology#";
		  String skos_ns="http://www.w3.org/2004/02/skos/core#";
		  String prolog1 = "PREFIX geonames: <"+geonames_ns+">" ;
		  String prolog2 = "PREFIX rdf: <"+RDF.getURI()+">" ;
		  String prolog3 = "PREFIX skos: <"+skos_ns+">" ;
		  m.read(fil_URL);
		  // Query string.
		  String rdq = prolog1 + NL + prolog2 + NL + prolog3 + NL +
		  "SELECT ?x  ?label ?class ?definition WHERE {?x rdf:type skos:Concept ." +
		  " ?x skos:prefLabel ?label ." +
		  " ?x skos:definition ?definition ." +
		  " ?x skos:inScheme ?class ." +
		  " FILTER (lang(?definition)='en') ." +
		  " FILTER (lang(?label)='en') }" ;

		  Query query = QueryFactory.create(rdq);
		  QueryExecution qexec = QueryExecutionFactory.create(query, m);
		  try {
			  Iterator<QuerySolution> results = qexec.execSelect();
			  RDFVisitor aVisitor = new Skos_UnVisiteur();
			  System.out.println("Concepts geonames, definition et nom de classe : ");
			  for (;results.hasNext();)
			  {
				  QuerySolution sol = results.next();
				  RDFNode node = sol.get("x");
				  node.visitWith(aVisitor);
				  RDFNode cl = sol.get("class");
				  cl.visitWith(aVisitor);
				  RDFNode label = sol.get("label");
				  if (label.isLiteral())
				  {Literal label_en = (Literal) label;
				  if (label_en.getLanguage().equals("en")) 
					  label_en.visitWith(aVisitor);}
				  RDFNode definition = sol.get("definition");
				  if (definition.isLiteral())
				  {Literal definition_en = (Literal) definition;
				  if (definition_en.getLanguage().equals("en")) 
				  definition_en.visitWith(aVisitor);}
				  
			  }
		  }
		  finally {qexec.close();}
	    }
	}