/**
* --------------------------------------------------------------------------<br/>
* Classe : AppTemplate (abstract) <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JPanel <br/>
* Description : Classe g�rant l'en-t�te de chaque application. <br/> 
* Contient une bouton de retour (classe backButton) <br/>
* --------------------------------------------------------------------------<br/>
*/
package GUI.composants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class NavigationBar extends JPanel {
	
	private ImageIcon backImage = new ImageIcon("image\\icon\\backicon.png");
	private ImageIcon backImageHover = new ImageIcon("image\\icon\\backiconHover.png");
	
	private JLabel backIcon = new JLabel(backImage);
	private BackButton backButton = new BackButton();
	private JLabel titre;
	
	/**
	 * Constructeur
	 * @param nomApp
	 * @author Rita Moreira
	 */
	public NavigationBar(String nomApp) 
	{

		this.setSize(480, 300);
		this.setLayout(new BorderLayout());
		this.setBackground(Color.GRAY);
		
		this.add(backButton, BorderLayout.WEST);
		
		//Design font du nomApp
		titre = new JLabel(nomApp+"            ", SwingConstants.CENTER);
		titre.setFont(recupFont());				//R�cup�ration du Font
		this.add(titre, BorderLayout.CENTER);
		this.getBackButton().addMouseListener(new ReturnMenu());
		
		this.setVisible(true);
	}
	
	/**
	 * Classe : BackButton
	 * Extension : JButton
	 * Description : Classe qui g�re le bouton de retour
	 * @author Rita Moreira
	 *
	 */
	public class BackButton extends JButton
	{
		
		BackButton()
		{
			this.add(backIcon);
			
			//Supprimer les effets par d�faut bouton 
			this.setOpaque(false);
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			
			this.setVisible(true);
		}
		
		
		public void setImage(ImageIcon backImage) 
		{
			backIcon.setIcon(backImage);
		}

	}
	
	public  BackButton getBackButton() 
	{
		return backButton;
	}
	
	class ReturnMenu extends MouseAdapter
	{

		public void mouseClicked(MouseEvent e) 
		{
			getBackButton().setImage(backImage);

		}

		public void mouseEntered(MouseEvent e) 
		{
			getBackButton().setImage(backImageHover);
		}

		public void mouseExited(MouseEvent e) 
		{
			getBackButton().setImage(backImage);
		}

	}
	
	/** M�thode qui r�cup�re la police enregistr�e dans un File 
	 * (en String)
	 * @author Rita Moreira
	 * @return Font
	 */
	private Font recupFont() 
	{
		String font;
		BufferedReader br;
		Font newFont= new Font("Arial", 1, 25); //Par d�faut
		
		try 
		{
			FileReader fr;
			fr = new FileReader("serials/SettingsInfo.txt");
			br = new BufferedReader(fr);

			br.readLine();			//1�re ligne
			font = br.readLine();	//2�me ligne lue (info font)
			
			if(font != null)
			newFont = new Font(getNameFont(font),1,25);
			
			br.close();
			
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		return newFont;
	
	}
	
	/**
	 * M�thode qui r�cup�re le nom de la Font
	 * @author Rita Moreira 
	 * @param font
	 * @return nom de la Police String)
	 */
	private String getNameFont(String font) 
	{
		String name="";
		name= font.substring(font.indexOf("=") + 1);
		name= name.substring(0,name.indexOf(","));
		
		return name;
	}
	
	public void setTitreFont() 
	{
		this.titre.setFont(recupFont());
		this.updateUI();
	}

}
