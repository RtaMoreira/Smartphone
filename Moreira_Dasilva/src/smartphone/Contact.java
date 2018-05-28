/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.LineBorder;

/* a ajouter:
 * long click insert cercle dans la 3e case du panel et si on click il devient vert et bool selected devienttrue
 * 	apres le long click le bouton plus change et on met celui de la poubelle qui efface tous ceux que selected=true
 * 
 * set image change l'image de base en une choisie
 * avec un new galerie et ActionListenner dans chaque image pour l'ajouter au contact et fermer galerie
 * 
 * modify contact avec le new contact qui s'ouvre mais avec les champs remplis
 * 
 * tests si pas num valide ou champs obligatoires vides le textField devient rouge jusqu'a ce qu'on clique dessous
 */

public class Contact implements Serializable{

	String nom;
	String prenom;
	String numero;
	ImageIcon photo = new ImageIcon("image/icon/backicon.png");
	JPanel button=new JPanel();
	boolean selected = false;
	
	public Contact(String nom, String prenom, String numero) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		createButton();
	}

	private void createButton() {
		button.setLayout(new GridLayout(0,3));
		button.add(new JLabel(photo));
		button.add(new JLabel(nom+" "+prenom));
		button.setBackground(Color.WHITE);
		button.setBorder(new LineBorder(Color.GRAY));
		button.setPreferredSize(new Dimension(485, 55));
		button.setMinimumSize(new Dimension(485, 55));
		button.setMaximumSize(new Dimension(485, 55));
	}

	public void setbutton(JPanel button) {
		this.button=button;
	}
	
	public JPanel getbutton() {
		return button;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
}

