import java.io.*;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
/**
 * 
 * 
 *A class which is extending JPanel.
 *This is the top toolbar of the application
 */
public class Toolbar extends JPanel{

	public JToggleButton bPersonalVocabulary;
	public JToggleButton bHighlight;
	public JToggleButton bNightMode;
	public JTextField tPageNumber; 
	public JLabel lMaxPageNumber;
	public AbstractCard card;
	public JButton bParent;
	public JTextField tSearchBox;
	public JButton bPrevious;
	public JButton bNext;
	Logger log;
	boolean isBookCard;
	int totalPage;
	
	Toolbar(AbstractCard card, int totalPageNumber,boolean isBookCard)
	{
		log =Logger.getInstance();
		log.info("Initializing the toolbar class");
		this.isBookCard = isBookCard;
		if(this.isBookCard)
			bPersonalVocabulary = new JToggleButton("Personal Vocabulary");
		else
			bPersonalVocabulary = new JToggleButton("Personal Vocabulary",true);
		bHighlight = new JToggleButton("Highlight");
		tPageNumber = new JTextField(4);
		bNightMode = new JToggleButton("Night Mode");
		totalPage = totalPageNumber;
		
		lMaxPageNumber = new JLabel("/ "+String.valueOf(totalPageNumber));
		bParent =new JButton("Back");
		tSearchBox = new JTextField(20);
		FlowLayout layout = (FlowLayout)this.getLayout();
		bPrevious = new JButton("Previous");
		bNext = new JButton("Next");
		
		layout.setVgap(0);
		
		this.card = card;
		tPageNumber.setText(String.valueOf(this.card.getCurrentPageNumber()));
		this.setLayout(layout);
		log.info("Adding Listeners for the toolbar");
		addListeners();
		log.info("Adding Component to Toolbar");
		addComponents();
		
		
	}
	
	
	//Function to add the components to toolbar panel
	public void addComponents()
	{
		this.add(tSearchBox);
		this.add(bParent);
		this.add(bPersonalVocabulary);
		this.add(bHighlight);
		this.add(bNightMode);
		this.add(tPageNumber);
		this.add(lMaxPageNumber);
		this.add(bPrevious);
		this.add(bNext);
		
		
	}
	
	
	//Function to add listener to various panel
	public void addListeners()
	{
		
		//Listener to jump to page
		tPageNumber.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent event) {
						int page = Integer.parseInt(tPageNumber.getText());
						
						card.setPageNumber(page);
						if(bHighlight.isSelected()&&isBookCard)
							((BookCard) card).showHideSnippet(true);
						if(tSearchBox.getText().length()!=0)
							card.searchWord(tSearchBox.getText());
						
				}
				}
				);
		
		//Listener to go back to parent pane
		bParent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				log.info("Pressed back button - Switching to Parent");
				card.switchToParent();
			}
		});
		//Listener to jump to previous page
		bPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int page =card.getCurrentPageNumber();
				int StartingPage =2;
				if(!isBookCard)
					StartingPage =1;
					
				if(page==StartingPage)
					{
					
					log.info(String.format("Cant move to previous page , pdf has page %d",page));
					return;
					}
				
				else
					{
					
					log.info(String.format("Changing to previous page %d",page-1));
					card.setPageNumber(page-1);
					tPageNumber.setText(String.valueOf(page-1));
					if(isBookCard&&bHighlight.isSelected())
						((BookCard) card).showHideSnippet(true);
					if(tSearchBox.getText().length()!=0)
						card.searchWord(tSearchBox.getText());
					
					}
			}
		});
		
		//Listener for jumping to next page
		bNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int page =card.getCurrentPageNumber();
				if(page==totalPage)
					{
					
					log.info(String.format("Cant move next , last page %d",page));
					return;
					
					
					}
				else
				
					{log.info(String.format("Changing to next page %d",page+1));
					card.setPageNumber(page+1);
					tPageNumber.setText(String.valueOf(page+1));
					if(bHighlight.isSelected()&&isBookCard)
						((BookCard) card).showHideSnippet(true);
					if(tSearchBox.getText().length()!=0)
						card.searchWord(tSearchBox.getText());
					}
			}
		});
		
		//Listener fore enabling/disabling night mode
		bNightMode.addItemListener(new ItemListener()
				{
				
				public void itemStateChanged(ItemEvent itemEvent)
				{
					 int state = itemEvent.getStateChange();
					 
					 if (state == ItemEvent.SELECTED)
						 card.toggleNightMode(true);
						 
					 else
						 card.toggleNightMode(false);
					
				}
				}
				
				);
		
		//Listener for enabling/disabling snippet highlight
		bHighlight.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent itemEvent)
			{
				 int state = itemEvent.getStateChange();
				 
				 if (state == ItemEvent.SELECTED)
					 ((BookCard) card).showHideSnippet(true);
					 
				 else
					 ((BookCard) card).showHideSnippet(false);
				
			}
		
		});
		
		//Listener to search word in the text
		 tSearchBox.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				card.searchWord(tSearchBox.getText());
				
			}
		});
		 
		 //Listener for moving to personal vocabulary page
		 bPersonalVocabulary.addItemListener(new ItemListener() {
				 
			 public void itemStateChanged(ItemEvent itemEvent)
				{
				 int state = itemEvent.getStateChange();
				
				 
				 if (state == ItemEvent.SELECTED)
					 {
					 ((BookCard) card).switchToVocab();
					 bPersonalVocabulary.setSelected(false);
					 
					 }
					 
				 else
					 {
					 if(!isBookCard)
					 {	log.info("Pressed back button - Switching to Parent");
					 	card.switchToParent();
					 }
					 }
		 
		 
				}
		 }
	);
		
		
		
		
	}
	
	//Function to disable higlight button
	public void disableHighlightButton()
	{
		bHighlight.setEnabled(false);
	}
	
	
	
	
	
}
