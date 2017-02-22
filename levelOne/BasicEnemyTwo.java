package com.game.levelOne;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
//import com.game.engine.BulletHandler;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.pickups.BombPickup;
import com.game.pickups.PowerPickup;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

/*
 Basic Enemy shoots singular bullets on a timer that shoots at player's last known X and Y position.
 Receives parameters on creation to determine velX and velY.
 Can not move past Y=250.
 DISPLAYS WARNING MESSAGE. SPAWNING FROM BOTTOM & SIDES
 */

public class BasicEnemyTwo extends GameObject{
	
	private Handler handler;
	private int timer = 15;
	private int existTimer = 750;
	private float spawnTimer;
	private float alpha = 0.00f;
	private Random r =new Random();
	private Random t =new Random();
	private int hasPickupPower, hasPickupBomb;
	private boolean hasPickupP = false; //POWER PICKUP
	private boolean hasPickupB = false; //BOMB PICKUP
	private boolean spawned = false; //DETERMINES WHEN TO DISPLAY ENEMY AND TURN ON HITBOX
	
	private Image warning;


	public BasicEnemyTwo(float x, float y, float velX, float velY, float warningTime, ID id, Handler handler) {
		super(x, y, velX, velY, id);
		this.handler = handler;
		hasPickupPower = r.nextInt(2);
		if(hasPickupPower == 1){
			hasPickupP = true;
		}
		
		hasPickupBomb = t.nextInt(10);
		if(hasPickupBomb == 1){
			hasPickupB = true;
		}
		
		try {
			warning = ImageIO.read(new File("bin/used/Warning.png"));
		} catch (IOException e) {
			e.printStackTrace();	
		}
		this.spawnTimer = warningTime;
	}
	
	public Rectangle getBounds(){
		
		//If spawned make hitbox appear where drawn
		if(spawned){
		return new Rectangle((int) x, (int) y, 30, 30);
		//else draw hitbox out of screen with size of 0
		}else{
		return new Rectangle(-100, -100, 0, 0);
		}
	}
	public void collision(){
		
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.PlayerBullet ||
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){	
					if(CharacterSelect.player == 0 && !Player1.invulnerable){
						Player1.playerScore+=100;
					}
					if(CharacterSelect.player == 1 && !Player2.invulnerable){
						Player2.playerScore+=100;
					}
					if(CharacterSelect.player == 2 && !Player3.invulnerable){
						Player3.playerScore+=100;
					}
					if(hasPickupP && PowerPickup.allowDrop == true){
					handler.addObject(new PowerPickup((int)x,(int)y, ID.Pickup,handler));
					handler.removeObject(this);	
					}
					if(hasPickupB && BombPickup.allowDrop == true){
					handler.addObject(new BombPickup((int)x,(int)y, ID.Pickup,handler));
					handler.removeObject(this);
					}
					else{
					handler.removeObject(this);
					}
				}
			}
			if (tempObject.getId() == ID.Player ){
				if(getBounds().intersects(tempObject.getBounds())){
					if(CharacterSelect.player == 0){
						Player1.loseLife();
					}
					if(CharacterSelect.player == 1){
						Player2.loseLife();
					}
					if(CharacterSelect.player == 2){
						Player3.loseLife();

					}
				}
			}
		}
	}	
	
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	
	public void tick() {
		System.out.println(x + " " + y);
		if(!spawned){
			spawnTimer--;
			if(spawnTimer == 0){
				spawned = true;
			}
		}
		if(spawned){
		x += velX;
		y += velY;
		
		timer--;
		
		if (timer == 0){
		handler.addObject(new BasicEnemyBullet(x, y, 0, 1, ID.EnemyBullet, handler));
		timer = 75;
		}
		
//		if(existTimer == 0){
//		handler.removeObject(this);
//		}
		collision();
		
		
		if(y < 0 || y >= Game.HEIGHT+10)
			handler.removeObject(this);
		if(x <= 200 || x >= Game.WIDTH-200){
			handler.removeObject(this);
		}
		}
 
	}

	public void render(Graphics2D g2d) {
		
		if(!spawned && x>Game.WIDTH-400 && y<Game.HEIGHT-50){
			alpha+=0.0004f;
			if(alpha>0.99f){
				alpha = 0.3f;
			}
			g2d.setComposite(makeComposite(alpha));
			g2d.drawImage(warning, (int)x-100, (int)y, 125, 50, null);
			g2d.setComposite(makeComposite(1));
		}
		
		if(!spawned && x<210 && y>Game.HEIGHT-50){
			alpha+=0.0004f;
			if(alpha>0.99f){
				alpha = 0.3f;
			}
			g2d.setComposite(makeComposite(alpha));
			g2d.drawImage(warning, (int)x-100, (int)y, 125, 50, null);
			g2d.setComposite(makeComposite(1));
		}
		
		if(!spawned && y>0 && x>199 && x<Game.WIDTH-310){
			alpha+=0.0004f;
			if(alpha>0.99f){
				alpha = 0.3f;
			}
			g2d.setComposite(makeComposite(alpha));
			g2d.drawImage(warning, (int)x, (int)y-25, 125, 50, null);
			g2d.setComposite(makeComposite(1));
		}
		
		if (spawned){
		g2d.setColor(Color.white);
		g2d.fillRect((int)x, (int)y, 30, 30);
		
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x, (int)y, 30, 30);
		}
		}
		
	}
	
	

}