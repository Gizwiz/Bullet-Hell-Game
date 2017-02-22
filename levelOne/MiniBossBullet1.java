package com.game.levelOne;


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

/*
 * Basic bullet. Starts moving after movement timer hits 0
 */

public class MiniBossBullet1 extends GameObject{
	
	private Image bullet;
	private Handler handler;
	private int moveTimer = 200;
	private int moveTimer2 = 55;
	private float moveVelX;
	private boolean move = false;
	
	public MiniBossBullet1(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, velX, velY, id);
		this.handler = handler;
		this.velX = velX;
		try {
			bullet = ImageIO.read(new File("bin/used/mbossBullet1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Rectangle getBounds(){
		return new Rectangle((int)x+15,(int)y+15, 14, 14);
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

		if(CharacterSelect.player == 0 && !Player1.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 1 && !Player2.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 2 && !Player3.invulnerable){
		collision();
		}
		
		//MOVETIMER TICKER
		moveTimer--;
		
		if(moveTimer == 0){
			move=true;
		}
		
//		if (move){
//			moveTimer2--;
//			x+=moveVelX;
//		}
		if(move){
			velY += 0.01f;
			x += velX;
		}
		y+= velY;
//		x+=velX;	

		
		if(y >= Game.HEIGHT){
			handler.removeObject(this);
		}
//		if(x <= -165 || x >= Game.WIDTH-315){
//			handler.removeObject(this);
//		}
		
 
	}

	@Override
	public void render(Graphics2D g2d) {
		if(x >= 200 && x <= Game.WIDTH-315){
		g2d.drawImage(bullet, (int)x, (int)y, 45, 45, null);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
			g2d.drawRect((int)x+15,(int)y+15, 14, 14);
		}
		}
	}
	
	

}