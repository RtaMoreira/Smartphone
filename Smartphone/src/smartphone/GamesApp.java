/**
* TP Week2
*Author: Joao Silva
*Date creation : 22 mai 2018
*/
package smartphone;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamesApp extends AppTemplate{

	private ImageIcon gamesIcon =new ImageIcon("image/icon/icon_tictactoe.png");
	private ImageIcon gamesIconHover =new ImageIcon("image/icon/icon_tictactoeHOVER.png");
	
	private CardLayout cardlayout = new CardLayout();
	private JPanel gamePanel = new JPanel(cardlayout);
	private JPanel mainPanel = new JPanel();
	private Snake snake = new Snake();
	
	private JLabel snakeButton = new JLabel(snake.getSnakeIcon());
	
	public ImageIcon getGamesIcon() {
		return gamesIcon;
	}

	public ImageIcon getGamesIconHover() {
		return gamesIconHover;
	}
	
	
	public GamesApp() {
		super("Games", Color.BLUE);
		// TODO Auto-generated constructor stub
		snakeButton.addMouseListener(new OpenSnake());
		mainPanel.add(snakeButton);
		
		gamePanel.add(mainPanel,"main");
		gamePanel.add(snake,"snake");
		
		add(gamePanel);
		
		super.getNavigation().getBackButton().addActionListener(new TimerStop());
	}
	
	class TimerStop implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			snake.getTimer().stop();
			cardlayout.show(gamePanel, "main");
		}
		
	}
	
	class OpenSnake implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			snake.getTimer().start();
			cardlayout.show(gamePanel, "snake");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			snakeButton.setIcon(snake.getSnakeIconHover());
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			snakeButton.setIcon(snake.getSnakeIcon());
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}

}
