import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import org.sqlite.JDBC;


public class Database {

	private static Database db =null;
	private Logger log;
	private Connection conn;
	
	private Database()
	{
		this.log = Logger.getInstance();
	}
	private void connect() throws SQLException
	{
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:C:\\sqlite\\pdfDatabase.db";  
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
	
	public synchronized ResultSet selectQuery(String query)
	{
		ResultSet rs =null;
		try
		{
			connect();
			Statement stmt  = this.conn.createStatement();
			log.info("Executing the query: "+query);
			rs = stmt.executeQuery(query);
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
		return rs;
		
		
	}
	
	public synchronized boolean updateQuery(String query)
	{
		try
		{
			connect();
			
			Statement stmt  = this.conn.prepareStatement(query);
			log.info("Executing the query: "+query);
			stmt.executeUpdate(query);
			
			
			
			
			
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
		return true;
	}
		
	
	
	public synchronized int insertQuery(String query)
	{
		
		int record=0;
		try
		{
			this.connect();
			
			PreparedStatement stmt  = this.conn.prepareStatement(query);  
			log.info("Executing the query: "+query);
			stmt.executeUpdate();
			stmt = conn.prepareStatement("select last_insert_rowid();");
			ResultSet rs = stmt.executeQuery();
			record = rs.getInt(1);

			
			
			
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
	return record;	
	}
	
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
