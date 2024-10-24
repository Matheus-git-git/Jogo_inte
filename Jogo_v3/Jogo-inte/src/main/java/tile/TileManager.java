package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	private int numberOfTiles = 60;
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[numberOfTiles];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
				
		getTileImage();
		
		loadMap("/maps/World_V3m.txt");
	}

	//Otimizando as texturas 
	private void getTileImage() {
	    //chama o nome do metodo, Qual o [x]no mapa, nome da figura, colisão sim ou não //todas as img tem q ter .png
		 	// Tiles reservados
			
			try {

				setup(44, "grass", false);
				setup(11, "asfalto", false);
				setup(14, "QR_code", false);
				setup(21, "21", false);
				setup(13, "range", false);
				setup(12, "Street", false);
				setup(20, "20", false);
				setup(42, "42", true);
				setup(40, "40", false);
				setup(30, "30", false);
				setup(41, "41", false);
				setup(43, "wall", true);
				setup(35, "grass_little_fence", false);
				setup(32, "grass_little_fence_2", true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			//setup(44, "grass", false);
			/*setup(1, "grass", false);
			setup(2, "grass", false);
			setup(3, "grass", false);
			setup(4, "grass", false);
			setup(5, "grass", false);
			setup(6, "grass", false);
			setup(7, "grass", false);
			setup(8, "grass", false);
			setup(9, "grass", false);
			setup(10, "grass", false);*/
			// teste setup(6, "grama", false); //deixou ela em baixa Qualidade
			
			//Tiles que vamos usar para construir o mapa novo

			/*  

			Exemplo antigo dos tile
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			*/
	}
	
	//Renderização das texturas tile subistituindo os tile[5]
	
	public void setup(int index,String imageName,boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/img/" + imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0, row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col< gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;	
				}
				if(col == gp.maxWorldCol){
					col = 0;
					row++;
				}
			}
			br.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;			
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;	
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;			
				worldRow++;
			}

		}	
	}
}