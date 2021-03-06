import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
public abstract class AbstractCard {
	Toolbar toolbar;
	
	JTextPane tarea;
	Reader reader;
	ReaderFactory rFactory;
	MainWindow mWindow;
	Logger log;
	JPanel cardPanel;
	Integer currentPage;
	public abstract void switchToParent();
	HashMap<Integer,String> pageMap;
	Dictionary dictionary;
	
	//Constructor to initiate Abstract Card
	
	AbstractCard(MainWindow mWindow)
	{
		
		log = Logger.getInstance();
		//Initializing textArea
		tarea = new JTextPane();
		
		dictionary = new Dictionary();
		tarea.setEditable(false);
		Border border = new LineBorder(Color.white,10);
		tarea.setCaretPosition(0);
		tarea.setPreferredSize( new Dimension( 600, 600 ) );
		tarea.setBorder(border);
		tarea.setBackground(Color.white);
		tarea.setForeground(Color.black);
		this.mWindow =mWindow;
		
		
		
		pageMap = new HashMap<Integer,String>(); 
	
		cardPanel = new JPanel();
		
		
		
	}
	
	//Function to get the Panel for respective pane
	public JPanel getPanel()
	{
		return cardPanel;
	}
	
	
	//Adding components to Panel
	public void addComponentsToPanel()
	{
		cardPanel.add(toolbar);
		cardPanel.add(tarea);
	}
	
	
	
	//Function to toggle Night Mode
	public void toggleNightMode(boolean enable)
	{
		if(enable)
		{
			log.info("Night Mode is enabled");
			tarea.setBackground(Color.black);
			tarea.setForeground(Color.white);
			Border border = new LineBorder(Color.black,10);
			tarea.setBorder(border);
		}
		
		else
		{
			log.info("Night Mode is disable");
			tarea.setBackground(Color.white);
			tarea.setForeground(Color.black);
			Border border = new LineBorder(Color.white,10);
			tarea.setBorder(border);
		}
	}
	
	
	//function to jump to Particular Page
	public void setPageNumber(int page)
	{
		log.info(String.format("Jumping to Page Number %d", page));
		currentPage=page;
		showText();
	}
	
	//Function to get the current Page number
	public int getCurrentPageNumber()
	{
		return currentPage; 
	}
	
	
	//Function to Highlight the searched string
	public void searchWord(String text)
	{
		Highlighter h = tarea.getHighlighter();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);
		h.removeAllHighlights();
		if(text.length()!=0)
		{
			String pageText=pageMap.get(currentPage).toLowerCase();
			int len = text.length();
			int i=0;
			while(i<pageText.length())
			{
				int offset = pageText.indexOf(text, i);
				if(offset==-1)
					break;
				try {
					h.addHighlight(offset, offset + len, painter);
				} catch (BadLocationException e) {
					log.info("Exception occurred while searching for a word "+e.getMessage());
					e.printStackTrace();
				}
				i=offset+1;
			}
			
		}
		
	}
	
	//Function to show the text in the pane.
	public void showText()
	{
		tarea.setText(pageMap.get(currentPage));
	}
	
}
