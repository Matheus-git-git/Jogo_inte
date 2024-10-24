package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		
		if(entity.directionUp == true) {
			entityTopRow = (entityTopWorldY - (entity.speed))/gp.tileSize;
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
			
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnUp = true;
			} else {
				entity.collisionOnUp = false;
			}
		}
		
		entityLeftCol = entityLeftWorldX/gp.tileSize;
		entityRightCol = entityRightWorldX/gp.tileSize;
		entityTopRow = entityTopWorldY/gp.tileSize;
		entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		if(entity.directionDown == true) {
			entityBottomRow = (entityBottomWorldY + (entity.speed))/gp.tileSize;
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
			
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnDown = true;
			} else {
				entity.collisionOnDown = false;
			}
		}
		entityLeftCol = entityLeftWorldX/gp.tileSize;
		entityRightCol = entityRightWorldX/gp.tileSize;
		entityTopRow = entityTopWorldY/gp.tileSize;
		entityBottomRow = entityBottomWorldY/gp.tileSize;
		if(entity.directionLeft == true) {
			entityLeftCol = (entityLeftWorldX - (entity.speed))/gp.tileSize;
			tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
			
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnLeft = true;
			} else {
				entity.collisionOnLeft = false;
			}
			
		}
		entityLeftCol = entityLeftWorldX/gp.tileSize;
		entityRightCol = entityRightWorldX/gp.tileSize;
		entityTopRow = entityTopWorldY/gp.tileSize;
		entityBottomRow = entityBottomWorldY/gp.tileSize;
		if(entity.directionRight == true) {
			entityRightCol = (entityRightWorldX + (entity.speed))/gp.tileSize;
			tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
			
			if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnRight = true;
			} else {
				entity.collisionOnRight = false;
			}
		}
		
	}
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] != null) {
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// Get the object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
	
				if(entity.directionUp) {
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOnUp = true;
						}
						if(player == true) {
							index = i;
						}
					}
				}
				if(entity.directionDown) {
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOnDown = true;
						}
						if(player == true) {
							index = i;
						}
					}
				}
				if(entity.directionLeft) {
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOnLeft = true;
						}
						if(player == true) {
							index = i;
						}
					}
				}
				if(entity.directionRight) {
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOnRight = true;
						}
						if(player == true) {
							index = i;
						}
					}
				}
     			entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

			}
		}
		
		return index;
	}
}















