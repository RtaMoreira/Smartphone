/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Settings extends AppTemplate {

	private ImageIcon settingsIcon = new ImageIcon("image/icon/reglage.png");
	private ImageIcon settingsIconHover = new ImageIcon("image/icon/reglageHOVER.png");
	
	public ImageIcon getSettingsIcon() {
		return settingsIcon;
	}

	public ImageIcon getSettingsIconHover() {
		return settingsIconHover;
	}

	public Settings() {
		super("Parametres", Color.WHITE);
	}

}
