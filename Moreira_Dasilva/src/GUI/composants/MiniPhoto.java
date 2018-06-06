/**
 *	Exercise : GUI.composants miniPhoto.java
 *	Author : Rita Moreira
 *	Date : 25 mai 2018
 */

package GUI.composants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MiniPhoto extends JButton {
	private String pathPhoto;
	private String nomPhoto;
	private ImageIcon photoRedimensionne;
	
	public MiniPhoto(ImageIcon photoRedim, String pathPhoto) {
		super(photoRedim);
		Dimension icon = new Dimension(135, 135);
		this.setPreferredSize(icon);
		this.setIcon(photoRedim);
		this.pathPhoto = pathPhoto;
		this.photoRedimensionne = photoRedim;
		this.nomPhoto = recupNom(pathPhoto);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	
	}
	
	public String recupNom(String path) {
		File f = new File(path);
		return f.getName();
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

	public String getNomPhoto() {
		return nomPhoto;
	}

	public void setNomPhoto(String nomPhoto) {
		this.nomPhoto = nomPhoto;
	}
	
	
	
	
}
