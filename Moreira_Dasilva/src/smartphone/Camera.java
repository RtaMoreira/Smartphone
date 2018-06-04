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

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

public class Camera extends AppTemplate {

	private ImageIcon cameraIcon = new ImageIcon("image/icon/camera.png");
	private ImageIcon cameraIconHover = new ImageIcon("image/icon/cameraHOVER.png");
	
	Webcam webcam = Webcam.getDefault();
	File dossier = new File ("images/image");
	File fichier=new File(dossier, "capture.jpg");

	JLabel label = new JLabel();
	JButton button = new JButton("capture");
	WebcamPanel cam =new WebcamPanel(webcam);
	
	public Camera() {
		super("Appareil Photo", Color.WHITE);
		cam=new WebcamPanel(webcam);
		webcam.setViewSize(new Dimension(320,240));
		add(cam);
		
		button.addActionListener(new Capture());
		super.getNavigation().getBackButton().addActionListener(new Close());
	}
	
	public void start() {
		cam =new WebcamPanel(webcam);
		webcam.open();
		remove(cam);
		add(cam);
	}
	
	public Webcam getWebcam() {
		return webcam;
	}
	
	public ImageIcon getCameraIcon() {
		return cameraIcon;
	}

	public ImageIcon getCameraIconHover() {
		return cameraIconHover;
	}
	class Capture implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				ImageIO.write(webcam.getImage(), "JPG", fichier);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	class Close implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			webcam.close();
		}
		
	}
}
