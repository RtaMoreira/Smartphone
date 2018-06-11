/**
* --------------------------------------------------------------------------<br/>
* Classe : MiniPhoto <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JButton <br/>
* Description : Classe de JButton contenant l'icon d'une image <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MiniPhoto extends JButton {
	
	private String pathPhoto;
	private String nomPhoto;
	private ImageIcon photoRedimensionne;
	
	/**
	 * Constructeur
	 * @param photoRedim : Image redimensionnée en petit
	 * @param pathPhoto : chemin de l'image
	 * @author Rita Moreira
	 */
	public MiniPhoto(ImageIcon photoRedim, String pathPhoto) 
	{
		super(photoRedim);
		
		this.pathPhoto = pathPhoto;
		this.photoRedimensionne = photoRedim;
		this.nomPhoto = recupNom(pathPhoto);
		
		Dimension iconMax = new Dimension(150, 150);
		this.setPreferredSize(iconMax);
		this.setIcon(photoRedim);
		
		//suppression du style bouton
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setBorder(getBorder());
		
	}
	
	
	/**
	 * Récupère le nom de l'image depuis son chemin
	 * @param path
	 * @return String
	 */
	public String recupNom(String path) 
	{
		File f = new File(path);
		return f.getName();
	}

	public String getPathPhoto() 
	{
		return pathPhoto;
	}

	public void setPathPhoto(String pathPhoto) 
	{
		this.pathPhoto = pathPhoto;
	}

	public ImageIcon getPhotoRedimensionne() 
	{
		return photoRedimensionne;
	}

	public void setPhotoRedimensionne(ImageIcon photoRedimensionne) 
	{
		this.photoRedimensionne = photoRedimensionne;
	}

	public String getNomPhoto() 
	{
		return nomPhoto;
	}

	public void setNomPhoto(String nomPhoto) 
	{
		this.nomPhoto = nomPhoto;
	}

}
