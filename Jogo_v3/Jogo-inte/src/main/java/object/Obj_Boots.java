package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_Boots extends SuperObject{
	public Obj_Boots() {
		name = "Boots";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
