/**
 *	Exercise : GUI.composants Resizable.java
 *	Author : Rita Moreira
 *	Date : 31 mai 2018
 */

package GUI.composants;

import java.awt.Image;

import javax.swing.ImageIcon;

public interface Resizable {

	/**
	 * M�thode pour changer la taille d'une image en Icon (format carr�)
	 * @param taille
	 * @param photoOriginal
	 * @return
	 */
	public static ImageIcon resizePhotoIcon(int taille, ImageIcon photoOriginal){
		
		Image monImage = photoOriginal.getImage();
		Image newimg = monImage.getScaledInstance(taille, taille, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

		ImageIcon imageIcon = new ImageIcon(newimg); 

		return imageIcon;
		
	}
	
	/**
	 * M�thode qui change la taille d'une image en gardant ses proportions.
	 * 
	 * @param width
	 * @param height
	 * @param widthMax = largeur maximale que peut avoir l'image
	 * @param heightMax = longueur maximale que peut avoir l'image
	 * @param photoOriginal
	 * @return
	 */
	public static ImageIcon resizePhotoRatio(int widthMax, int heightMax, ImageIcon photoOriginal){
	
		// Test si taille de l'image d�passe pas les 480 et 800
					double hPhoto = photoOriginal.getIconHeight();
					double wPhoto = photoOriginal.getIconWidth();
					ImageIcon imageResized;
					double ratio;

					while (hPhoto > heightMax || wPhoto > widthMax) {

						if (wPhoto > widthMax) {
							// Reconvertir la taille selon la taille de l'�cran (ratio)
							ratio = widthMax / wPhoto; // ratio qu'on doit garder pour changer la hauteur
							wPhoto = widthMax;
							hPhoto = hPhoto * ratio;
						} else {
							ratio = heightMax / hPhoto;
							hPhoto = heightMax;
							wPhoto = wPhoto * ratio;
						}

					}
					Image monImage = photoOriginal.getImage(); // transform it
					Image newimg = monImage.getScaledInstance((int) wPhoto, (int) hPhoto, java.awt.Image.SCALE_SMOOTH);
					imageResized = new ImageIcon(newimg);
					
		return imageResized;
		
	}
		
	
} 
