
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.Description;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.Traversal;
import org.neo4j.server.plugins.Name;
import org.neo4j.server.plugins.PluginTarget;
import org.neo4j.server.plugins.ServerPlugin;
import org.neo4j.server.plugins.Source;
import org.neo4j.tooling.GlobalGraphOperations;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
public class France {
	
	
	private static enum RelTypes implements RelationshipType
	{
	    DEPARTEMENT, VOISIN, REGION, EST
	}

	
	private long popNodeId=0;
	private long popNodeIddep=0;
	private static final String neo4j_DBPath="/usr/local/neo4j-community-2.0.0";
	public static GraphDatabaseService graphdbservice;
	

	public void createNodeSpace()
	{
		graphdbservice = new GraphDatabaseFactory().newEmbeddedDatabase(neo4j_DBPath);
	Transaction tx = graphdbservice.beginTx();
	try {
		
		
		Node France= graphdbservice.createNode(); // création du point d'accès au graphe
		popNodeId = France.getId();
		
		Node pays = graphdbservice.createNode(); // création du 2éme point 
	
		pays.setProperty("name", "pays"); 
		France.createRelationshipTo(pays, RelTypes.EST);
		
		Node LanguedocRoussillon = graphdbservice.createNode(); // création la branche de région 91
		
		LanguedocRoussillon.setProperty("code", "91");
		LanguedocRoussillon.setProperty("name", "LANGUEDOC-ROUSSILLON");// attribution du premier id
		Node lozere = graphdbservice.createNode(); // création d'un autre noeud
		lozere.setProperty("name", "LOZERE");
		lozere.setProperty("code", "48"); // property(code)
		lozere.setProperty("pop", "77 085"); // property(nombre de population)
		// création d'une relation entre l'entrée du graphe et la région LanguedocRoussillon
		LanguedocRoussillon.createRelationshipTo(lozere, RelTypes.DEPARTEMENT);
		Node herault = graphdbservice.createNode();
		herault.setProperty("name", "HERAULT");
		herault.setProperty("code", "34");
		herault.setProperty("pop", "1 090 052"); 
		lozere.createRelationshipTo(herault, RelTypes.VOISIN);
		
		Node gard = graphdbservice.createNode(); // création d'un autre noeud
		gard.setProperty("name", "GARD"); 
		gard.setProperty("code", "30"); 
		gard.setProperty("pop", "733 747"); 
		herault.createRelationshipTo(gard, RelTypes.VOISIN);
		Node aude = graphdbservice.createNode();
		aude.setProperty("name", "AUDE");
		aude.setProperty("code", "11");
		aude.setProperty("pop", "366 604");
		gard.createRelationshipTo(aude, RelTypes.VOISIN);
		
		Node pyrennesorientales= graphdbservice.createNode();
		pyrennesorientales.setProperty("name", "PYRENEES-ORIENTALES");
		pyrennesorientales.setProperty("code", "66");
		pyrennesorientales.setProperty("pop", "459 798");
		aude.createRelationshipTo(pyrennesorientales, RelTypes.VOISIN);
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		Node paysdelaloire = graphdbservice.createNode(); // création de la branche région 52
		paysdelaloire.setProperty("name", "PAYS DE LA LOIRE"); 
		paysdelaloire.setProperty("code", "52"); 
		Node maineetloire = graphdbservice.createNode(); // création d'un autre noeud
		maineetloire.setProperty("name", "MAINE-ET-LOIRE"); 
		maineetloire.setProperty("code", "49"); 
		maineetloire.setProperty("pop", "800 424"); 
		// création d'une relation entre l'entrée du graphe et maineetloire
		paysdelaloire.createRelationshipTo(maineetloire, RelTypes.DEPARTEMENT);
		Node vendee = graphdbservice.createNode();
		vendee.setProperty("name", "VENDEE");
		vendee.setProperty("code", "85");
		vendee.setProperty("pop", "657 326");
		maineetloire.createRelationshipTo(vendee, RelTypes.VOISIN);

		Node sarthe = graphdbservice.createNode();
		sarthe.setProperty("name", "SARTHE");
		sarthe.setProperty("code", "72");
		sarthe.setProperty("pop", "569 029");
		vendee.createRelationshipTo(sarthe, RelTypes.VOISIN);
	
		Node mayenne = graphdbservice.createNode();
		mayenne.setProperty("name", "MAYENNE");
		mayenne.setProperty("code", "53");
		mayenne.setProperty("pop", "309 168");
		sarthe.createRelationshipTo(mayenne, RelTypes.VOISIN);
		
		Node loireatlantique = graphdbservice.createNode();
		loireatlantique.setProperty("name", "LOIRE-ATLANTIQUE");
		loireatlantique.setProperty("code", "44");
		loireatlantique.setProperty("pop", "1 322 404");
		mayenne.createRelationshipTo(loireatlantique, RelTypes.VOISIN);
	
	//////////////////////////////////////////////////////////////////////////////////////////
		
		Node aquitaine = graphdbservice.createNode(); // création d'une autre région
		aquitaine.setProperty("name", "AQUITAINE");
		aquitaine.setProperty("code", "72");
		Node gironde = graphdbservice.createNode();
		gironde.setProperty("name", "GIRONDE"); 
		gironde.setProperty("code", "33"); 
		gironde.setProperty("pop", "1 491 170"); 
	
		aquitaine.createRelationshipTo(gironde, RelTypes.DEPARTEMENT);
		Node lotetgaronne = graphdbservice.createNode();
		lotetgaronne.setProperty("name", "LOT-ET-GARONNE");
		lotetgaronne.setProperty("code", "47");
		lotetgaronne.setProperty("pop", "333 569"); 
		gironde.createRelationshipTo(lotetgaronne, RelTypes.VOISIN);
		
		Node pyreneesatlantiques = graphdbservice.createNode(); // création d'un autre noeud
		pyreneesatlantiques.setProperty("name", "PYRENEES-ATLANTIQUES"); 
		pyreneesatlantiques.setProperty("code", "64"); 
		pyreneesatlantiques.setProperty("pop", "662 236"); 
		lotetgaronne.createRelationshipTo(pyreneesatlantiques, RelTypes.VOISIN);
		Node landes = graphdbservice.createNode();
		landes.setProperty("name", "LANDES");
		landes.setProperty("code", "40");
		landes.setProperty("pop", "19");
		pyreneesatlantiques.createRelationshipTo(landes, RelTypes.VOISIN);
		Node dordogne = graphdbservice.createNode();
		dordogne.setProperty("name", "DORDOGNE");
		dordogne.setProperty("code", "24");
		dordogne.setProperty("pop", "20");
		landes.createRelationshipTo(dordogne, RelTypes.VOISIN);
		pays.createRelationshipTo(LanguedocRoussillon, RelTypes.REGION);
		pays.createRelationshipTo(paysdelaloire, RelTypes.REGION);
		pays.createRelationshipTo(aquitaine, RelTypes.REGION);	
		
		//////////////////////////////////////////////////////////////////////////
		
		tx.success(); // 
	}
	 catch(Exception e){
		System.out.println(e.getMessage());
		tx.finish();
	}
	}
	
    
	private Node getFirstNode() {
		return graphdbservice.getNodeById(popNodeId)
		.getSingleRelationship( RelTypes.EST, Direction.OUTGOING)
		.getEndNode();
		}
	
