package com.game.levelOne;

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
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.ID;
import com.game.pickups.BombPickup;
import com.game.pickups.PowerPickup;
import com.game.pickups.ScorePickup;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


/*
Enemy shoots big bullets that kill if player is not phased.
Also shoots 2x normal bullets that travel in diagonal and 1x that travels straight down.

Enemy has shield that disappears if player is phased.
Bomb ignores shield.
 */

public class ShieldEnemy extends GameObject{
	
	private Handler handler;
	private int timer = 215;
	private int timer2 = 175;
	private int existTimer = 750;
	private Random r =new Random();
	private Random t =new Random();
	private int hasPickupPower, hasPickupBomb;
	private boolean hasPickupP = false; //POWER PICKUP
	private boolean hasPickupB  =false; //BOMB PICKUP
	private Image shield;

	public ShieldEnemy(float x, float y, float velX, float velY, ID id, Handler handler) {
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
		try{
		shield = ImageIO.read(new File("bin/used/enemyMagicCircle1.png"));
		}
		catch (IOException e){
			e.printStackTrace();
		}

	
	}
	public Rectangle getBounds(){
		if(KeyInput.keySeven){
		return new Rectangle((int) x, (int) y, 30, 30);
		}
		if(!KeyInput.keySeven){
		return new Rectangle((int)x-55, (int)y-50, 130, 130);
		}
		else{
		return null;
		}
	}
	public void collision(){
		
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			// IF HIT BY BOMB
			if (
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){	
					if(CharacterSelect.player == 0 && !Player1.invulnerable){
						Player1.playerScore+=300;
					}
					if(CharacterSelect.player == 1 && !Player2.invulnerable){
						Player2.playerScore+=300;
					}
					if(CharacterSelect.player == 2 && !Player3.invulnerable){
						Player3.playerScore+=300;
					}
					
					handler.addObject(new ScorePickup((int)x,(int)y, ID.Pickup,handler));
					handler.addObject(new ScorePickup((int)x+20,(int)y-20, ID.Pickup,handler));
					handler.addObject(new ScorePickup((int)x-20,(int)y+20, ID.Pickup,handler));
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
			
			//IF PLAYER IS PHASED REGISTER HITS BY NORMAL SHOTS
			if(KeyInput.keySeven && tempObject.getId() == ID.PlayerBullet){
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
			if(!KeyInput.keySeven && tempObject.getId() == ID.PlayerBullet){
				if(getBounds().intersects(tempObject.getBounds())){	
					handler.removeObject(tempObject);
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
	public void tick() {
		x += velX;
		y += velY;

		collision();
		
		timer--;
		timer2--;
		existTimer--;

		//BIG BULLET
		if (timer == 0){
			handler.addObject(new ShieldEnemyBullet((int)x-25, (int)y+25, 0, 4, ID.EnemyBullet, handler));
		timer = 175;
		}
		//SMALL BULLET
		if(timer2 == 0){
			handler.addObject(new ShieldEnemyBulletSmall((int)x, (int)y, 0, 2, ID.EnemyBullet, handler));
			handler.addObject(new ShieldEnemyBulletSmall((int)x, (int)y, 1, 2, ID.EnemyBullet, handler));
			handler.addObject(new ShieldEnemyBulletSmall((int)x, (int)y, -1, 2, ID.EnemyBullet, handler));
		timer2 = 125;
		}
		
		if(y <= 0 || y >= Game.HEIGHT-32){
			handler.removeObject(this);
		}
		if (y>= 150){
			velY = 0;
		}
		if(x <= 200 || x >= Game.WIDTH-330)
			handler.removeObject(this);
		if(existTimer <= 0){
			y-=1;
		}

	}

	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect((int)x, (int)y, 30, 30);
		if(!KeyInput.keySeven){
		g2d.drawImage(shield, (int)x-85, (int)y-87, 200, 200, null);
		}
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x, (int)y, 30, 30);
		if(!KeyInput.keySeven){
		g2d.drawRect((int)x-55, (int)y-50, 130, 130);
		}
		}
		
	}
	
	

}