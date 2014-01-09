import java.sql.*;


public class EmpDescription {
	

	public static void main(String[] args) 
	throws SQLException
		{
      try {
		 Class.forName("oracle.jdbc.driver.OracleDriver");
		 
	      }
      catch (ClassNotFoundException e)
	  {System.err.println("Erreur de chargement du driver "+e);}
      Connection c = null;
      ResultSet rset = null;
      Statement stmt = null;
     try {
    String url = "jdbc:oracle:thin:@ouafae-HP-620:1521:XE";
     c =
    DriverManager.getConnection (url, "ouafae","cocacola"); 
    		  
    stmt =  c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
    		ResultSet.CONCUR_READ_ONLY); 
    rset = stmt.executeQuery ("select *"
				           + " from REGION");
    //cheflieu varchar2(5), tncc varchar2(1), ncc varchar2(30), nccenr varchar2(30)
    ResultSetMetaData rsetSchema = rset.getMetaData();
    int nbCols = rsetSchema.getColumnCount();
    for (int i=1; i<=nbCols;i++)
    { 
    	System.out.print(rsetSchema.getColumnName(i)+ " | ");
    }
    System.out.println();
    System.out.println("-------------------------------");
    while (rset.next ())
    {
    	for (int i=1; i<=nbCols;i++)
    	{
    		System.out.print(rset.getObject(i)+ " | ");;
    	}
    	 System.out.println();
    }
   if (!rset.isAfterLast())
    rset.afterLast(); rset.previous();
   System.out.println("------------------------------");
   System.out.println("nbre de tuples dans la table "+rset.getRow());
   System.out.println("------------------------------");
     }
     catch (Exception e) {
				      System.err.println("Erreur SQL "+e);
				    }


     finally { rset.close();
			    stmt.close();
			    c.close();
			    }
		}

}
