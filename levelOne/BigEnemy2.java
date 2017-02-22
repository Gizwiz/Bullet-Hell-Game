package com.game.levelOne;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
Big sized enemy that has a continuous slow circular shot pattern.
Has a lot of health.
 */

public class BigEnemy2 extends GameObject{
	
	private Handler handler;
	private int shootTimer = 75;
	private int shootTimer2 = 1;
	private int moveTimer = 100;
	private int existTimer = 2000;
	private int i = 0;
	private double health = 2000;
	private double theta = 0;
	private double theta2 = 0;
	
	public BigEnemy2(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, velX, velY, id);
		this.handler = handler;
	
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x-25, (int)y, 125, 125);
	}
	public void collision(){
		
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (tempObject.getId() == ID.PlayerBullet ||
					tempObject.getId() == ID.Bomb){
				
				//if health is under 1 remove object and give player score and drop pickups
				///Always drop power and bomb pickups if player is eligible
				if(getBounds().intersects(tempObject.getBounds()) && health<0){	
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
					if(PowerPickup.allowDrop == true){
					handler.addObject(new PowerPickup((int)x,(int)y, ID.Pickup,handler));
					handler.removeObject(this);	
					}
					if(BombPickup.allowDrop == true){
					handler.addObject(new BombPickup((int)x,(int)y, ID.Pickup,handler));
					handler.removeObject(this);
					}
					else{
					handler.removeObject(this);
					}
				}
				//else decrease boss health by one
				else{
					health-=1;
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

		collision();
		
		moveTimer--;
		existTimer--;
		shootTimer--;
		shootTimer2--;
		
		if (moveTimer > 0){
			y += velY;
		}
		
		if(shootTimer <= 0 && moveTimer <= 0){
			theta = theta + Math.toRadians(10);
			float a = (float) (x + Math.sin(theta)*300);
			float b = (float) (y + Math.cos(theta)*100);
//			handler.addObject(new BigEnemyBullet2 (a, b+50, 0, 0, ID.EnemyBullet, handler));
//			handler.addObject(new BigEnemyBullet (b+25, y+50, 0, 2, ID.EnemyBullet, handler));
			handler.addObject(new BigEnemyBullet2 (x+75, y, 0, 0, ID.EnemyBullet, handler));
			shootTimer = 75;
		}
//		if(shootTimer2 <= 0 && moveTimer <= 0){
//			handler.addObject(new MiniBossHomingBullet (x, y+50, 0.5f, 1, ID.EnemyBullet, handler));
//			shootTimer2 = 300;
//		}
//			

		
		if(y <= 0 || y >= Game.HEIGHT-32)
			handler.removeObject(this);
		//Do not move past half screen height
		if (y>= Game.WIDTH/2){
			velY = 0;
		}
		if(x <= 200 || x >= Game.WIDTH-330)
			handler.removeObject(this);
//		if(existTimer <= 0){
//			y-=2;
//		}

	}

	public void render(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect((int)x-25, (int)y, 125, 125);
		
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x-25, (int)y, 125, 125);
		}
		
	}
	
	

}