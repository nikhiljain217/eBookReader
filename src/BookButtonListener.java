import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
/**
 * 
 * A button listener which is invoke when user clicks on the book
 */
public class BookButtonListener implements ActionListener{
	MainWindow mWindow;
	String path;

	
	public BookButtonListener(MainWindow mw, String bookPath)
	{
		mWindow = mw;
		path = bookPath;
	}

	//This function add new card to cardLayout which is reader and change the screen.
	@Override
	public void actionPerformed(ActionEvent event) {
    mWindow.addCard("Reader",this.path);
    mWindow.changeScreen("Reader");
}
}
