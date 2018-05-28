/**
 *	Exercise : GUI.composants ShowPanel.java
 *	Author : Rita Moreira
 *	Date : 25 mai 2018
 */

package GUI.composants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import smartphone.GalerieApp;
import smartphone.GalerieApp.*;

public class ShowPanel extends JPanel {
	
	private JLabel delete = new JLabel(new ImageIcon("image/icon/delete.png"));
	private JLabel quit = new JLabel(new ImageIcon("image/icon/crossBL.png"));
	private GalerieApp galerieTest;

	
	
	public ShowPanel(){
		super();
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(000,000,000,250));
		
		
		//Création partie gestion (supprimer, quitter aperçu)
		JPanel gestion = new JPanel(new FlowLayout(FlowLayout.CENTER, 170, 5));
		gestion.setBackground(Color.CYAN);
		
		

		delete.addMouseListener(new Options());
		quit.addMouseListener(new Options());
		
		//Panel qui contient la croix pour quitter aperçu 
		JPanel quitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		quitPanel.setOpaque(false);
		quitPanel.add(quit);


		gestion.add(delete);
		
		add(quitPanel, BorderLayout.NORTH);
		add(gestion,BorderLayout.SOUTH);
	}
	
	class Options implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JLabel event = (JLabel) e.getSource();
	
			if (event == getDelete()) 
			{
							
			}else if (event == getQuit()) {
//       		event.getClass().getDeclaredMethod("changePanel", null);	
//    		event.setAccessible(true);
//    		System.out.println(m.invoke(p));
               }
                // using cardLayout next() to go  to next panel
			}
		

		public void mouseEntered(MouseEvent e) {
			JLabel event = (JLabel) e.getSource();
			if (event == getDelete())
				getDelete().setIcon(new ImageIcon("image/icon/deleteHOVER.png"));
		}

		public void mouseExited(MouseEvent e) {
			JLabel event = (JLabel) e.getSource();
			if (event == getDelete())
				getDelete().setIcon(new ImageIcon("image/icon/delete.png"));

		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
	}	


	public JLabel getDelete() {
		return delete;
	}
	public void setDelete(JLabel delete) {
		this.delete = delete;
	}
	public JLabel getQuit() {
		return quit;
	}
	public void setQuit(JLabel quit) {
		this.quit = quit;
	}
	
	
	

}
