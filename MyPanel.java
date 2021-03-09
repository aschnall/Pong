package Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyPanel extends JPanel implements ActionListener, KeyListener {

	//set dimensions of the panel
	final int PANEL_HEIGHT = 500;
	final int PANEL_WIDTH = 500;
	//paddle Y positions and velocity
	int paddleRightY = 200;
	int paddleLeftY = 200;
	int paddleRightVelocity = -15;
	int paddleLeftVelocity = -15;
	//ball positions and velocity
	int ballX = 250;
	int ballY = 250;
	int ballXVelocity = 1;
	int ballYVelocity = 1;
	//user scores
	int playerRightScore = 0;
	int playerLeftScore = 0;
	//boolean to indicate whether play is live or not
	boolean play = true;
	
	public MyPanel() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		Timer timer = new Timer(10, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		//set color to white
		g2D.setColor(Color.white);
		//draw the middle line
		g2D.drawLine(PANEL_WIDTH/2, 0, PANEL_WIDTH/2, PANEL_HEIGHT);
		//draw the panels
		g2D.fillRect(PANEL_WIDTH - 40, paddleRightY, 20, 100);
		g2D.fillRect(40, paddleLeftY, 20, 100);
		//draw the ball
		g2D.fillOval(ballX, ballY, 20, 20);
		//draw score
		g.setFont(new Font("serif", Font.BOLD, 25));
		g2D.drawString("" + playerRightScore, PANEL_WIDTH/2 + 20, 25);
		g2D.drawString("" + playerLeftScore, PANEL_WIDTH/2 - 35, 25);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (play) {
			if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(PANEL_WIDTH - 40, paddleRightY, 20, 100))) {
				ballXVelocity = ballXVelocity*-1;
			}
			if(new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(40, paddleLeftY, 20, 100))) {
				ballXVelocity = ballXVelocity*-1;
			}
			//left user scores, increment left score and reset ball/paddle positions
			if (ballX >= PANEL_WIDTH-20) {
				play = false;
				ballX = 250;
				ballY = 250;
				paddleLeftY = 200;
				paddleRightY = 200;
				playerLeftScore++;
			}
			//right user scores, increment right score and reset ball/paddle positions
			if (ballX <= 0) {
				play = false;
				ballX = 250;
				ballY = 250;
				paddleLeftY = 200;
				paddleRightY = 200;
				playerRightScore++;
			}
			ballX += ballXVelocity;
			if (ballY >= PANEL_HEIGHT-20 || ballY <= 0) {
				ballYVelocity = ballYVelocity*-1;
			}
			ballY += ballYVelocity;
			repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		//key listeners for moving paddles up and down
		switch (code) {
			case KeyEvent.VK_UP:
				if (paddleRightY >= 8) {
					paddleRightY += paddleRightVelocity;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (paddleRightY <= PANEL_HEIGHT - 108) {
					paddleRightY -= paddleRightVelocity;
				}
				break;
			case KeyEvent.VK_Q:
				if (paddleLeftY >= 8) {
					paddleLeftY += paddleLeftVelocity;
				}
				break;
			case KeyEvent.VK_A:
				if (paddleLeftY <= PANEL_HEIGHT - 108) {
					paddleLeftY -= paddleLeftVelocity;
				}
				break;
		}
		//set play back live after a score
		if (code == KeyEvent.VK_SPACE) {
			play = true;
		}
		repaint();		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	
}
