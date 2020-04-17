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


public class Library implements MyObserver{

	private String folderPath;
	private ArrayList<String> bookPaths;
	WatchLibraryFolder wLibraryFolder;
	ArrayList<JButton> bookButtonList;
	ReaderFactory rFactory;
	JPanel mainLayout;
	
	JPanel jPanel;
	
	JLabel label;
	MainWindow mWindow;
	
	
	
	Library(WatchLibraryFolder ws,MainWindow mw)
	{
		this.wLibraryFolder = ws;
		this.wLibraryFolder.registerObserver(this);
		folderPath = new String("D:\\hw\\OOAD\\New folder");
		bookPaths = new ArrayList<String>();
		bookButtonList = new ArrayList<JButton>();
		rFactory = new ReaderFactory();
		mainLayout = new JPanel();
		label = new JLabel("Library");
		label.setFont(new Font("Serif", Font.BOLD, 25));
		mainLayout.add(label);
		jPanel = new JPanel(new GridLayout(0,4,5,5));
		this.mWindow = mw;
		initializeLibrary();
		
		
		
	}
	
	public JPanel getPanel()
	{
		return mainLayout;
	}
	
	public void initializeLibrary()
	{
		
		addFilesToBookPath();
		setBookstoButtons();
		mainLayout.add(jPanel);
	}
	
	public void addFilesToBookPath()
	{
		  String[] FileList = (new File(folderPath)).list();
		  
		  for(String file:FileList)
			  bookPaths.add(folderPath+'\\'+file);
	}
	
	public void setBookstoButtons()
	{
		try
		{
		for(int i=0;i<bookPaths.size();i++)
		{
			
			JButton button = addBookButton(bookPaths.get(i));
			addButtonToPanel(button);
		}
		}
		catch(Exception e)
		{
			System.out.println("Exception occured at setBookstoButton"+e);
		}
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
	
	public void addButtonToPanel(JButton btn)
	{
		jPanel.add(btn);
		
	}
	public JButton addBookButton(String path) throws IOException
	{
		
		
		BufferedImage bim = (rFactory.createReader(path)).getCover();
		
		JButton button  = new JButton(new ImageIcon(bim));
		button.setPreferredSize(new Dimension(200, 300));
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
		bookButtonList.add(button);
		
		
		return button;
	}
	
	public void removeBookButton(String path)
	{
		int index = bookPaths.indexOf(path);
		JButton button = bookButtonList.get(index);
		jPanel.remove(button);
		bookButtonList.remove(button);
		
		
	}
	
	@Override
	public void update(WatchEvent<?> event) {
		
        
        // Context for directory entry event is the file name of entry
        WatchEvent<Path> ev = cast(event);
        Path name = ev.context();
        try {
        
		if(event.kind().name().equals("ENTRY_CREATE"))
		{
			TimeUnit.SECONDS.sleep(2);
			bookPaths.add(folderPath+'\\'+name.toString());
			JButton button = addBookButton(folderPath+'\\'+name.toString());
				addButtonToPanel(button);
		}
		else if(event.kind().name().equals("ENTRY_DELETE"))
		{
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
