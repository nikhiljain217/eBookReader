import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

public class card2{

	JButton bPane21;
	JButton bPane23;
	JTextField SearchWord;
	public JPanel jp;
	public JPanel cards;
	solution MainWindow;
	
	public card2(MainWindow mWindow)
	{
		jp =new JPanel();
		bPane21 = new JButton("Pane-1");
    	bPane23 = new JButton("Pane-3");
    	SearchWord = new JTextField("Search word", 15);
    	jp.add(bPane21);
		jp.add(bPane23);
		jp.add(SearchWord);
		this.MainWindow = mWindow;
		
		
		
		bPane21.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindow.resetCard();
				CardLayout cardLayout = (CardLayout) MainWindow.getCards().getLayout();
				cardLayout.show(MainWindow.getCards(),"Library");
				
			}
		});
        
        bPane23.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) MainWindow.getCards().getLayout();
				cardLayout.show(MainWindow.getCards(),"Personal Voc");
				MainWindow.setPanelFrom("Reader");
				
			}
		});

		
    	
	}
	public void setCards(JPanel cards)
	{
		this.cards = cards;
		
	}
	
	public JPanel getPanel() {
		
			return jp;
			
		}
	
	
}
