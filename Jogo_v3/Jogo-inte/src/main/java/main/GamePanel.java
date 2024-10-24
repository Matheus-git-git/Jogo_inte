package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	
	/* calculate the tile size base on the standard of 16x16 pixel.
	screens nowadays are much bigger, so, in order to make the tiles 16x16 appear bigger
	we are going to scale it
	*/
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	
	/*  now that we've calculated the size of the tile, we have to scale the screen based
	on how much tiles we want in our screen.
	*/
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol; //
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FPS
	int fps = 60;
	
	/*threads allow several running programs to be executed at the same time,
	to use it, we have to implement Runnable to the class, when we start it, it automatically calls the
	run method
	*/
	
	// SYSTEM
	TileManager tileManager = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	
	Thread gameThread; 
	
	
	// ENTITY AND OBJECT
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	// Game State
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	public GamePanel() { //constructor
		 this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		 this.setBackground(Color.black);
		 this.setDoubleBuffered(true);
		 this.addKeyListener(keyH);
		 this.setFocusable(true);
		 
		 
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		gameState = 1;
		playMusic(0);
	}

	public void startGameThread() {
		
		gameThread = new Thread(this); // instantiating the game thread
		gameThread.start();
	}
	
	
	public void run() { //this is where we are going to create our game loop
		
		double drawInterval = 1000000000/fps; // 0.01666 seconds in case of fps = 60
		double nextDrawTime = System.nanoTime() + drawInterval;
		int refreshrate = 0;
		double timer;
		
		while(gameThread != null) {
			// What we are doing here is: 
			// 1: Updating Information such as character positions 
			
			timer = System.nanoTime();
			update();
			
			
			// 2: DRAW: draw the screen with the updated information
			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			refreshrate++;
			if (refreshrate>=60) {
				
				System.out.println("fps: " + refreshrate + " time: " + ((System.nanoTime()-timer)*60)/1000000000 + " s");
				refreshrate=0;
			}
			
		}
	}
	
	public void update() {
		if (gameState == playState) {
			player.update();
		}
		if (gameState == pauseState) {
			// nada ainda 
		}
			
	}
		
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//DEGUB CONTA QUANTO TEMPO LEVA PARA O JOGO PINTAR AS IMG 
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
			
		}
		
		
		//tiles
		tileManager.draw (g2);
		
		// objects
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		//player
		player.draw(g2);
		
		//UI
		ui.draw(g2);
		
		//Desenhando o contator de renderização
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Contador Render: " + passed, 10, 400);
			System.out.println("Renderizador:" + passed);
		}
		
		
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
}

