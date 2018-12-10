package Secondtry;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Random;

import sun.audio.*;

public class MyCanvas extends Canvas implements KeyListener {
	
	Goodguy DMcar = new Goodguy(940,730,50,40,"files/LeftCarDM.png"); // retrieves the Domino's Pizza delivery car + sets coordinates
	Goodguy PHcar = new Goodguy(10,10,50,40,"files/RightCarPH.png"); // retrieves the Pizza hut delivery car + sets coordinates
	Background startScreen = new Background(0,0,1000,800,"files/StartScreen.png"); // displays the start screen
	Background background = new Background(0,0,1000,800,"files/Background.png"); // sets the game background
	Background DMwin = new Background(0,0,1000,800,"files/DMEndScreen.png"); 
	Background PHwin = new Background(0,0,1000,800,"files/PHEndScreen.png");
	LinkedList badguys = new LinkedList();
	int DMscore = 0;
	int PHscore = 0;
	boolean GameStart = false;
	boolean dmwin = false;
	boolean phwin = false;
	boolean GameOver = false;
	
	
	public MyCanvas() {
		this.setSize(1000,800); 
		this.addKeyListener(this); 
		playIt("files/soundtrack2.wav");
		
		Random rand = new Random();
		int winwidth = this.getWidth();
		int winheight = this.getHeight();
		for(int i = 0; i<21; i++) {
			Badguy bg = new Badguy(getRandomNumberInRange(0, 900),getRandomNumberInRange(0, 700),80,70,"files/villain.png");
			Rectangle r = new Rectangle(100,100,100,100);
			if (r.contains(DMcar.getxCoord(),DMcar.getyCoord())) {
				System.out.println("Pizza delivered!");
				continue;
			}
			badguys.add(bg);
		}
	}
	
