/**
* TP Week2
*Author: Joao Silva
*Date creation : 23 mai 2018
*/
package smartphone;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener{

	private ImageIcon snakeIcon = new ImageIcon("image/icon/snake.png");
	private ImageIcon snakeIconHover = new ImageIcon("image/icon/snakeHOVER.png");
	
	private final int DELAY = 100;
    private JPanel[][] pixels = new JPanel[100][100];

    /* The coordinates of the snake. */
    private int[] x = new int[10000];//possible d'avoir le tout accup� par le snake
    private int[] y = new int[10000];

    /* Coordinates for apple. */
    private int apple_x, apple_y;

    /* Pressed Key. */
    private int snakeSize = 3;//commence a 3
    private Timer t = new Timer(DELAY, this);
    int direction = 1;
    boolean over=false;
    
    public Snake() {
    	addMouseListener(new changeDirection());
    	setBackground(Color.GREEN);
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(600, 600));
    	setLayout(new GridLayout(100,100));
       	for (int i = 0; i <snakeSize; i++) {
			y[i]=50;
			x[i]=50-i;
		}
       	
       	for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				pixels[i][j]=new JPanel();
				pixels[i][j].setBackground(Color.BLACK);
				pixels[i][j].setOpaque(true);
				add(pixels[i][j]);
			}
    	}
       	randomFood();
    	paint();	
    }
    
    public ImageIcon getSnakeIcon() {
		return snakeIcon;
	}

	public ImageIcon getSnakeIconHover() {
		return snakeIconHover;
	}

	public Timer getTimer() {
		return t;
	}
	public void paint() {
    	for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels.length; j++) {
				pixels[i][j].setBackground(Color.BLACK);
			}
		}
    	
    	pixels[apple_x][apple_y].setBackground(Color.GREEN);
    	
    	for (int i = 0; i < snakeSize; i++) {
			pixels[x[i]][y[i]].setBackground(Color.GREEN);
		}
    }
    
    public void randomFood() {
        apple_x=(int)(Math.random()*100);
        apple_y=(int)(Math.random()*100);
    }
    
    public boolean checkpixel() {
    	//si c'es une pomme on rajoute 1 pixel de longueur
    	if(x[0]==apple_x&&y[0]==apple_y) {
    		snakeSize++;
    		randomFood();
    		return false;
    	}
    	
    	//si �a d�passe le tableau GAMEOVER
    	if(x[0]==100||x[0]<0||y[0]==100||y[0]<0) {
			return true;
    	}
    	
    
		int cpt=0;
		//si le serpent va contre lui meme GAMEOVER
		if(pixels[x[0]][y[0]].getBackground()==Color.GREEN)
			return true;
    		  
    	return false;
    }
    
    public void gameover() {
    	t.stop();
    	if(	JOptionPane.showConfirmDialog(null,"Voulez vous recommencer?")==JOptionPane.YES_OPTION)
    	{
    		for (int i = 0; i <snakeSize; i++) {
				y[i]=50;
				x[i]=50-i;
			}
			
			snakeSize=3;
			
			randomFood();
			paint();
	        t.start();
    	}
    	else
    	System.exit(0);
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		
	for (int i = (snakeSize-1); i > 0; i--) {
		x[i]=x[i-1];
		y[i]=y[i-1];
	}
	if (direction==1)
	x[0]++;
	if (direction==2)
	y[0]++;
	if (direction==3)
	x[0]--;
	if (direction==4)
	y[0]--;
	
	over = checkpixel();
	if(over) 
		gameover();
	else
		paint();
	}
	
	class changeDirection implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (direction<4) {
				direction++;
			return;
			}
			
			if ( direction==4) {
				direction=1;
			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

		
	}
class restart implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (int i = 0; i <snakeSize; i++) {
			y[i]=50;
			x[i]=50-i;
		}
		
		snakeSize=3;
		
		randomFood();
    	paint();
        t.start();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
}
