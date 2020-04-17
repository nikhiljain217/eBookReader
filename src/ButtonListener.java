import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 

public class ButtonListener implements ActionListener{
	private solution MainWindow;

	public ButtonListener(solution mw)
	{
		this.MainWindow = mw;
	}

@Override
public void actionPerformed(ActionEvent event) {
    this.MainWindow.addCard();
    this.MainWindow.changescreen();
}
}