	public static int getRandomNumberInRange (int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		
		Random r = new Random ();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public void playIt(String filename) {
		
		try {
			InputStream in = new FileInputStream(filename);
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	@Override
	public void paint(Graphics g) {
		if (GameStart == false && GameOver == false) {
			g.drawImage(startScreen.getImg(), startScreen.getxCoord(), startScreen.getyCoord(), startScreen.getWidth(), startScreen.getHeight(), this);

		} if (GameStart == true && GameOver == false) {
			g.drawImage(background.getImg(), background.getxCoord(), background.getyCoord(), background.getWidth(), background.getHeight(), this);
			g.drawImage(DMcar.getImg(), DMcar.getxCoord(), DMcar.getyCoord(), DMcar.getWidth(), DMcar.getHeight(), this);
			g.drawImage(PHcar.getImg(), PHcar.getxCoord(), PHcar.getyCoord(), PHcar.getWidth(), PHcar.getHeight(), this);
			
			Font font = new Font ("Gameplay", Font.PLAIN, 20);
			g.setFont(font);
			g.setColor(Color.BLACK);
			
			g.drawString("Domino's Pizza Score: " + DMscore, 10, 30);
			g.drawString("Pizza Hut Score: " + PHscore, 10, 80);
			

			for(int i = 0; i < badguys.size(); i++) {
				Badguy bg = (Badguy) badguys.get(i);
				g.drawImage(bg.getImg(), bg.getxCoord(), bg.getyCoord(), bg.getWidth(), bg.getHeight(), this); 
				
				/*if (DMscore + PHscore == 21) {
					
					if (DMscore > 10) {
						dmwin = true;
						GameStart  = false;
						GameOver = true;
					}
					
					if (PHscore > 10) {
						phwin = true;
						GameStart  = false;
						GameOver = true;
					}
				}*/
			}
			if (badguys.size()==0) {
				GameOver = true;
				if (DMscore>PHscore) {
					dmwin = true;
					phwin = false;
				}
				else {
					dmwin = false;
					phwin = true;
				}
			}
		}
		
		if (dmwin == true && GameOver == true) {
			g.drawImage(DMwin.getImg(), DMwin.getxCoord(), DMwin.getyCoord(), DMwin.getWidth(), DMwin.getHeight(), this);

		}
		
		if (phwin == true && GameOver == true) {
			g.drawImage(PHwin.getImg(), PHwin.getxCoord(), PHwin.getyCoord(), PHwin.getWidth(), PHwin.getHeight(), this);

		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		int key = e.getKeyCode();
		int speed = 20;
		int x = DMcar.getxCoord();
		int y = DMcar.getyCoord();
		int x2 = PHcar.getxCoord();
		int y2 = PHcar.getyCoord();
		
		if (GameOver && e.getKeyCode()==10) {
			GameStart = true;
			GameOver = false;
			dmwin = false;
			phwin = false;
			Random rand = new Random();
			int winwidth = this.getWidth();
			int winheight = this.getHeight();
			for(int i = 0; i<21; i++) {
				Badguy bg = new Badguy(getRandomNumberInRange(0, 900),getRandomNumberInRange(0, 700),80,70,"files/villain.png");
				Rectangle r = new Rectangle(100,100,100,100);
				if (r.contains(DMcar.getxCoord(),DMcar.getyCoord())) {
					System.out.println("Pizza delivered!");
					continue;
				}
				badguys.add(bg);
			}
			/* 
			 * setxCoord(10);
		setyCoord(10);
		setWidth(30);
		setHeight(30);
			 */
			
			
			
			
			PHcar.setxCoord(10);
			PHcar.setyCoord(10);
			PHcar.setImg("files/RightCarPH.png");
			DMcar.setxCoord(940);
			DMcar.setyCoord(730);
			DMcar.setImg("files/LeftCarDM.png");
			
			/* DMcar.setxCoord(this.getWidth()-DMcar.getWidth());
			DMcar.setyCoord(this.getHeight()-DMcar.getHeight());
			*/
			
			DMscore = 0;
			PHscore = 0;
		}
			
			
		if (key == KeyEvent.VK_LEFT) {
			if (x < 0) { x = x + speed * 3; } 
			x = x - speed;
			DMcar.setxCoord(x);
			DMcar.setImg("files/left.png");
		}
		
		if (key == KeyEvent.VK_RIGHT) {
				x = x + speed;
				if (x > 950) { x = x - speed * 3; } 
			DMcar.setxCoord(x);
			DMcar.setImg("files/right.png");
		} 
			
			
		if (key == KeyEvent.VK_UP) {
			if (y < 0) { y = y + speed * 3; } 
			y = y - speed;
			DMcar.setyCoord(y);
			DMcar.setImg("files/up.png");
		} 
		
		
		if (key == KeyEvent.VK_DOWN) {
			if (y > 720) { y = y - speed * 3; }
			y = y + speed;
			DMcar.setyCoord(y);
			DMcar.setImg("files/down.png");
		}
		
		
		
		
		if (key == KeyEvent.VK_A) {
			if (x2 < 0) { x2 = x2 + speed * 3; } 
			x2 = x2 - speed;
			PHcar.setxCoord(x2);
			PHcar.setImg("files/LeftCarPH.png");
		}
		
		if (key == KeyEvent.VK_D) {
			x2 = x2 + speed;
			if (x2 > 950) { x2 = x2 - speed * 3; } 
			PHcar.setxCoord(x2);
			PHcar.setImg("files/RightCarPH.png");
		} 
			
			
		if (key == KeyEvent.VK_W) {
			if (y2 < 0) { y2 = y2 + speed * 3; } // check to see if user has moved off game area
			y2 = y2 - speed;
			PHcar.setyCoord(y2);
			PHcar.setImg("files/BackCarPH.png");
		} 
		
		
		if (key == KeyEvent.VK_S) {
			if (y2 > 720) { y2 = y2 - speed * 3; } 
			y2 = y2 + speed;
			PHcar.setyCoord(y2);
			PHcar.setImg("files/FrontCarPH.png");
		}
		
		
			
		for(int i = 0; i < badguys.size(); i++) {
			Badguy bg = (Badguy) badguys.get(i); 
			Rectangle r = new Rectangle(bg.getxCoord(),bg.getyCoord(),bg.getWidth(),bg.getHeight());
			Rectangle DM = new Rectangle(DMcar.getxCoord(), DMcar.getyCoord(), DMcar.getWidth(), DMcar.getHeight());
			Rectangle PH = new Rectangle(PHcar.getxCoord(), PHcar.getyCoord(), PHcar.getWidth(), PHcar.getHeight());
			
			if (r.intersects(DM)) { 
				System.out.println("Pizza delivered by Domino's Pizza!");
				badguys.remove(i);
				DMscore ++;
			}
			
			if (r.intersects(PH)) { 
				System.out.println("Pizza delivered by Pizza Hut!");
				badguys.remove(i);
				PHscore ++;
			}
			
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ENTER) {
			GameStart = true;
		}
	}

}