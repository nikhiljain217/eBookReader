import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.awt.Font.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;



public class BookCard extends AbstractCard{
	
	
	Book book;
	
	JButton bAddHighlight;
	JButton bRemoveHighlight;
	JPanel popupHighlightPanel;
	Popup pHighlight;
	Popup pMeaning;
	PopupFactory pf;
	ArrayList<int[]> SnippetList;
	JLabel lMeaning;
	JPanel popupMeaningPanel; 
	JButton bAddToPersonalVocabulary;
	BookCard(MainWindow mWindow, String bookPath)
	{
		super(mWindow);
		log.info("Initialzing the bookcard");
		
		
		//Creating Book Instance
		this.book =new Book();
		
		this.bookPath = bookPath;
		//Initializing Text area
		
		pf = new PopupFactory();
		
		SnippetList =new ArrayList<int[]>();
		bAddHighlight = new JButton("Add to Highlight");
		bAddHighlight = new JButton("Remove to Highlight");
		rFactory = new ReaderFactory();
		reader = rFactory.createReader(bookPath);
		
		currentPage = book.getBookmark(bookPath);
		
		bAddToPersonalVocabulary = new JButton("Add");
		toolbar = new Toolbar(this,reader.getTotalPageNumber(),true);
		
		
		
		
		pageMap= reader.getText();
		lMeaning =new JLabel();
		popupHighlightPanel = new JPanel(); 
		bAddHighlight = new JButton("Add to Highlight");
		bRemoveHighlight = new JButton("Remove from Highlight");
		popupHighlightPanel.add(bAddHighlight);
		popupHighlightPanel.add(bRemoveHighlight);
		
		
		popupMeaningPanel =new JPanel();
		popupMeaningPanel.add(lMeaning);
		popupMeaningPanel.add(bAddToPersonalVocabulary);
		
		
		initializeBookCard();
				
	}
	
	
	public void initializeBookCard()
	{
		addComponentsToPanel();
		addListeners();
		showText();
	}
	
	public void addListeners()
	{
		
		// Listener to get the popup for Word Meaning or adding/Removing data from Snippet.
		tarea.addMouseListener(new MouseAdapter() 
				{
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e)
			{
				System.out.println("Mouse Release event happened ");
				System.out.println(tarea.getSelectedText());
				if (tarea.getSelectedText() != null) {
					String selectedString = tarea.getSelectedText();
					
					boolean singleWord = selectedString.matches("\\w+");
					
					if(singleWord)
					{
						String meaning = dictionary.getWordMeaning(selectedString);
						if(meaning!="Cannot find")
							bAddToPersonalVocabulary.setEnabled(true);
						else
							bAddToPersonalVocabulary.setEnabled(false);
						lMeaning.setText(meaning);
						
						pMeaning = pf.getPopup(cardPanel, popupMeaningPanel, 180, 100);
						pMeaning.show();
					}
					else
					{
						System.out.println("Will show the popup");
						pHighlight = pf.getPopup(cardPanel, popupHighlightPanel, 180, 100);
						pHighlight.show();
					}
					
				}
				else
				{
					if(pMeaning != null)
						
						{pMeaning.hide();
							
						}
					if(pHighlight != null)
						pHighlight.hide();
				}
				
				}
			}
				
				
				);
		
		//Action Listener for Adding to snippet.
		bAddHighlight.addActionListener(new ActionListener()
				{
				
			@Override
			public void actionPerformed(ActionEvent event) {
				String selectedtext = tarea.getSelectedText().toLowerCase();
				String pageText = pageMap.get(currentPage).toLowerCase();
				int offset = pageText.indexOf(selectedtext);
				int length = selectedtext.length();
				book.addSnippet(bookPath,offset, length, currentPage);
				pHighlight.hide();
				
			}
				});
		
		
		bRemoveHighlight.addActionListener(new ActionListener()
		{
		
			@Override
			public void actionPerformed(ActionEvent event) {
				String selectedtext = tarea.getSelectedText().toLowerCase();
				String pageText = pageMap.get(currentPage).toLowerCase();
				int offset = pageText.indexOf(selectedtext);
				int length = selectedtext.length();
				book.removeSnippet(bookPath,offset, length, currentPage);
				pHighlight.hide();
				showHideSnippet(true);
		
			}
		});
		
		bAddToPersonalVocabulary.addActionListener(new ActionListener()
		{
		
			@Override
			public void actionPerformed(ActionEvent event) {
				dictionary.addToPDictionary(tarea.getSelectedText());
		
			}
		});
		
		
		
	}
	
	public void switchToParent()
	{
		
		log.info("Setting the bookmark");
		book.setBookmark(bookPath, currentPage);
		
		this.mWindow.changeScreen("Library");
		this.mWindow.resetCard();
	}
	
	
	
	/*public void addComponentsToPanel()
	{
		cardPanel.add(toolbar);
		cardPanel.add(tarea);
	}*/
	
	
	
	
	/*public void setPageNumber(int page)
	{
		log.info(String.format("Jumping to Page Number %d", page));
		currentPage=page;
		showText();
	}*/
	
	public void showHideSnippet(boolean enable)
	{
		SnippetList = book.getSnippetList(bookPath);
		Highlighter h = tarea.getHighlighter();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);
		
		if(enable)
		{
			h.removeAllHighlights();
			for(int i=0;i<SnippetList.size();i++)
			{
				if(SnippetList.get(i)[0]==currentPage)
				{
					int offset = SnippetList.get(i)[1];
					int length = SnippetList.get(i)[2];
					try {
						h.addHighlight(offset, offset+length, painter);
					} catch (BadLocationException e) {
						// 
						log.info("Exception occurred while adding Highlights"+e.getMessage());
						e.printStackTrace();
					}
				}
					
					
			}
		}
		else
		{
			h.removeAllHighlights();
		}
				

		}
	
	public void switchToVocab()
	{
		this.mWindow.addCard("PVocubalary","");
		this.mWindow.changeScreen("PVocubalary");
		
	}
	
	
	

	
	
	

}
