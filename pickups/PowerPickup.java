package com.game.pickups;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

public class PowerPickup extends GameObject{
	
	private Handler handler;
	private BufferedImage power;
	public static boolean allowDrop;
	public PowerPickup(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		try {
			power = ImageIO.read(new File("bin/used/power.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		velX = 0;
		velY = 1.5f;

	}
	public Rectangle getBounds(){
		return new Rectangle((int) x-25, (int) y-25, 65, 65);
	}
	public void collision(){
		
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					if(CharacterSelect.player == 0){
						Player1.power+=1;
					}
					if(CharacterSelect.player == 1){
						Player2.power+=1;
					}
					if(CharacterSelect.player == 2){
						Player3.power+=1;
					}
					
					handler.removeObject(this);	
				
				}
			}
		}
	}	
	public void tick() {
		if(allowDrop){
		y += velY;
		collision();
		if(y <= 0 || y >= Game.HEIGHT)
			handler.removeObject(this);
		}
		if(!allowDrop){
			handler.removeObject(this);
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(power, (int)x,(int)y, 15, 15, null);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
			g2d.drawRect((int) x-25, (int) y-25, 65, 65);
		}
	}
	
	

}