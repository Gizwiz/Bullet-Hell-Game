package com.game.zdump;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

	public class CurvedBulletRight extends GameObject{
		
		private Handler handler;

		private BufferedImage bullet;


		public CurvedBulletRight(float x, float y, float velX, float velY, ID id, Handler handler) {
			super(x, y, velX, velY, id);
			this.velX = velX;
			this.velY = velY;
			this.handler = handler;
			try {
				bullet = ImageIO.read(new File("bin/used/basicCurvedBullet.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		public Rectangle getBounds(){
			return new Rectangle((int) x, (int) y, 16, 16);
		}
		public void collision(){
			for (int i=0; i<handler.object.size(); i++){
				
				GameObject tempObject = handler.object.get(i);
				//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
				if (
						tempObject.getId() == ID.Bomb){
					if(getBounds().intersects(tempObject.getBounds())){
						handler.removeObject(this);
					}
				}
				if (tempObject.getId() == ID.Player){
					if(getBounds().intersects(tempObject.getBounds())){
						if(CharacterSelect.player == 0){
							Player1.playerHealth -= 1;
						}
						if(CharacterSelect.player == 1){
							Player2.playerHealth -= 1;
						}
						if(CharacterSelect.player == 2){
							Player3.playerHealth -= 1;
						}
					
					}
				}
			}
		}	
		public void tick() {
			x -= Math.sqrt(y)-21;
			y -=  velY;
			if(y <= 0 || y >= Game.HEIGHT){
				handler.removeObject(this);
			}
			if(x <= 0 || x >= Game.WIDTH){
				handler.removeObject(this);
			}
			collision();
//			handler.addObject(new Trail((int) x, (int) y, ID.Trail, handler,  Color.red, 16, 16, 0.03f));
		}

		@Override
		public void render(Graphics2D g2d) {
			g2d.drawImage(bullet, (int)x, (int)y, 22, 22, null);
//			g2d.setColor(new Color(75,0,150));
//			g2d.fillRect((int) x,(int) y, 16, 16);
			
		}
		
		

	}

