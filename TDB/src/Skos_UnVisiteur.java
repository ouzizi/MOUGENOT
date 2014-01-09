import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFVisitor;
import com.hp.hpl.jena.rdf.model.Resource;


public class Skos_UnVisiteur implements RDFVisitor {
	public Object visitBlank(Resource r, AnonId id) {
	System.out.println("anon: " + id);
	return null;
	}
	public Object visitURI(Resource r, String uri) {
	System.out.println("qualified name : " + r.getLocalName());
	return null;
	}
	public Object visitLiteral(Literal l) {
	System.out.println(l);
	return null;
	}
	
	}