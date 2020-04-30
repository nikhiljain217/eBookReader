import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 
 * Class which interact with Database class to get and manipulate data for Book and snippet table
 *
 */

public class Book {

	
	private Database db;
	private Logger log;
	Book()
	{
		log = Logger.getInstance();
		db = Database.getInstance();
	}
	
	
	//To check if the book is present in the Database
	public ArrayList<ArrayList<Object>> checkBookInDatabase(String bookPath)
	{
		
		String query = String.format("Select * from books where path=\"%s\"",bookPath);
		
		System.out.println("Executing this query "+query);
		log.info("Executing this query -"+query);
		return db.selectQuery(query);
	}
	
	// To add the book in Database if it is not present
	public int addBookToDatabse(String bookPath)
	{
		String query = String.format("Insert into Books(path) values(\"%s\")",bookPath);
		System.out.println("Executing this query "+query);
		log.info("Executing this query -"+query);
		return db.insertQuery(query);
	}
	
	
	// To get the bookmark of the book opened
	public int getBookmark(String bookPath)
	{
		ArrayList<ArrayList<Object>> results = checkBookInDatabase(bookPath);
		int bookmark=2;
		try {
			if(results.size()==0)
			{
				log.info("Book is not present in database");
				log.info("Lets add the book to database");
				addBookToDatabse(bookPath);
				return bookmark;
			}
				bookmark = (Integer) results.get(0).get(2);
			} catch (Exception e) {
			
			log.info("Exception occur in getBookmark "+e.getMessage());
			System.out.println("Exception occur in getBookMark "+e.getMessage());
		}
		return bookmark;
	}
	
	
	// To set the bookmark when user click on back button
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
	
	//To get the bookid from the database
	public int getBookID(String bookPath)
	{
		ArrayList<ArrayList<Object>> results = checkBookInDatabase(bookPath);
		int bookId=0;
		try {
		
			bookId = (Integer) results.get(0).get(0);
		
			
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		return bookId;
		
	}
	
	
	// To add new snippet for a book from the Database
	public void addSnippet(String bookPath,int offset,int length,int pageNumber)
	{
		
		int bookId =getBookID(bookPath);
		
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Adding new snippet to the database");
			String query = String.format("Insert into Snippet(page,offset,length,book_id) values(%d,%d,%d,%d)",pageNumber,offset, length, bookId );
			db.insertQuery(query);
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		
	}
	
	//To get Snippet List for the book from the Database
	public ArrayList<int[]> getSnippetList(String bookPath)
	{
		int bookId =getBookID(bookPath);
		ArrayList<int[]> SnippetList = new ArrayList<int[]>();
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Getting SnippetList for bookPath "+bookPath);
			
			String query = "Select page, offset, length from Snippet where book_id = "+bookId;
			ArrayList<ArrayList<Object>> results = db.selectQuery(query);
			
			for(int i=0;i<results.size();i++)
			{
				int[] local = new int[3];
				local[0]= (Integer) results.get(i).get(0);
				local[1]=(Integer) results.get(i).get(1);
				local[2]=(Integer) results.get(i).get(2);
				SnippetList.add(local);
			}
			
		} catch (Exception e) {
			log.info("Exception occur while gettingSnippetList  "+e.getMessage());
			System.out.println("Exception occur while gettingSnippetList "+e.getMessage());
		}
		return SnippetList;
	}
	
	// To remove Snippet from the book from the database
	public void removeSnippet(String bookPath,int offset, int length,int pageNumber)
	{
		int bookId =getBookID(bookPath);
		
		try {
			if(bookId==0)
				throw new IllegalStateException("The book is not added in the Database");
			log.info("Deleting snippet from the database");
			
			String query = String.format("Delete from Snippet where book_id = %d and offset = %d and length = %d and page= %d",bookId,offset,length,pageNumber);
			
			db.deleteQuery(query);
		} catch (Exception e) {
			log.info("Exception occur while getting BookID  "+e.getMessage());
			System.out.println("Exception occur while getting BookID "+e.getMessage());
		}
		
	}
	
	
	
	
	
}
