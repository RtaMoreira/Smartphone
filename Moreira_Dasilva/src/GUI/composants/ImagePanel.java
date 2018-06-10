/**
* ImagePanel
*Author: Joao Silva
*Date creation : 20 avr. 2018
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
	
    public ImagePanel(ImageIcon image) 
    {
        this.image = image;
    }
    
    public ImagePanel() {}
 
    public void paintComponent(Graphics g) 
    {
    	g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
    
    
    public void setImage() 
    {
    	this.image=new ImageIcon(recupBackground());
    }
    
    public void setImage(String image) {
    	this.image=new ImageIcon(image);
    }
    
    /**
     * M�thode qui r�cup�re le Path du fond d'�cran
     * 
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
	
	
	public File getSettingsInfo() 
	{
		return this.settingsInfo;
	}
	
	
}
