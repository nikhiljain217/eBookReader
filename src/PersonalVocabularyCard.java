
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
	
	public void initilializePersonal()
	{
		showText();
		addComponentsToPanel();
	}

	@Override
	public void switchToParent() {
		this.mWindow.changeScreen("Reader");
		this.mWindow.resetCard();
		
	}
}
