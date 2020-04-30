import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
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
    
	
	public void reloadWindow()
	{
		log.info("Reloading the window");
		frame.revalidate();
		frame.repaint();
	}
	
	/*
    public void GUI()
    {

   	 String BUTTONPANEL = "Card with JButtons";
   	 String TEXTPANEL = "Card with JTextField";
   	 JButton bPane12 = new JButton("Pane-2");  
   	 JButton bPane13 = new JButton("Pane-3");
   	JPanel card1 = new JPanel();
   	
   	
   	card1.add(bPane12);
   	card1.add(bPane13);
   	
   	//card2 cd2 = new card2(this);
   	//bookcard = cd2.getPanel();
   	JPanel card3 = new JPanel();
   	JButton bParent = new JButton("Parent");
   	card3.add(bParent);
   	
   	
       cards.add(card1, "Library");
       //cards.add(bookcard, "Reader");
       cards.add(card3, "Personal Voc");
   	
   	
   	frame.getContentPane().add(cards);
       bPane12.addActionListener(bl);
       
       bPane13.addActionListener(new ActionListener() {
    	   	
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards,"Personal Voc");
				Panelfrom = "Library";
			}
		});
       
       
       bParent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards,Panelfrom);
			}
		});
       
       frame.pack();
       frame.setVisible(true); 

    }
    
    public void setPanelFrom(String pf)
    {
    	this.Panelfrom = pf; 
    }
    /*
     * 
     * 
     * 
     */
	
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
    
    public JPanel getCards()
    {
    	return this.cards;
    }
    public void changeScreen(String screen)
    {
    	log.info("Changing screen to "+screen);
    	CardLayout cardLayout = (CardLayout) cards.getLayout();
    	cardLayout.show(cards, screen);
    }
    
    
} 