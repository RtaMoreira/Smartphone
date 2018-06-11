/**
* --------------------------------------------------------------------------<br/>
* Interface : Resizable <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Description : Interface qui permet de redimensionner des images <br/>
* --------------------------------------------------------------------------<br/>
*/

package GUI.composants;

import java.awt.Image;
import javax.swing.ImageIcon;

public interface Resizable {

	/**
	 * Méthode pour changer la taille d'une image en Icon (format carré)
	 * @param taille
	 * @param photoOriginal
	 * @return image redimensionnée
	 */
	public static ImageIcon resizePhotoIcon(int taille, ImageIcon photoOriginal)
	{
		Image monImage = photoOriginal.getImage();
		Image newimg = monImage.getScaledInstance(taille, taille, java.awt.Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(newimg); 
		return imageIcon;
		
	}
	
	/**
	 * Méthode qui change la taille d'une image en gardant ses proportions.
	 * @param widthMax = largeur maximale que peut avoir l'image
	 * @param heightMax = longueur maximale que peut avoir l'image
	 * @param photoOriginal
	 * @return image redimensionnée
	 */
	public static ImageIcon resizePhotoRatio(int widthMax, int heightMax, ImageIcon photoOriginal)
	{
	
		// Test si taille de l'image dépasse pas les widthMax ou heightMax
			double hPhoto = photoOriginal.getIconHeight();
			double wPhoto = photoOriginal.getIconWidth();
			ImageIcon imageResized;
			double ratio;
			
			while (hPhoto > heightMax || wPhoto > widthMax) 
			{

				if (wPhoto > widthMax) 
				{
					// Reconvertir la taille selon la taille de l'écran (ratio)
					ratio = widthMax / wPhoto; // ratio qu'on doit garder pour changer la hauteur
					wPhoto = widthMax;
					hPhoto = hPhoto * ratio;
				}
				else 
				{
					ratio = heightMax / hPhoto;
					hPhoto = heightMax;
					wPhoto = wPhoto * ratio;
				}

			}
				Image newimg = photoOriginal.getImage().getScaledInstance((int) wPhoto, (int) hPhoto, java.awt.Image.SCALE_SMOOTH);
				imageResized = new ImageIcon(newimg);
					
		return imageResized;
		
	}
		
	
} 
