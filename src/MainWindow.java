import java.awt.event.*; 

import java.awt.*; 
import javax.swing.*; 
/*
 * 
 * 
 * This is mainWIndow which is responsible for creating Jframe and moving the screen between different panels
 * */

public class MainWindow{ 
	
	
	public JFrame frame;
	public JPanel jBookCard;
	public BookCard bookCard;
	public JPanel jPersonalCard;
	public JPanel libraryPanel;
	public Library library;
	public PersonalVocabularyCard personalCard;
	
	//public ButtonListener bl;
	public static String currentPanel;
	public static String panelFrom;
	public static JPanel cards;
	private Logger log; 
    // main class 
	public MainWindow(WatchLibraryFolder ws)
	{
		
		log = Logger.getInstance();
		log.info("Main Window initialized");
		panelFrom= new String();
		//bl = new ButtonListener(this);
	    frame = new JFrame("Book Reader");
	   	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());;
		library = new Library(ws,this);
		libraryPanel = library.getPanel();
		currentPanel = "Library";
		cards.add(libraryPanel,"Library");
		frame.getContentPane().add(cards);
		frame.pack();
		log.info("Changing to library Window");
		frame.setVisible(true);
	}
    
	
	//Function to reload the window in case of changes
	public void reloadWindow()
	{
		log.info("Reloading the window");
		frame.revalidate();
		frame.repaint();
	}
	
	
	//Function to remove the card from cardlayout
	public void resetCard()
  
    {
    	
    	if(currentPanel=="Reader")
    		{
    			log.info("Removing the card Reader from card Layout");
    			cards.remove(jBookCard);
    			
    		}
    	else
    	{
    		log.info("Removing the card Reader from card Layout");
    		cards.remove(jPersonalCard);
    		currentPanel="Reader";
    	}
    		
    }
    
	//Function to add the card when new panel is initiated
    public void addCard(String cardType, String bookPath)
    {
    	if(cardType=="Reader")
    	{	
    		bookCard = new BookCard(this,bookPath);
    		panelFrom ="Library";
    		currentPanel ="Reader";
    		jBookCard = bookCard.getPanel() ;
        	cards.add(jBookCard,"Reader");
    	}
    	else
    	{
    		personalCard = new PersonalVocabularyCard(this);
    		panelFrom="Reader";
    		currentPanel="PVocubalary";
    		jPersonalCard = personalCard.getPanel() ;
    		cards.add(jPersonalCard,"PVocubalary");
    	}
    			
    	
    }
    
    //Function to return card layout
    public JPanel getCards()
    {
    	return this.cards;
    }
    
    //Function which changes the screen 
    public void changeScreen(String screen)
    {
    	log.info("Changing screen to "+screen);
    	CardLayout cardLayout = (CardLayout) cards.getLayout();
    	cardLayout.show(cards, screen);
    }
    
    
} 