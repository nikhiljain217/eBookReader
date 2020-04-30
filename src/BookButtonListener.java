import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

public class BookButtonListener implements ActionListener{
	MainWindow mWindow;
	String path;

	
	public BookButtonListener(MainWindow mw, String bookPath)
	{
		mWindow = mw;
		path = bookPath;
	}

@Override
public void actionPerformed(ActionEvent event) {
    mWindow.addCard("Reader",this.path);
    mWindow.changeScreen("Reader");
}
}
