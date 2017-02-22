package com.game.pickups;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.engine.ExperienceHandler;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

public class ScorePickup extends GameObject{
	
	private Handler handler;
	private BufferedImage power;
	public static boolean allowDrop  = true;
	private boolean pickedUp;
	private int removeTimer = 150;
	public ScorePickup(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		try {
			power = ImageIO.read(new File("bin/used/score.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		velX = 0;
		velY = 1f;

	}
	public Rectangle getBounds(){
		return new Rectangle((int) x-25, (int) y-25, 65, 65);
	}
	public void collision(){
		if(!pickedUp){
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					if(CharacterSelect.player == 0){
						pickedUp = true;
						Player1.playerScore+=100;
					}
					if(CharacterSelect.player == 1){
						pickedUp = true;
						Player2.playerScore+=100;
					}
					if(CharacterSelect.player == 2){
						pickedUp = true;
						Player3.playerScore+=100;
					}
					
					ExperienceHandler.updateXp(100);
				}
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
		if(x <= -5 || x >= Game.WIDTH-320){
			handler.removeObject(this);
		}
		if(!allowDrop){
			handler.removeObject(this);
		}
		if(pickedUp){
			removeTimer--;
		}
		if(removeTimer == 0){
		handler.removeObject(this);	
		}
	}

	@Override
	public void render(Graphics2D g2d) {
		Font fnt = new Font("Arial", 1, 10);
		if(!pickedUp){
		g2d.drawImage(power, (int)x,(int)y, 15, 15, null);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
			g2d.drawRect((int) x-25, (int) y-25, 65, 65);
		}
		}
		if(pickedUp){
			g2d.setFont(fnt);
			g2d.setColor(Color.white);
			g2d.drawString("+100", (int)x, (int)y);
		}
		
	}
	
	

}