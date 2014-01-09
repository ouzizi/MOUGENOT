import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class mapping {
        public static final String NL = System.getProperty("line.separator");

        public static void main(String[] args) {
        String directory = "/home/ouafae/Documents/Mougenotprojet";
         Dataset ds = TDBFactory.createDataset(directory);
         Model mInsee = ds.getNamedModel("geo");
         Model mGeo = ds.getNamedModel("gn");
         mInsee.add(mGeo);
         Model mMap = ds.getNamedModel("mapping");
         mInsee.add(mMap);

         // Requête INSEE + geonames
        String q = "PREFIX rdf: <" + RDF.getURI() + ">" + NL +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + NL +
                        "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + NL +
                        // INSEE
                        "PREFIX geo: <http://rdf.insee.fr/def/geo#>" + NL +
                        // Geonames
                        "PREFIX gn: <http://www.geonames.org/ontology#>" + NL +
                        "SELECT ?insee_nom_region ?gn_nom_region WHERE {" + NL +
                        " ?s rdf:type geo:Region;" + NL +
                        " geo:nom ?insee_nom_region ." + NL +
                        " ?s2 gn:featureCode gn:A.ADM1;" + NL +
                        " gn:name ?gn_nom_region ." + NL +
                        " FILTER(SAMETERM(?insee_nom_region, ?gn_nom_region)) }" + NL +
                        "ORDER BY ?insee_nom_region LIMIT 50";
        
         // Requête geonames (attention pas de départements)
        /*String q = "PREFIX rdf: <" + RDF.getURI() + ">" + NL +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + NL +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" + NL +
                "PREFIX gn: <http://www.geonames.org/ontology#>" + NL +
                "SELECT ?nom_dep ?nom_region WHERE {" + NL +
                " ?d gn:name ?nom_dep;" +
                " gn:parentADM1 ?region ." +
                " ?region gn:name ?nom_region }" + NL +
                "ORDER BY ?nom_region";*/
        
         Query query = QueryFactory.create(q);
        QueryExecution qexec = QueryExecutionFactory.create(query, mInsee);
        try {
                System.out.println("Requete INSEE + Geonames");
                ResultSet results = qexec.execSelect();
         ResultSetFormatter.out(results);
        }
        finally {
         qexec.close() ;
         ds.close();
        }
        }

}