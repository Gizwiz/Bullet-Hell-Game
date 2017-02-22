package com.game.levelOne;

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
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

/*
 Basic Enemy shoots singular bullets on a timer that shoots at player's last known X and Y position.
 Receives parameters on creation to determine velX and velY.
 Can not move past Y=250.
 */

public class BasicEnemy extends GameObject{
	
	private Handler handler;
	private int timer = 15;
	private int existTimer = 750;
	private Random r =new Random();
	private Random t =new Random();
	private int hasPickupPower, hasPickupBomb;
	private boolean hasPickupP = false; //POWER PICKUP
	private boolean hasPickupB = false; //BOMB PICKUP


	public BasicEnemy(float x, float y, float velX, float velY, ID id, Handler handler) {
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
	public void tick() {
		x += velX;
		
		timer--;
		existTimer--;


		if (timer == 0){
		handler.addObject(new BasicEnemyBullet(x, y, 0, 1, ID.EnemyBullet, handler));
		timer = 75;
		}
		
		if(existTimer>650){
			y+=1;
		}
//		if(existTimer == 0){
//		handler.removeObject(this);
//		} 
		collision();
		
		if(y <= 0 || y >= Game.HEIGHT-32)
			handler.removeObject(this);
		if (y>= 200){
			velY = 0;
		}
		if(x <= 200 || x >= Game.WIDTH-330)
			velX *= -1;
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