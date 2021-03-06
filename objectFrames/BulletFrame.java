package com.game.objectFrames;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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

public class BulletFrame extends GameObject{
	
	private Image bullet;
	private Handler handler;

	public BulletFrame(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, velX, velY, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		try {
			bullet = ImageIO.read(new File("bin/used/basicEnemyBullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


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
			if (tempObject.getId() == ID.Player){
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
		collision();
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT){
			handler.removeObject(this);
		}
		if(x <= 200 || x >= Game.WIDTH-350){
			handler.removeObject(this);
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