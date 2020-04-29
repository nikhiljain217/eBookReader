import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book {

	
	private Database db;
	private Logger log;
	Book()
	{
		log = Logger.getInstance();
		db = Database.getInstance();
	}
	
	public ResultSet checkBookInDatabase(String bookPath)
	{
		String query = String.format("Select * from where path=\"%s\"",bookPath);
		return db.selectQuery(query);
	}
	
	public int addBookToDatabse(String bookPath)
	{
		String query = String.format("Insert into Books values(\"5s\")",bookPath);
		return db.insertQuery(query);
	}
	
	
	
	public int getBookmark(String bookPath)
	{
		ResultSet rs = checkBookInDatabase(bookPath);
		int bookmark=1;
		try {
			if(rs.next()==false)
			{
				log.info("Book is not present in database");
				log.info("Lets add the book to database");
				addBookToDatabse(bookPath);
				return bookmark;
			}
				bookmark=rs.getInt(3);
			} catch (SQLException e) {
			
			log.info("Exception occur in Book constructor "+e.getMessage());
			System.out.println("Exception occur in Book constructor "+e.getMessage());
		}
		return bookmark;
	}
	
	public void setBookmark(String bookPath, int bookmark)
	{
		String query = String.format("Update Books set bookmark= %d where path=\"%s\"",bookmark,bookPath);
		
		if(db.updateQuery(query))
		{
			log.info("Bookmark set for the book");
		}
		else
			System.out.println("Bookmark did not set for the book.");
		
	}
	public int getBookID(String bookPath)
	{
		ResultSet rs = checkBookInDatabase(bookPath);
		int bookId=0;
		try {
			bookId =  rs.getInt(1);
		} catch (SQLException e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		return bookId;
		
	}
	
	public void addSnippet(String bookPath,int offset,int length)
	{
		
		int bookId =getBookID(bookPath);
		
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Adding new snippet to the database");
			String query = String.format("Insert into Snippet(offset,length,book_id) values(%d,%d,%d)", offset, length, bookId);
			db.insertQuery(query);
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		
	}
	
	public ArrayList<int[]> getSnippetList(String bookPath)
	{
		int bookId =getBookID(bookPath);
		ArrayList<int[]> SnippetList = new ArrayList<int[]>();
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Getting SnippetList for bookPath "+bookPath);
			
			String query = "Select offset,length from Snippet where book_id = "+bookId;
			ResultSet rs = db.selectQuery(query);
			while(rs.next())
			{
				int[] local = new int[2];
				local[0]=rs.getInt(2);
				local[1]=rs.getInt(3);
				SnippetList.add(local);
			}
			
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		return SnippetList;
	}
	
	public void removeSnippet(String bookPath,int offset, int length)
	{
		int bookId =getBookID(bookPath);
		
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Deleting snippet from the database");
			
			String query = String.format("Delete from Snippet where bookId = %d and offset = %d and length = %d",bookId,offset,length);
			
			db.deleteQuery(query);
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		
	}
	
	
	
	
	
}
