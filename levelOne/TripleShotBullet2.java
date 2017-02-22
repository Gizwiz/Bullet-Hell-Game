package com.game.levelOne;


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

public class TripleShotBullet2 extends GameObject{
	
	private BufferedImage bullet;
	private Handler handler;

	public TripleShotBullet2(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, velX, velY, id);
		this.handler = handler;
		try {
			bullet = ImageIO.read(new File("bin/used/tripleShotBullet2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if(CharacterSelect.player == 0){
//			diffX = (x-Player1.playerX); 
//			diffY = (y-Player1.playerY);
//		}
//		if(CharacterSelect.player == 1){
//			diffX = (x-Player2.playerX); 
//			diffY = (y-Player2.playerY);
//		}
//		if(CharacterSelect.player == 2){
//			diffX = (x-Player3.playerX); 
//			diffY = (y-Player3.playerY);
//		}
//
//		
//		if(diffX < 0){
//			moveX = (float) -(diffX/150.0);
//		}
//		if(diffX > 0){
//			moveX = (float) -(diffX/150.0);
//		}
//		if(diffY < 0){
//			moveY = (float) -(diffY/150.0);
//		}
//		if(diffY > 0){
//			moveY = (float) (diffY/150.0);
//		}

	}
	public Rectangle getBounds(){
		return new Rectangle((int) x+2, (int) y+2, 16, 16);
	}
	public void collision(){
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (
					tempObject.getId() == ID.Player ||
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(this);
				}
			}
			if (tempObject.getId() == ID.Player ){
				if(getBounds().intersects(tempObject.getBounds())){

					if(CharacterSelect.player == 0 && !Player1.invulnerable){
						Player1.loseLife();
					}
					if(CharacterSelect.player == 1 && !Player2.invulnerable){
						Player2.loseLife();
					}
					if(CharacterSelect.player == 2 && !Player3.invulnerable){
						Player3.loseLife();

					}
				}
			}
		}
	}	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT){
			handler.removeObject(this);
		}
		if(x <= 200 || x >= Game.WIDTH-350){
			handler.removeObject(this);
		}

		if(CharacterSelect.player == 0 && !Player1.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 1 && !Player2.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 2 && !Player3.invulnerable){
		collision();
		}
 
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(bullet, (int)x, (int)y, 22, 22, null);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
			g2d.drawRect((int)x+2,(int)y+2, 16, 16);
		}
	}
	
	

}