	private static Traverser getVoisins(Node firstNode) {
		TraversalDescription td = Traversal.description()
		.breadthFirst()
		.relationships(RelTypes.REGION, Direction.OUTGOING)
		.evaluator(Evaluators.excludeStartPosition())
		.uniqueness(Uniqueness.NODE_GLOBAL);
		return td.traverse(firstNode);
		}
	
//////////////////////////////////////////////////
	
	public void printNeoVoisins(Model m) {
		String stat ="http://www.statistiques.fr#";
		m.setNsPrefix("statistiques", stat);
		Resource pays = m.createResource(stat+"pays");
		Property caracterisepar = m.createProperty(stat+"caracterisepar");
		
		
		
		Resource Pays = m.createResource(stat+"pays");
		m.add(Pays, RDF.type, pays);
		
		Node firstNode = getFirstNode();
		Node contient;
		String output = firstNode.getProperty("name") + "voisins :" +
		System.getProperty("line.separator");
		Traverser voisinsTraverser = getVoisins(firstNode); // noeuds traverses
		int nbOfFriends = 0; // compteur de voisins
		for (Path voisinPath : voisinsTraverser) {
			
			output = "Region " + voisinPath.length() + " => "
					+voisinPath.endNode().getProperty( "name" ) +
					System.getProperty("line.separator");
			System.out.println(output);
			Resource reg = m.createResource(stat+voisinPath.endNode().getProperty( "name" ));
			reg.addProperty(DC.title, voisinPath.endNode().getProperty( "name" ).toString());
			m.add(Pays, caracterisepar, reg);
			
			 
				long id=0;
				contient=voisinPath.endNode();
				id=contient.getId();			 
		
				System.out.println(printNeoVoisinsappartenant(id,m,reg));
				nbOfFriends++;
				
				}
		 m.write(System.out, "RDF/XML");
		 
			try {       
				FileOutputStream outStream = new FileOutputStream("statistiques.rdf");
			
				//exporte  dans un fichier
				m.write(outStream, "RDF/XML");
				outStream.close();
			}
			catch (FileNotFoundException e) {System.out.println("File not found");}
			catch (IOException e) {System.out.println("IO problem");}

		  }
	//////////////////////////////////////////////////////////////////////////////////////////////
	public String printNeoVoisinsappartenant(long i,Model m,Resource r) {
		String stat ="http://www.statistiques.fr#";
		m.setNsPrefix("statistiques", stat);
		Resource region = m.createResource(stat+"region");
		Resource departement = m.createResource(stat+"departement");
		Property departements = m.createProperty(stat+"departements");
		//Property code = m.createProperty(stat+"code");
		Property pop = m.createProperty(stat+"pop");
		Property name = m.createProperty(stat+"name");
		
		m.add(r,RDF.type,region);
		
		
		Node firstNode = getFirstNodeAppartenant(i);
		Node appartenant;
		
		String output = firstNode.getProperty("name") + "  voisins :" +
		System.getProperty("line.separator");
		Resource dep = m.createResource(stat+firstNode.getProperty("code"));
		
		//dep.addProperty(code , firstNode.getProperty("code").toString());
		dep.addProperty(name, firstNode.getProperty("name").toString());
		dep.addProperty(pop , firstNode.getProperty("pop").toString());
		m.add(r,departements,dep);
		m.add(dep,RDF.type,departement);
		Traverser voisinsTraverser = getVoisinsappartenant(firstNode); // noeuds traverses
		int nbOfFriends = 0; // compteur de voisins
		for (Path voisinPath : voisinsTraverser) {	
		
		output += "Departement " + voisinPath.length() + " => "
		+ voisinPath.endNode().getProperty( "name" ) +
		System.getProperty("line.separator");
		
		Resource departs = m.createResource(stat+voisinPath.endNode().getProperty("code"));
		//departs.addProperty(code, voisinPath.endNode().getProperty("code").toString());
		departs.addProperty(name, voisinPath.endNode().getProperty("name").toString());
		departs.addProperty(pop, voisinPath.endNode().getProperty("pop").toString());
		m.add(departs,RDF.type,departement);
		m.add(r,departements,departs);
		nbOfFriends++;
		
		}
		output += "Nombre de departements voisins est " + nbOfFriends;
		return output;
		}
	
////////////////////////////////////////////////////////////////////////////////////////////

private Node getFirstNodeAppartenant(long i) {
	return graphdbservice.getNodeById(i)
	.getSingleRelationship( RelTypes.DEPARTEMENT, Direction.OUTGOING)
	.getEndNode();
	}

private static Traverser getVoisinsappartenant(Node firstNode) {
	TraversalDescription td = Traversal.description()
	.breadthFirst()
	.relationships(RelTypes.VOISIN, Direction.OUTGOING)
	.evaluator(Evaluators.excludeStartPosition())
	.uniqueness(Uniqueness.NODE_GLOBAL);
	return td.traverse(firstNode);
	}
	
//////////////////////////////////////////////////////////////////////////////////
public static void main(String[] args) {
	
	//GetAll noeuds = new GetAll();

	//noeuds.getAllNodes(graphdbservice);
		
	France gr = new France();
	gr.getVoisinsappartenant(null);
	
}




}

////////////////////////////////////	
	



