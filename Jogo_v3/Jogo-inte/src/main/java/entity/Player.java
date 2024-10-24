package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	public boolean speedPower = false;
	int standCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;	
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
	}


	public void setDefaultValues() {
		worldX = gp.tileSize *11;
		worldY = gp.tileSize * 40;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/verde 192.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true 
				|| keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if((keyH.upPressed == true && keyH.rightPressed == true) ||
				(keyH.upPressed == true && keyH.leftPressed == true) ||	
				(keyH.downPressed == true && keyH.rightPressed == true) ||
				(keyH.downPressed == true && keyH.leftPressed == true)) {
				if(speedPower) {
					speed = 8;
				} else {
					speed = 4;
				}
				
				speed = (int)Math.round(speed/Math.sqrt(2));
			} else {
				if(speedPower) {
					speed = 8;
				} else {
					speed = 4;
				}
			}
			
			
			if(keyH.upPressed == true) {
				directionUp = true;
				direction = "up";
				if(collisionOnUp == false) {
					worldY -= speed;
				}
			}
			if(keyH.downPressed == true) {
				directionDown = true;
				direction = "down";
				if(collisionOnDown == false) {
					worldY += speed;
				}
			}

			if(keyH.leftPressed == true) {
				directionLeft = true;
				direction = "left";
				if(collisionOnLeft == false) {
					worldX -= speed;
				}
			}

			if(keyH.rightPressed == true) {
				directionRight = true;
				direction = "right";
				if(collisionOnRight == false) {
					worldX += speed;
				}
			}
			if(collisionOnUp && keyH.downPressed) {
				collisionOnUp = false;
			}
			if(collisionOnDown && keyH.upPressed) {
				collisionOnDown = false;
			}
			if(collisionOnLeft && keyH.rightPressed) {
				collisionOnLeft = false;
			}
			if(collisionOnRight && keyH.leftPressed) {
				collisionOnRight = false;
			}
			
			// Check Tile Collision
			gp.cChecker.checkTile(this);
			
			//Check Object Collision
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			
			directionUp = false;
			directionDown = false;
			directionLeft = false;
			directionRight = false;
			
			
			//for each 12 cycles we toggle the values. This toggling process generates the animation as well as its speed
			spriteCounter++;
			if(spriteCounter>5) {
				if(spriteNumber==1) {
					spriteNumber=2;
				}
				else if(spriteNumber==2) {
					spriteNumber=1;
				}
				spriteCounter=0;
			}
			standCounter = 0;
			
			/*directionUp = false;
			directionDown = false;
			directionLeft = false;
			directionRight = false;*/
		} else {
			
			standCounter++;
			if(standCounter>20) {
				spriteNumber = 1;
				standCounter = 0;
			}
				
		}
	}
	public void pickUpObject(int i) {
		
		if (i != 999) {
		
			String objectName = gp.obj[i].name;
			
			
			switch(objectName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("Você tem uma chave!");
			
				break;
			case "Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
					hasKey--;
					gp.ui.showMessage("Você abriu a porta!");
				} else {
					gp.ui.showMessage("Você precisa de uma chave!");
				}
				break;
			case "Boots":
				gp.playSE(2);
				speedPower = true;
				gp.obj[i] = null;
				gp.ui.showMessage("Acelerar!!!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			}

		
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch(direction) {
			case "up":
				if(spriteNumber==1) {
					image = up1;
				}
				if(spriteNumber==2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNumber==1) {
					image = down1;
				}
				if(spriteNumber==2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNumber==1) {
					image = left1;
				}
				if(spriteNumber==2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNumber==1) {
					image = right1;
				}
				if(spriteNumber==2) {
					image = right2;
				}
				break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		//SEE PLAYER COLISION RECTANGLE
		/*g2.setColor(Color.red);
		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);*/
		
		
	}
}
