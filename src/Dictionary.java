import java.util.*;

public class Dictionary {

	Database db;
	Logger log;
	Dictionary()
	{
		db  = Database.getInstance();
		log = Logger.getInstance();
	}
	
	public String getWordMeaning(String word)
	{
		String query = String.format("Select * from Dictionary where word= \"%s\"",word);
		String meaning = new String();
		log.info("Getting word meaning");
		ArrayList<ArrayList<Object>> results = db.selectQuery(query);
		
		if(results.size()==0)
		{
			meaning = "Cannot find";
		}
		else
		{
			meaning = (String) results.get(0).get(2);
		}
		
		return meaning;
		
	}
	
	public void addToPDictionary(String word){
		
		String query =String.format("Update Dictionary set ispv=%d where word =\"%s\" ",1,word); 
		if(db.updateQuery(query))
		{
			log.info("Added the word for Personal Dictionary");
		}
		else
			System.out.println("Could not set the word for personal Dictionary");
		
	}
	
	public HashMap<Integer,String> getPageMapDictionary()
	{
		String query = String.format("Select word, meaning from Dictionary where ispv=1");
		HashMap<Integer,String> pageMap = new HashMap<Integer,String>();
		ArrayList<ArrayList<Object>> results = db.selectQuery(query);
		int pageNumber=1;
		String pageText=new String();
		System.out.println(results.size());
		int i;
		for(i=0;i<results.size();i++)
			{
			pageText+=results.get(i).get(0)+": "+results.get(i).get(1)+"\n";
			if(i%36==0&&i!=0)
				{
				pageMap.put(pageNumber,pageText);
				pageText="";
				pageNumber++;
				}
			
			}
		if(pageText!="")
			pageMap.put(pageNumber,pageText);
		
			
		
		return pageMap;
	}

	
	
	
	
}
