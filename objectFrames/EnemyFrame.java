package com.game.objectFrames;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.pickups.BombPickup;
import com.game.pickups.PowerPickup;
import com.game.pickups.ScorePickup;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


/*
Description of enemy
 */

public class EnemyFrame extends GameObject{
	
	private Handler handler;
	private int shootTimer = 15;
	private int existTimer = 750;
	private int moveTimer = 300;
	private Random r =new Random();
	private Random t =new Random();
	private int hasPickupPower, hasPickupBomb;
	private boolean hasPickupP = false; //POWER PICKUP
	private boolean hasPickupB  =false; //BOMB PICKUP


	public EnemyFrame(float x, float y, float velX, float velY, ID id, Handler handler) {
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

	
	}
	public Rectangle getBounds(){
		return new Rectangle((int) x, (int) y, 30, 30);
	}
	public void collision(){
		
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
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
					handler.addObject(new ScorePickup((int)x,(int)y, ID.Pickup,handler));
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
			if (tempObject.getId() == ID.Player){
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
		
		shootTimer--;
		existTimer--;
		
		//shoot
		if (shootTimer == 0){
		shootTimer = 75;
		}

		
		//restrict movement
		if(y <= 0 || y >= Game.HEIGHT-32)
			handler.removeObject(this);
		if (y>= 250){
			velY = 0;
		}
		if(x <= 200 || x >= Game.WIDTH-330)
			handler.removeObject(this);
		
		//move out of screen to despawn
		if(existTimer <= 0){
			y-=2;
		}

	}

	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect((int)x, (int)y, 30, 30);
		
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x, (int)y, 30, 30);
		}
		
	}
	
	

}