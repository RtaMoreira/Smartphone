/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Meteo extends AppTemplate {

	private ImageIcon meteoIcon = new ImageIcon("image/icon/meteo.png");
	private ImageIcon meteoIconHover = new ImageIcon("image/icon/meteoHOVER.png");
	
	public Meteo() {
		super("meteo", Color.WHITE);
	}

	public ImageIcon getMeteoIcon() {
		return meteoIcon;
	}

	public ImageIcon getMeteoIconHover() {
		return meteoIconHover;
	}

}
