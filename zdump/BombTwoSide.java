package com.game.zdump;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

	public class BombTwoSide extends GameObject{
		
		private Handler handler;
		private Random r = new Random();
		private int rand;
		private BufferedImage bombside;

		public BombTwoSide(float x, float y, ID id, float velX, float velY, Handler handler) {
			/*///////////////////////////////////////////////////////
			!!!!!!!!!!!!!!			BOMBS			!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!			ARE				!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!			OBJECTS			!!!!!!!!!!!!!!
			///////////////////////////////////////////////////////*/
			super(x, y, velY, velY, id);
			
			this.handler = handler;
			rand = r.nextInt(5);
			try {
				bombside = ImageIO.read(new File("bin/used/bomb1side.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			velX = 0;
			velY = 3;

		}
		public Rectangle getBounds(){
			return new Rectangle((int) x-100, (int) y-100, 222, 222);
		}
		public void collision(){

		}	
		public void tick() {
			if (rand == 1 || rand==3){
			x +=Math.sqrt(y)-25;
			}
			if (rand == 0|| rand==4){
			x -=Math.sqrt(y)-25;
			}
			if (rand == 2){
			x -= 0;
			}
			y -= velY ;
			if(y <= 0){
			handler.removeObject(this);
			}
			if(x <= 0 || x>Game.WIDTH){
			handler.removeObject(this);
			}
//			handler.addObject(new Trail((int) x, (int) y, ID.Trail, handler,  Color.red, 16, 16, 0.03f));
		}

		@Override
		public void render(Graphics2D g2d) {
			g2d.drawImage(bombside, (int)x-50, (int)y-50, 111, 111, null);
//			g2d.setColor(new Color(75,0,150));
//			g2d.fillRect((int) x,(int) y, 16, 16);
//			
		}
		
		

	}

