/**
 *	Exercise : smartphone NotesApp.java
 *	Author : Rita Moreira
 *	Date : 5 juin 2018
 */

package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class NotesApp extends AppTemplate{
	//icon
	private ImageIcon notesIcon = new ImageIcon("image\\icon\\galerie.png");
	private ImageIcon notesIconHover = new ImageIcon("image\\icon\\galerieHOVER.png");
	
	//panel
	private JPanel mainPanel = new JPanel(new BorderLayout());
	private JPanel notes = new JPanel(new GridLayout(7, 1)); //mettre 2 si ajoute checkmark
	
	public NotesApp() 
	{
		super("Notes", new Color(129,97,37));
		this.setBackground(new Color(246,237,178));
		
		
		
	}

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
