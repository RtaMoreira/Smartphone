/**
* --------------------------------------------------------------------------<br/>
* Classe : AppTemplate (abstract) <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JPanel <br/>
* Description : Classe abstraite gérant le template de base de chaque application <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GUI.composants.NavigationBar;


abstract class AppTemplate extends JPanel
{
	
	private NavigationBar Navigation;
	private JLabel appIcone;
	private JLabel appIconeHover;


	/**
	 * Constructeur
	 * @param nomApp : nom de l'app
	 * @param couleurNavig : couleur qu'aura l'en-tête de l'application
	 */
	public AppTemplate(String nomApp, Color couleurNavig) 
	{
		
		this.setLayout(new BorderLayout());
		Navigation = new NavigationBar(nomApp);
		Navigation.setBackground(couleurNavig);
		this.add(Navigation, BorderLayout.NORTH);

		this.setVisible(true);
	}

	
	//******** Getter & Setters *********//
	
	public NavigationBar getNavigation() 
	{
		return Navigation;
	}
	
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

