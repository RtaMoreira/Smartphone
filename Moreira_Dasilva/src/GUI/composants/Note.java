/**
* --------------------------------------------------------------------------<br/>
* Classe : Note <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : JPanel <br/>
* Description :	Cette classe contient les informations nécessaires pour une note <br/>
* 
* Remarques : en cours de développement
* --------------------------------------------------------------------------<br/>
*/

package GUI.composants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;

public class Note extends JPanel {

	private final static int CARAC_MAX_TEXTE = 400;
	private String titre;
	private Date date;
	private String texte;
	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
	
//	//TROUVER CURRETN DATE :
//	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//	Date date = new Date();
//	System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

	/**
	 * Constructeur 
	 * @param titre
	 * @param date
	 */
	public Note(String titre, Date date) {
		this.titre = titre;
		this.date = date;
		dateFormat.format(date);
		
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

}
