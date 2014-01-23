
//package CreateNeo4j;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class testFrance {
	
	public static void main(String[] args) {

		Model m = ModelFactory.createOntologyModel();
		France gr = new France();
		gr.createNodeSpace();
		gr.printNeoVoisins(m);
		//m.read("statistiques.rdf");

	}
}


