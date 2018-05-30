/**
 *	Exercise : smartphone Image.java
 *	Author : Rita Moreira
 *	Date : 7 mai 2018
 */

package GUI.composants;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/* 
 * Classe : IMAGE 
 * ---------------
 * Auteur : Rita Moreira
 * 
 * Description : Classe qui gère la classe Image de la Galerie photo
 */
public class Photo extends File{
	private String nom;
	
	private int width;
	private int height;
	private String location;
	private JButton bouton;
	
	
	/**
	 * Constructeur Image
	 * @param ID
	 * @param nom
	 */
	
	public Photo(String nom){
		super("image/image/"+nom);
		this.nom = nom;
		this.location = "image/image/"+nom;
	
		BufferedImage bimg;
			try {
				bimg = ImageIO.read(this);
				this.height = bimg.getHeight();
				this.width = bimg.getWidth();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	//Getters - Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	
	
	

	
}
