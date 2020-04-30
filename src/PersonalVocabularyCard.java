/*
 * 
 * Class for third pane of the application to show personal Vocabulary
 * */
public class PersonalVocabularyCard extends AbstractCard {

	PersonalVocabularyCard(MainWindow mWindow)
	{
		super(mWindow);
		pageMap = dictionary.getPageMapDictionary();
		currentPage = new Integer(1);
		toolbar = new Toolbar(this,pageMap.size(),false);
		toolbar.disableHighlightButton();
		
		initilializePersonal();
		
		
	}
	
	//Initialize the Pane
	public void initilializePersonal()
	{
		showText();
		addComponentsToPanel();
	}

	
	//Function to move back to parent pane
	@Override
	public void switchToParent() {
		this.mWindow.changeScreen("Reader");
		this.mWindow.resetCard();
		
	}
}
