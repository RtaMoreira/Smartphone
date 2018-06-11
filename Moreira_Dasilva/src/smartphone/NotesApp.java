/**
* --------------------------------------------------------------------------<br/>
* Classe : NotesApp <br/>
* --------------------------------------------------------------------------<br/>
* Auteur: Rita Moreira <br/>
* Extension : AppTemplate <br/>
* Description : Cette classe gère l'application notes (à développer encore) <br/>
* --------------------------------------------------------------------------<br/>
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class NotesApp extends AppTemplate{
	//icon
	private ImageIcon notesIcon = new ImageIcon("image\\icon\\notes.png");
	private ImageIcon notesIconHover = new ImageIcon("image\\icon\\notesHOVER.png");
	
	//panel
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel notes = new JPanel(new GridLayout(7, 1)); //mettre 2 si ajoute checkmark
	
	
	/**
	 * Constructeur
	 * @author Rita Moreira
	 */
	public NotesApp() 
	{
		super("Notes", new Color(129,97,37));
		this.setBackground(new Color(246,237,178));
		
	}

	
	
	/**
	 * Getters & Setters
	 */
	public ImageIcon getNotesIcon() {
		return notesIcon;
	}

	public void setNotesIcon(ImageIcon notesIcon) {
		this.notesIcon = notesIcon;
	}

	public ImageIcon getNotesIconHover() {
		return notesIconHover;
	}

	public void setNotesIconHover(ImageIcon notesIconHover) {
		this.notesIconHover = notesIconHover;
	}
	
	
	

}
