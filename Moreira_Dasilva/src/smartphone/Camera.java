/**
* TP Week2
*Author: Joao Silva
*Date creation : 4 juin 2018
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
//Copyright (C) 2012 - 2015 Bartosz Firyn (https://github.com/sarxos)

public class Camera extends AppTemplate 
{
	private ImageIcon cameraIcon = new ImageIcon("image/icon/camera.png");
	private ImageIcon cameraIconHover = new ImageIcon("image/icon/cameraHOVER.png");
	private ImageIcon capture = new ImageIcon("image/icon/cam.png");
	private JPanel mainPanel=new JPanel();
	
	private Webcam webcam = Webcam.getDefault();
	private File dossier = new File ("image/image");

	private JLabel label = new JLabel();
	private JButton button = new JButton("capture");
	private WebcamPanel cam =new WebcamPanel(webcam);
	
	//panel sud avec bouton et access galerie
	private JPanel south = new JPanel(new BorderLayout());
	
	public Camera() 
	{
		super("Appareil Photo", Color.WHITE);
		
		cam=new WebcamPanel(webcam);
		webcam.setViewSize(new Dimension(320,240));
		
		JButton button = new JButton(capture);
		button.addActionListener(new Capture());
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		south.add(button, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(south, BorderLayout.AFTER_LAST_LINE);
		south.setBackground(Color.BLACK);
		
		add(mainPanel);

		super.getNavigation().getBackButton().addActionListener(new Close());
	}
	
	public void start() 
	{
		cam =new WebcamPanel(webcam);
		webcam.open();
		mainPanel.remove(cam);
		mainPanel.add(cam,BorderLayout.CENTER);
	}
	
	public Webcam getWebcam() 
	{
		return webcam;
	}
	
	public ImageIcon getCameraIcon() 
	{
		return cameraIcon;
	}

	public ImageIcon getCameraIconHover() 
	{
		return cameraIconHover;
	}
	
	class Capture implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			int numero=1;
			String nom="capture"+numero+".jpg";
			File list[]=dossier.listFiles();

			for (int i = 0; i < list.length; i++) {
				if (list[i].getName().indexOf(nom.substring(0,nom.indexOf("e")+1))!=-1) {
					if (numero<=Integer.parseInt(list[i].getName().substring(7,list[i].getName().indexOf("."))))
						numero=Integer.parseInt(list[i].getName().substring(7,list[i].getName().indexOf(".")))+1;
				}
			}
			
			nom="capture"+numero+".jpg";
			File fichier=new File(dossier, nom);
			GalerieApp app = new GalerieApp();
			app.createAddMiniIcon(dossier+"/"+nom);
			
			try {
				ImageIO.write(webcam.getImage(), "JPG", fichier);
			} catch (IOException e) 
				{e.printStackTrace();}
		}
	}
	
	class Close implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			webcam.close();
		}
	}
}
