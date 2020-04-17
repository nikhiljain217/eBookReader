import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
public class MainWindow{ 
	
	
	public JFrame frame;
	public JPanel bookCard;
	public JPanel personalCard;
	public JPanel libraryPanel;
	public Library library;
	//public ButtonListener bl;
	public static String currentPanel;
	public static String Panelfrom;
	public static JPanel cards;
    // main class 
	public MainWindow(WatchLibraryFolder ws)
	{
		Panelfrom= new String();
		//bl = new ButtonListener(this);
	    frame = new JFrame("Book Reader");
	   	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new JPanel(new CardLayout());;
		library = new Library(ws,this);
		libraryPanel = library.getPanel();
		cards.add(libraryPanel,"Library");
		frame.getContentPane().add(cards);
		frame.pack();
		frame.setVisible(true);
	}
    
	
	public void reloadWindow()
	{
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
    public void resetCard()
    {
    	
    	
    	cards.remove(bookCard);
    	
    }
    
    public void addCard()
    {
    	card2 cd2 = new card2(this);
    	bookcard = cd2.getPanel() ;
    	cards.add(bookcard,"Reader");
    }
    
    public JPanel getCards()
    {
    	return this.cards;
    }
    public void changescreen()
    {
    	CardLayout cardLayout = (CardLayout) cards.getLayout();
    	cardLayout.show(cards,"Reader");
    }
    */
    
} 