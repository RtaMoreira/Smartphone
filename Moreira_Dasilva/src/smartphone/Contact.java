/**
* Contact qui sera serializé
* @author jcfds
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Contact implements Serializable
{
	private String nom;
	private String prenom;
	private String natel;
	private String telephone;
	private String mail;
	private String adresse;
	private String photoPath;
	private transient JPanel button;
	private JLabel imageLabel = new JLabel();
	boolean selected = false;
	
	public Contact(String nom, String prenom, String natel, String telephone,String mail,String adresse, String photoPath) 
	{
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.natel = natel;
		this.telephone=telephone;
		this.mail=mail;
		this.adresse=adresse;
		this.photoPath=photoPath;
		createButton();
	}

	public void createButton() 
	{
		button=new JPanel();
		button.setLayout(new GridLayout(0,3));
		imageLabel.setIcon(new ImageIcon(photoPath));
		button.add(imageLabel);
		button.add(new JLabel(nom+" "+prenom));
		button.setBackground(Color.WHITE);
		button.setBorder(new LineBorder(Color.GRAY));
		button.setPreferredSize(new Dimension(485, 55));
		button.setMinimumSize(new Dimension(485, 55));
		button.setMaximumSize(new Dimension(485, 55));
	}

	public String getNom() 
	{
		return nom;
	}

	public String getPrenom() 
	{
		return prenom;
	}

	public String getNatel() 
	{
		return natel;
	}

	public String getTelephone() 
	{
		return telephone;
	}
	
	public JLabel getImageLabel() 
	{
		return imageLabel;
	}

	public String getMail() 
	{
		return mail;
	}

	public String getAdresse() 
	{
		return adresse;
	}

	public String getPhotoPath() 
	{
		return photoPath;
	}

	public JPanel getButton() 
	{
		return button;
	}

	
	
}

