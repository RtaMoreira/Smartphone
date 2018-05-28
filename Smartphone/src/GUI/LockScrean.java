/**
* TP Week2
*Author: Joao Silva
*Date creation : 7 mai 2018
*/
package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.composants.ImagePanel;
public class LockScrean extends JPanel{
	
	private ImagePanel lock = new ImagePanel(new ImageIcon("image/background/samsung.jpg"));
	private ImagePanel codeInput = new ImagePanel(new ImageIcon("image/background/cat-background.jpg"));
	private JPanel clavier = new JPanel (new GridLayout(4,3));
	
	GridBagConstraints c = new GridBagConstraints();
	protected CardLayout cardLayout = new CardLayout();
	private JPanel cards= new JPanel(cardLayout);
	int cpt=0;
	private JButton ok = new JButton("ok");
	JTextField code = new JTextField();
	
	DateFormat hhmm = new SimpleDateFormat("HH:mm");
	DateFormat dmy = new SimpleDateFormat("dd MMMM yyyy");
	JLabel heure = new JLabel();
	Timer timer = new Timer(0,new CurrentTime());
	
	JLabel date = new JLabel();
	Font font = new Font("myFont", 10, 20);
	JPanel datePanel = new JPanel(new GridLayout(2,1));
	JLabel swipe = new JLabel("Swipe to unlock");
	
	public LockScrean() {
		setLayout(new BorderLayout());
		timer.start();
		add(cards);
		
	//ajouter les trucs dans l'écran vérouillé et le MouseLListenner
		lock.setLayout(new BorderLayout());
		datePanel.setOpaque(false);
		
		heure.setFont(font);
		heure.setForeground(Color.WHITE);
		heure.setHorizontalAlignment(SwingConstants.CENTER);
		datePanel.add(heure);
		
		date.setFont(font);
		date.setForeground(Color.WHITE);
		date.setHorizontalAlignment(SwingConstants.CENTER);
		datePanel.add(date);
		

		lock.add(datePanel,BorderLayout.PAGE_START);
		
		swipe.setFont(font);
		swipe.setForeground(Color.WHITE);
		swipe.setHorizontalAlignment(SwingConstants.CENTER);
		lock.add(swipe, BorderLayout.PAGE_END);
		lock.setBorder(new EmptyBorder(40, 0, 30, 0));
		lock.addMouseMotionListener(new Drag());
		
//	clavier
		for (int i = 0; i < 9; i++) {
			JLabel j = new JLabel(String.valueOf(i+1));
			j.setForeground(Color.WHITE);
			j.setFont(font);
			
			j.addMouseListener(new addNb());
			clavier.add(j);
		}
		JLabel retour = new JLabel("<");
		retour.setFont(font);
		retour.setForeground(Color.WHITE);
		retour.addMouseListener(new addNb());
		clavier.add(retour);
		
		JLabel zero = new JLabel("0");
		zero.setFont(font);
		zero.setForeground(Color.WHITE);
		zero.addMouseListener(new addNb());
		clavier.add(zero);
		
		clavier.add(ok);
		
		clavier.setOpaque(false);
		
	//change codeInput
		codeInput.setLayout(new GridBagLayout());
		c.gridx=0;
		c.gridy=0;
		code.setOpaque(false);
		code.setPreferredSize(new Dimension(200, 30));
		code.setHorizontalAlignment(SwingConstants.CENTER);
		code.setFont(font);
		code.setForeground(Color.WHITE);
		code.setBorder(null);
		codeInput.add(code, c);
		
		c.gridx=0;
		c.gridy=1;
		codeInput.add(clavier,c);
		
		
	//cards manager
		cards.add(lock, "lock");
		cards.add(codeInput, "codeInput");
	}
	
	

	public String getCode() {
		return code.getText();
	}
	public JButton getVerrou() {
		return ok;
	}
	
	
	
	class CurrentTime implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			Calendar cal = Calendar.getInstance();
			heure.setText(hhmm.format(cal.getTime()));
			date.setText(dmy.format(cal.getTime()));
		}
	}
	
	
	
	
	class addNb implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			String text = code.getText();
			char[] textinchar;
			
			if(((JLabel)arg0.getSource()).getText().equals("<")) {
				textinchar=text.toCharArray();
				
				for (int i = code.getCaretPosition()-1; i < textinchar.length-1; i++) {
					textinchar[i]=textinchar[i+1];
				}
				
				text=String.valueOf(textinchar);
				text = text.substring(0, text.length()-1);
				code.setText(text);
			}
			else	
			code.setText(code.getText()+(((JLabel)arg0.getSource()).getText()));
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			((JLabel)arg0.getSource()).setBackground(Color.GRAY);
			((JLabel)arg0.getSource()).setOpaque(true);
		}
			
		@Override
		public void mouseExited(MouseEvent arg0) {
			((JLabel)arg0.getSource()).setBackground(new Color(0,0,0,0));
			((JLabel)arg0.getSource()).setOpaque(false);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
	
	class Drag implements MouseMotionListener{
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			cpt++;
			if(cpt>30){
				cardLayout.show(cards, "codeInput");
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {}
	}
	
}
