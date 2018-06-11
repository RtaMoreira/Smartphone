/**
* --------------------------------------------------------------------------<br/>
* Classe : ImagePanel <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JPanel <br/>
* Description : Classe crée pour la gestion des fonds d'écran <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class ImagePanel extends JPanel 
{
    private ImageIcon image;
	private File settingsInfo = new File("serials/SettingsInfo.txt");
	
	/**
	 * Contructeur
	 * @param image
	 * @author Rita Moreira
	 */
    public ImagePanel(ImageIcon image) 
    {
        this.image = image;
    }
    
    public ImagePanel() {}
 
    
    public void paintComponent(Graphics g) 
    {
    	g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
    
    
    /**
     * Méthode qui récupère le Path du fond d'écran dans un fichier txt
     * @author Rita Moreira
     * @return String
     */
	public String recupBackground() 
	{
		String backgroundPath="";
		try 
		{
			FileReader fr;
			fr = new FileReader(settingsInfo);
			BufferedReader br = new BufferedReader(fr);

			backgroundPath = br.readLine();
		} catch (IOException e) 
			{e.printStackTrace();}
	
		return backgroundPath;
	}

	/**
	 * Getters & Setters	
	 */
    public void setImage() 
    {
    	this.image=new ImageIcon(recupBackground());
    }
    
    public void setImage(String image) {
    	this.image=new ImageIcon(image);
    }
	
	
	public File getSettingsInfo() 
	{
		return this.settingsInfo;
	}
		
	
}
