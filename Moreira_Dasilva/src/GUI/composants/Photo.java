/**
 *	Exercise : smartphone Image.java
 *	Author : Rita Moreira
 *	Date : 7 mai 2018
 */

package GUI.composants;

import java.io.File;

/* 
 * Classe : IMAGE 
 * ---------------
 * Auteur : Rita Moreira
 * 
 * Description : Classe qui gère la classe Image de la Galerie photo
 */
public class Photo extends File{
	private String nom;
	private String location;
	
	
	/**
	 * Constructeur Image
	 * @param nom
	 */
	
	public Photo(String nom)
	{
		super("image/image/"+nom);
		this.nom = nom;
		this.location = "image/image/"+nom;
	
	}
	
	
	//Getters - Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	
	
	

	
}
