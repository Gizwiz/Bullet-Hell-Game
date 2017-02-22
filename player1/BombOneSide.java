package com.game.player1;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

	public class BombOneSide extends GameObject{
		
		private Handler handler;
		private Random r = new Random();
		private Random t = new Random();
		private Random f = new Random();
		private int direction;
		private int rand;
		private int speedUpTimer = 100;
		private Image[] bombside = new Image [6];

		public BombOneSide(float x, float y, ID id, float velX, float velY, Handler handler) {
			/*///////////////////////////////////////////////////////
			!!!!!!!!!!!!!!			BOMBS			!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!			ARE				!!!!!!!!!!!!!!
			!!!!!!!!!!!!!!			OBJECTS			!!!!!!!!!!!!!!
			///////////////////////////////////////////////////////*/
			super(x, y, velY, velY, id);
			
			this.handler = handler;
			rand = r.nextInt(6);
			this.x = t.nextInt(Game.WIDTH);

			try {
				bombside[0] = ImageIO.read(new File("bin/used/snowflake1.png"));
				bombside[1] = ImageIO.read(new File("bin/used/snowflake2.png"));
				bombside[2] = ImageIO.read(new File("bin/used/snowflake3.png"));
				bombside[3] = ImageIO.read(new File("bin/used/snowflake4.png"));
				bombside[4] = ImageIO.read(new File("bin/used/snowflake5.png"));
				bombside[5] = ImageIO.read(new File("bin/used/snowflake6.png"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			velX = 0.2f;
			velY = 1;

		}
		public Rectangle getBounds(){
			return new Rectangle((int) x-65, (int) y-65, 75, 75);
		}
		public void collision(){

		}	
		public void tick() {
			//At speedUpTimer0 make bullet move at speed x +-= velX = 0,2f and y=7
			//Else move at velY = 1;
			speedUpTimer--;
			if (speedUpTimer == 0){
				velY = 7;
				direction = f.nextInt(3);
			}
			if(direction == 1){
				x+=velX;
			}
			if(direction == 2){
				x-=velX;
			}
			if(direction == 0){
				x+=0;
			}
			y -= velY ;
			if(y <= 0){
			handler.removeObject(this);
			}
			//fullscreen bounding
			if(x <= 200 || x> Game.WIDTH-300){
			handler.removeObject(this);
			}
		}

		@Override
		public void render(Graphics2D g2d) {
			g2d.drawImage(bombside[rand], (int)x-50, (int)y-50, 50, 50, null);
			if(MainSettings.showHitBoxes){
				g2d.setColor(Color.red);
				g2d.drawRect((int) x-65, (int) y-65, 75, 75);
			}
						
		}
		
		

	}

