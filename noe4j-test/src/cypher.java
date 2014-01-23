import java.io.File;
import java.io.IOException;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

public class cypher
{
    private static final String DB_PATH = "/usr/local/neo4j-community-2.0.0/data/graph.db";
    private static ExecutionEngine engine; 
    private static ExecutionResult result ;
    private static GraphDatabaseService graphDb;
    
    

    public static void main( final String[] args )
    {
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
    	registerShutdownHook( graphDb ); 
    	engine = new ExecutionEngine(graphDb);
//    	 
//    	 try ( Transaction tx = graphDb.beginTx() )
//     	{
//     	    Node myNode = graphDb.createNode();
//     	    myNode.setProperty( "name", "my node" );
//     	    tx.success();
//     	    
//     	}
    	
    	try ( Transaction ignored = graphDb.beginTx() )
    	{
    	    result = engine.execute( "MATCH (tom {name: \"Tom Hanks\"}) RETURN tom ");
    	    System.out.println(result.dumpToString());
    	}
    

    
       
        
       
            
        	
        
        // END SNIPPET: transaction
    }

   
   

    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    // END SNIPPET: shutdownHook
}