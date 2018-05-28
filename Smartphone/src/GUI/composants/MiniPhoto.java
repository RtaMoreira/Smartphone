/**
 *	Exercise : GUI.composants miniPhoto.java
 *	Author : Rita Moreira
 *	Date : 25 mai 2018
 */

package GUI.composants;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MiniPhoto extends JButton {
	private String pathPhoto;
	private ImageIcon photoRedimensionne;
	
	public MiniPhoto(ImageIcon photoRedim, String pathPhoto) {
		super(photoRedim);
		this.setIcon(photoRedim);
		this.pathPhoto = pathPhoto;
		this.photoRedimensionne = photoRedim;
		
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	
	}

	public String getPathPhoto() {
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}

	public ImageIcon getPhotoRedimensionne() {
		return photoRedimensionne;
	}

	public void setPhotoRedimensionne(ImageIcon photoRedimensionne) {
		this.photoRedimensionne = photoRedimensionne;
	}
	
	
}
