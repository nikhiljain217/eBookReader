import java.util.*;
import java.io.*;
import java.awt.event.*;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.concurrent.TimeUnit;
/*
 * 
 * This is a observer class for watchLibraryFolder subject.
 * This shows the books cover which is present in Library folder.
 * */

public class Library implements MyObserver{

	private String folderPath;
	private ArrayList<String> bookPaths;
	WatchLibraryFolder wLibraryFolder;
	ArrayList<JButton> bookButtonList;
	ReaderFactory rFactory;
	
	
	JPanel jPanel;
	private Logger log;
	JLabel label;
	MainWindow mWindow;
	
	
	//Constructor to initiate Library class
	Library(WatchLibraryFolder ws,MainWindow mw)
	{
		log = Logger.getInstance();
		log.info("Intializing the Library Class");
		this.wLibraryFolder = ws;
		this.wLibraryFolder.registerObserver(this);
		folderPath = new String("D:\\hw\\OOAD\\New folder");
		bookPaths = new ArrayList<String>();
		bookButtonList = new ArrayList<JButton>();
		rFactory = new ReaderFactory();
		jPanel = new JPanel(new GridLayout(0,4,5,5));
		this.mWindow = mw;
		initializeLibrary();
		
		
		
	}
	
	//Function to return corresponding panel
	public JPanel getPanel()
	{
		return jPanel;
	}
	
	//Function to add and set components of the Library Panel
	public void initializeLibrary()
	{
		
		addFilesToBookPath();
		setBookstoButtons();
		
	}
	
	//Function to add Files in the Folder to BookList
	public void addFilesToBookPath()
	{
		  String[] FileList = (new File(folderPath)).list();
		  
		  log.info("Adding books to library");
		  for(String file:FileList)
			  {bookPaths.add(folderPath+'\\'+file);
			  log.info("Book "+file+"is added to the list");
			  }
	}
	
	//Function to set the books cover page as button.
	public void setBookstoButtons()
	{
		try
		{
		log.info("setting books to buttons");
		for(int i=0;i<bookPaths.size();i++)
		{
			
			JButton button = addBookButton(bookPaths.get(i));
			addButtonToPanel(button);
		}
		}
		catch(Exception e)
		{
			log.info("Exception occured at setBookstoButton"+e);
		}
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
	
	//Function which adds button to the Panel
	public void addButtonToPanel(JButton btn)
	{
		jPanel.add(btn);
		
	}
	
	//Function to add New book button
	public JButton addBookButton(String path) throws IOException
	{
		
		
		log.info("Getting cover for the book");
		BufferedImage bim = (rFactory.createReader(path)).getCover();
		
		JButton button  = new JButton(new ImageIcon(bim));
		button.setPreferredSize(new Dimension(200, 300));
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		log.info("Setting actionlistener for the book");
		BookButtonListener bListener = new BookButtonListener(this.mWindow, path);
		
		button.addActionListener(bListener);
		bookButtonList.add(button);
		log.info("Button added to the book");
		
		return button;
	}
	
	//Function to remove the book from the list
	public void removeBookButton(String path)
	{
		int index = bookPaths.indexOf(path);
		JButton button = bookButtonList.get(index);
		jPanel.remove(button);
		bookButtonList.remove(button);
		
		
	}
	
	
	//update Function which is called when there is some new addition or deletion in the monitoring Folder;
	@Override
	public void update(WatchEvent<?> event) {
		
        
        // Context for directory entry event is the file name of entry
        WatchEvent<Path> ev = cast(event);
        log.info("Received new evernt from Watch Service");
        
        Path name = ev.context();
        log.info("Event: "+event.kind().name()+" for file "+name.toString());
        try {
        
		if(event.kind().name().equals("ENTRY_CREATE"))
		{
			TimeUnit.SECONDS.sleep(2);
			log.info("Adding book to buttonlist");
			bookPaths.add(folderPath+'\\'+name.toString());
			JButton button = addBookButton(folderPath+'\\'+name.toString());
				addButtonToPanel(button);
		}
		else if(event.kind().name().equals("ENTRY_DELETE"))
		{
			log.info("Removing book "+name.toString()+" to buttonlist");
			removeBookButton(folderPath+'\\'+name.toString());	
		}
		
		this.mWindow.reloadWindow();	
		
        System.out.format("%s: %s\n", event.kind().name(), name);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     
		
	}
	
	
	
}
