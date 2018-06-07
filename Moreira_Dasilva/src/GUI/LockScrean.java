/**
* LockScrean
*Author: Joao Silva
*Date creation : 7 mai 2018
*ce Panel sera présent lors de l'ouverture de l'appkication et lorsqu'on le verouille
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
	private ImagePanel codeInput = new ImagePanel(new ImageIcon("image/background/samsung.jpg"));
	private JPanel clavier = new JPanel (new GridLayout(4,3));
	
	private GridBagConstraints c = new GridBagConstraints();
	protected CardLayout cardLayout = new CardLayout();
	private JPanel cards= new JPanel(cardLayout);
	private int cpt=0;
	private JButton ok = new JButton("ok");
	private JPasswordField code = new JPasswordField();
	
	private DateFormat hhmm = new SimpleDateFormat("HH:mm");
	private DateFormat dmy = new SimpleDateFormat("dd MMMM yyyy");
	private JLabel heure = new JLabel();
	private Timer timer = new Timer(0,new CurrentTime());
	
	private JLabel date = new JLabel();
	private Font font = new Font("myFont", 10, 20);
	private JPanel datePanel = new JPanel(new GridLayout(2,1));
	private JLabel swipe = new JLabel("Swipe to unlock");
	private JLabel codeError = new JLabel("");
	
	public LockScrean() 
	{
		setLayout(new BorderLayout());
		timer.start();
		add(cards);
		
	/**ajouter les trucs dans l'écran vérouillé et le MouseListenner*/
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
		
	/**creer le clavier*/
		for (int i = 0; i < 9; i++) 
		{
			JLabel j = new JLabel(String.valueOf(i+1));
			j.setForeground(Color.WHITE);
			j.setFont(font);
			
			j.addMouseListener(new AddNb());
			clavier.add(j);
		}
		
		JLabel retour = new JLabel("<");
		retour.setFont(font);
		retour.setForeground(Color.WHITE);
		retour.addMouseListener(new AddNb());
		clavier.add(retour);
		
		JLabel zero = new JLabel("0");
		zero.setFont(font);
		zero.setForeground(Color.WHITE);
		zero.addMouseListener(new AddNb());
		clavier.add(zero);
		
		clavier.add(ok);
		
		clavier.setOpaque(false);
		
	/**change codeInput*/
		codeInput.setLayout(new GridBagLayout());
		codeError.setForeground(Color.WHITE);
		code.setOpaque(false);
		code.setPreferredSize(new Dimension(200, 30));
		code.setHorizontalAlignment(SwingConstants.CENTER);
		code.setFont(font);
		code.setForeground(Color.WHITE);
		code.setBorder(null);
		
		c.gridx=0;
		c.gridy=0;
		codeInput.add(codeError,c);
		
		c.gridy=1;
		codeInput.add(code, c);

		c.gridy=2;
		codeInput.add(clavier,c);
		
	/**manage cardLayout*/
		cards.add(lock, "lock");
		cards.add(codeInput, "codeInput");
	}
	
	public String getCode() 
	{
		return code.getText();
	}
	
	public JButton getVerrou() 
	{
		return ok;
	}
	
	public JLabel getCodeError() 
	{
		return codeError;
	}
	
	public void refresh() 
	{
		code.setText("");
		codeError.setText("");
		cpt=0;
		cardLayout.show(cards, "lock");
	}
	
	/**class utilisée par le Timer*/
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
	
	/**MouseListener pour le clavier*/
	class AddNb implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent arg0) 
		{
			String text = code.getText();
			char[] textinchar;
			
			if(((JLabel)arg0.getSource()).getText().equals("<")) 
			{
				textinchar=text.toCharArray();
				
				for (int i = code.getCaretPosition()-1; i < textinchar.length-1; i++) 
				{
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
		public void mouseEntered(MouseEvent arg0) 
		{
			((JLabel)arg0.getSource()).setBackground(Color.GRAY);
			((JLabel)arg0.getSource()).setOpaque(true);
		}
			
		@Override
		public void mouseExited(MouseEvent arg0) 
		
		{
			((JLabel)arg0.getSource()).setBackground(new Color(0,0,0,0));
			((JLabel)arg0.getSource()).setOpaque(false);
		}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}

	}
	
	/**mouseListener pour les swipe to unlock*/
	class Drag implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e) 
		{
			cpt++;
			if(cpt>20)
				cardLayout.show(cards, "codeInput");
		}
		public void mouseMoved(MouseEvent arg0) {}
	}
}
