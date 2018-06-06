/**
* TP Week2
*Author: Joao Silva
*Date creation : 20 avr. 2018
*/
package GUI.composants;

import java.awt.*;

import javax.swing.*;

public class ImagePanel extends JComponent 
{
    private ImageIcon image;
    
    public ImagePanel(ImageIcon image) 
    {
        this.image = image;
    }
 
    protected void paintComponent(Graphics g) 
    {
    	g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
