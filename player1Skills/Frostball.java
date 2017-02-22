package com.game.player1Skills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;

public class Frostball extends GameObject{
	/*
	 * Frostball will move for a set distance and explode into smaller bullets with random direction and velocity.
	 * Level affects the amounts and max speeds of main and side projectiles.
	 * cooldown 20s = 20 000ms
	 */
	
	Handler handler;
	
	private Image skill;
	
	private static String skillName = "Frostball";
	private static String[] skillDescription = new String[3];
	private static int skillID = 131;
	public static int slot; //skillHandler will assign a skill slot/keybind
	
	private int cooldown = 20000; //in ms --> 1000 = 1 second. Assigned in the constructor unless unaffected by levels.
	private static float level1Duration;
	private static float level2Duration;
	private static float level3Duration;
	private static float resultDuration; //for getDuration return
	private static float level1Cooldown = 60000;
	private static float level2Cooldown = 50000;
	private static float level3Cooldown = 42500;
	private static float resultCooldown; //for getcooldown retur
	private int level;
	private int explodeAmount; //determines into how many projectiles the main ball will break into
	
	private long spawnTime;
	private long timeNow; //current system time assigned in tick();
	private long explode;
	
	private boolean exists = true; //check for main bullet showing;
	
	private float velY = 2;
	
	private float sideVelY, sideVelX;
	
	
	public Frostball(float x, float y, Handler handler, ID Id, int level){
		super(x, y, Id);
		this.handler=handler;
		this.level=level;
		
		this.x=x;
		this.y=y;
		
		level = 1;
		
		if(level == 1){
			explode = 1500;
			explodeAmount = 10;  
			level1Duration = (float)explode;
			velY = 2;
		}
		if(level == 2){
			explode = 1000;
			explodeAmount = 25;
			velY = 3;
		}
		if(level == 3){
			explode = 600;
			explodeAmount = 60;
			velY = 5;
		}
		spawnTime = System.currentTimeMillis();
		//SET COOLDOWN TO 0 FOR TESTING PURPOSES ONLY!!
		cooldown = 0;
		
	}
	
	public void tick(){
		collision();
		
		y -= velY;
		
		if(y <= 10){
			velY *= -1;
		}
		if(y > Game.HEIGHT-75){
			velY *= -1;
		}
		
		
		
		timeNow = System.currentTimeMillis();
		if(timeNow - spawnTime > explode && exists){
			exists = !exists;
			for(int i = 0; i<explodeAmount*2; i++){
				handler.addObject(new FrostballSide( x, y, ID.PlayerBullet, handler));
			}
			removeSkill();
		}
		
	}
	
	public void render(Graphics2D g2d){
		
		if(exists){
		g2d.setColor(Color.red);
		g2d.fillRect((int)x, (int)y, 100, 100);
		}
		
	}
	
	//Collisions
	public void collision(){
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyBullet){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(tempObject);
				}
			}

		}
	}
	
	//Rectangle bounds for collisions
	public Rectangle getBounds() {
		return new Rectangle((int)Player1.playerX-38, (int)Player1.playerY-24, 110, 110);
	}
	
	//cooldown getter, needed by skillHandler and the Player class to handle cooldowns
	public int getCooldown(){
		return cooldown;
	}
		
	public static float getCooldown(int level){
		
		switch(level){
		case 2:
			resultCooldown = level2Cooldown;
			break;
		case 3:
			resultCooldown = level3Cooldown;
			break;
		default:
			resultCooldown = level1Cooldown;
			break;
		}
		
		return resultCooldown;
		
	}
	
	public static float getDuration(int level) {
		switch (level) {
		case 2:
			resultDuration = level2Duration;
			break;
		case 3:
			resultDuration = level3Duration;
			break;
		default:
			resultDuration = level1Duration;
			break;
		}

		return resultDuration;
	}
	
	public static String getSkillName(){
		return skillName;
	}
	
	public static String[] getSkillDescription(int level){
		
		switch(level){
		case 2:
			skillDescription [0] = "A moving ball of frost.";
			skillDescription [1] = "Explodes into more random";
			skillDescription [2] = "moving projectiles.";
			break;
		case 3:
			skillDescription [0] = "A moving ball of frost.";
			skillDescription [1] = "Explodes into even more";
			skillDescription [2] = "random moving projectiles.";
			break;
		default:
			skillDescription [0] = "A moving ball of frost.";
			skillDescription [1] = "Explodes into random";
			skillDescription [2] = "moving projectiles.";
			break;
		}
		return skillDescription;
	}
	
	//remove skill from existance
	public void removeSkill(){
		handler.removeObject(this);
	}
	
	//Alpha composite
	
//	private AlphaComposite makeComposite(float alpha){
//		int type = AlphaComposite.SRC_OVER;
//		return(AlphaComposite.getInstance(type, alpha));
//		
//	}
	
}
