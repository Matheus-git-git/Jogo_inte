package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public int speed;
	
	
	//describes an image with an accessible buffer of image data. We use this to store our image files
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	public boolean directionUp, directionDown, directionLeft, directionRight;
	
	public int spriteCounter = 0;
	public int spriteNumber = 1;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOnUp = false, collisionOnDown = false, collisionOnLeft = false, collisionOnRight = false;
	
}
