import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

public class BookButtonListener implements ActionListener{
	private MainWindow MainWindow;
	private String path;

	public BookButtonListener(MainWindow mw,String bookPath)
	{
		this.MainWindow = mw;
		this.path = bookPath
	}

@Override
public void actionPerformed(ActionEvent event) {
    this.MainWindow.addCard();
    this.MainWindow.changescreen();
}
}
