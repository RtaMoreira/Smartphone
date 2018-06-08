/**
 *	Exercise : GUI.composants AppTemplate.java
 *	Author : Rita Moreira
 *	Date : 14 mai 2018
 */

package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;




abstract class AppTemplate extends JPanel
{
	
	private JPanel App = new JPanel();
	private String nomApp;
	private NavigationBar Navigation;
	private JLabel appIcone;
	private JLabel appIconeHover;


	
	public AppTemplate(String nomApp, Color couleurNavig) {
		
		this.setLayout(new BorderLayout());
		Navigation = new NavigationBar(nomApp);
		Navigation.setBackground(couleurNavig);
		this.add(Navigation, BorderLayout.NORTH);

		this.setVisible(true);
	}

	public NavigationBar getNavigation() {
		return Navigation;
	}
	
	public void setNom(String nom) {
		this.nomApp = nom;
	}
	
	
	
	
	
	/*********** Barre de Navigation ********/
	
	public class NavigationBar extends JPanel
	{
		

		private ImageIcon backImage = new ImageIcon("image\\icon\\backicon.png");
		private ImageIcon backImageHover = new ImageIcon("image\\icon\\backiconHover.png");
		
		private JLabel backIcon = new JLabel(backImage);
		private BackButton backButton = new BackButton();
		private JLabel titre;
		
		public NavigationBar(String nomApp) {

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
		
		
		public class BackButton extends JButton
		{
			
			BackButton(){
				this.add(backIcon);
				
				//Supprimer les effets par d�faut bouton 
				this.setOpaque(false);
				this.setContentAreaFilled(false);
				this.setBorderPainted(false);
				
				this.setVisible(true);
			}
			
			
			public void setImage(ImageIcon backImage) {
				backIcon.setIcon(backImage);
			}

		}
		
		public  BackButton getBackButton() 
		{
			
			return backButton;
		}
		
		class ReturnMenu implements MouseListener
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				getNavigation().getBackButton().setImage(backImage);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				getNavigation().getBackButton().setImage(backImageHover);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				getNavigation().getBackButton().setImage(backImage);

			}

			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

		}
		

		public void setTitreFont() 
		{
			this.titre.setFont(recupFont());
			this.updateUI();
		}
	}
	
	/** M�thode qui r�cup�re la font enregistr�e dans un File
	 * 
	 * @return Font
	 */
	
	public Font recupFont() 
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
	
	
	private String getNameFont(String font) 
	{
		String name="";
		name= font.substring(font.indexOf("=") + 1);
		name= name.substring(0,name.indexOf(","));
		
		return name;
	}

	
	//Getters Setters
	public JLabel getAppIcone() 
	{
		return appIcone;
	}

	public void setAppIcone(JLabel appIcone) 
	{
		this.appIcone = appIcone;
	}

	public JLabel getAppIconeHover() 
	{
		return appIconeHover;
	}

	public void setAppIconeHover(JLabel appIconeHover) 
	{
		this.appIconeHover = appIconeHover;
	}





}

