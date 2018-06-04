/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Camera extends AppTemplate {

	private ImageIcon cameraIcon = new ImageIcon("image/icon/camera.png");
	private ImageIcon cameraIconHover = new ImageIcon("image/icon/cameraHOVER.png");
	
	public ImageIcon getCameraIcon() {
		return cameraIcon;
	}

	public ImageIcon getCameraIconHover() {
		return cameraIconHover;
	}

	public Camera() {
		super("Appareil Photo", Color.WHITE);
	}

}
