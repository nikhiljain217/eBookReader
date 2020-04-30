import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.sqlite.JDBC;

/**
 * 
 * This is a singleton class 
 * This is main class which integrate direcctly with Sqlite database with JDBC driver
 *
 */

public class Database {

	private static Database db =null;
	private Logger log;
	private Connection conn;
	
	private Database()
	{
		this.log = Logger.getInstance();
	}
	
	//Function to connect to database
	private void connect() throws SQLException
	{
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:pdfDatabase.db";  
	        // create a connection to the database  
	        conn = DriverManager.getConnection(url);  
	        this.conn.setAutoCommit(true);
	        log.info("Connection to SQLite has been established.");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.info("Exception occur while connecting to database:  "+e.getMessage());
			System.out.println("Exception occur while connecting to database "+e.getMessage());
			
		}
		
        
	}
	
	//To get instance of database, making sure sinlge instance is created.
	public synchronized static Database getInstance()
	{
		if(db==null)
			db = new Database();
		return db;
	}
	
	private void disconnect()
	{
		
		if(this.conn!=null)
			try {
				this.conn.close();
			} catch (SQLException e) {
				log.info("Exception occur  while disconnecting "+e.getMessage());
				System.out.println("Exception occur while disconnecting "+e.getMessage());
			}
	}
	
	//Function to run Select query and return the results
	public synchronized ArrayList<ArrayList<Object>> selectQuery(String query)
	{
		
		Statement stmt =null;
		ArrayList<ArrayList<Object>> results =new ArrayList<ArrayList<Object>>();
		try
		{
			connect();
			stmt  = this.conn.createStatement();
			log.info("Executing the query: "+query);
			ResultSet rs = stmt.executeQuery(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			
			
			
			while(rs.next())
			{
				ArrayList<Object> singleTuple = new ArrayList<Object>();
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					singleTuple.add(rs.getObject(i));
				}
				results.add(singleTuple);
			}
			
			
			stmt.close();
			this.disconnect();
			
			
		}
		catch(Exception e)
		{
			
			log.info("Exception occur at selectQuery "+e.getMessage());
			System.out.println("Exception occur at selectQuery "+e.getMessage());
			
			
		}
		finally
		{
			this.disconnect();
			
		}
		return results;
		
		
	}
	
	//Function to run update Query on the database
	public synchronized boolean updateQuery(String query)
	{
		try
		{
			connect();
			
			PreparedStatement stmt  = this.conn.prepareStatement(query);
			log.info("Executing the query: "+query);
			stmt.executeUpdate();
			stmt.close();
			
			
			
			
		}
		catch(Exception e)
		{
			
			log.info("Exception occur at updateQuery "+e.getMessage());
			System.out.println("Exception occur at updateQuery "+e.getMessage());
			
			
		}
		finally
		{
			this.disconnect();
		}
		return true;
	}
		
	
	
	//Function which runs insertquery on the database
	public synchronized int insertQuery(String query)
	{
		
		int record=0;
		try
		{
			this.connect();
			
			PreparedStatement stmt  = this.conn.prepareStatement(query);  
			log.info("Executing the query: "+query);
			System.out.println("Executing the query: "+query);
			stmt.executeUpdate();
			stmt = conn.prepareStatement("select last_insert_rowid();");
			ResultSet rs = stmt.executeQuery();
			record = rs.getInt(1);
			stmt.close();
			
			
			
		}
		catch(Exception e)
		{
			
			log.info("Exception occur at updateQuery "+e.getMessage());
			System.out.println("Exception occur at updateQuery "+e.getMessage());
		}
		finally
		{
		this.disconnect();
		}
	return record;	
	}
	
	//Function which run delete query on database.
	public void deleteQuery(String query)
	{
		try
		{
			connect();
			PreparedStatement stmt  = this.conn.prepareStatement(query);
			log.info("Executing the query: "+query);
			stmt.executeUpdate();
			
			
		}
		catch(Exception e)
		{
			
			log.info("Exception occur at updateQuery "+e.getMessage());
			System.out.println("Exception occur at selectQuery "+e.getMessage());
		}
		finally
		{
			this.disconnect();
		}
		
	}
	
	
}
