import org.openjena.atlas.io.IndentedWriter;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.vocabulary.RDF;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;


public class D2R_Un
{
	 public static final String NL = System.getProperty("line.separator") ;
   public static void main(String[] args)
   {
         
         //creer le modele D2RQ
         Model d2rqModel = new ModelD2RQ("file:ouafae.n3");
        String nomDomaine ="http://www.lirmm.fr/region#";
     	String enteteDomaine = "PREFIX v: <"+nomDomaine+">" ;
         String enteteRdf = "PREFIX rdf: <"+RDF.getURI()+">" ;
        
         // n°1 : requete SPARQL : noms des communes et de leurs departements dappartenance         
         String queryString = enteteDomaine + NL + enteteRdf + NL +
        		 " SELECT  ?a ?d "
              	+ "WHERE { " +
              	"?a rdf:type v:Cog_R." +
              	"?a v:CodeDepC_R ?b." +
              	"?a v:nccenrC_R ?c." +
              	"?b v:nccenrD ?d." +
              	"}";
                  
       /* 
          // n°2 : requete SPARQL : noms des communes de la r´egion Languedoc-Roussillon
           String queryString = enteteDomaine + NL + enteteRdf + NL +
              " SELECT  ?c "
                 + "WHERE { " +
                 "?a rdf:type v:Cog_R." +
                  "?a v:CodeRegC_R ?b." +
                   "?a v:nccenrC_R ?c." +
                   "?b v:nccR ?d." +
                	"filter(?d =\"LANGUEDOC-ROUSSILLON\" )" +
                 "}";
       */
         
       /* 
         
        // n°3 : requete SPARQL : nombre de communes par departement
        String queryString = enteteDomaine + NL + enteteRdf + NL +
        		 " SELECT  ?dep (COUNT(?commune) AS ?nbreCom) "
               + "WHERE { " +
                  "?a rdf:type v:Cog_R." +
                  "?a v:CodeDepC_R ?b." +
                  "?a v:nccenrC_R ?commune." +
                  "?b v:nccenrD ?dep." +
                  "}" + "GROUP BY ?dep";
         */
         
         /* 
       
       // n°4 :requete SPARQL : nom des communes qui correspondent a des chef lieux de region
       String queryString = enteteDomaine + NL + enteteRdf + NL +
    		   " SELECT  ?commune ?chief"
                + " WHERE { " +
                 "?x rdf:type v:Cog_R." +
                 "?x v:CodeRegC_R ?b." +
                  "?b v:chiefLieuR ?chief." +
                  "?x v:nccC_R ?commune." +
                   	"}";
    		  */
         /*
       
       //n°5 :requete SPARQL : nom des localites soumises a l’ISF ainsi que le nom de leur departement et de leur region d’appartenance 
         String queryString = enteteDomaine + NL + enteteRdf + NL +
        		 " SELECT  ?l ?r ?d "
              	+ "WHERE { " +
              	"?i rdf:type v:Impot." +
              	"?i v:CodeInseeI ?b." +
              	"?b v:nccC_R ?l." +
              	"?b v:CodeRegC_R ?x." +
              	"?x v:nccR ?r." +
              	"?b v:CodeDepC_R ?y." +
              	"?y v:nccD ?d." +
              	"}"; 
              	*/                   
                  
         Query query = QueryFactory.create(queryString) ;
         // afficher la requete
         query.serialize(new IndentedWriter(System.out,true)) ;
         System.out.println() ;
       
         QueryExecution qexec = QueryExecutionFactory.create(query, d2rqModel) ;
       

         System.out.println("Les elements du modele : ") ;

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
