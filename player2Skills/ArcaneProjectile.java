package com.game.player2Skills;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;

public class ArcaneProjectile extends GameObject {

	Handler handler;

	private Image skill;

	private int cooldown = 10000; //in ms --> 1000 = 1 second. Assigned in the constructor unless unaffected by levels.
	private int level;
	
	private static String skillName = "Arcane Projectile";
	private static String[] skillDescription = new String[3]; //determined in skillDescription method
	private static int skillID = 211;
	public static int slot; //skillHandler will assign a skill slot/keybind
	
	private static int level1Duration = 0;
	private static int level2Duration = 0;
	private static int level3Duration = 0;
	private static int resultDuration; //for getDuration return
	private static float level1Cooldown = 10000;
	private static float level2Cooldown = 90000;
	private static float level3Cooldown = 80000;
	private static float resultCooldown;
	

	private long timeNow; //current system time assigned in tick();

	public ArcaneProjectile(float x, float y, Handler handler, ID Id, int level) {
		super(x, y, Id);
		this.handler = handler;
		this.level = level;

		this.x = x;
		this.y = y;

	}

	public void tick() {

		collision();
		timeNow = System.currentTimeMillis();

	}

	public void render(Graphics2D g2d) {

	}

	//Collisions
	public void collision() {
		for (int i = 0; i < handler.object.size(); i++) {

			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.EnemyBullet) {
				if (getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(tempObject);
				}
			}

		}
	}

	//Rectangle bounds for collisions
	public Rectangle getBounds() {
		return new Rectangle((int) Player1.playerX - 38, (int) Player1.playerY - 24, 110, 110);
	}

	//cooldown getter, needed by skillHandler and the Player class to handle cooldowns
	public int getCooldown() {
		return cooldown;
	}

	//static cooldown getter for skillSelect class tooltip rendering
	public static float getCooldown(int level) {

		switch (level) {
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
	
	public static String[] getSkillDescription(){
		
		skillDescription [0] = "Launches a fast moving";
		skillDescription [1] = "arcane projectile";
		skillDescription [2] = "";
		return skillDescription;
	}
	
	public static String[] getSkillDescription(int level){
		
		switch(level){
		case 2:
			skillDescription [0] = "";
			skillDescription [1] = "";
			skillDescription [2] = "";
			break;
		case 3:
			skillDescription [0] = "";
			skillDescription [1] = "";
			skillDescription [2] = "";
			break;
		default:
			skillDescription [0] = "";
			skillDescription [1] = "";
			skillDescription [2] = "";
			break;
		}

		return skillDescription;
	}

	//remove skill from existance
	public void removeSkill() {
		handler.removeObject(this);
	}

	//Alpha composite

	//	private AlphaComposite makeComposite(float alpha){
	//		int type = AlphaComposite.SRC_OVER;
	//		return(AlphaComposite.getInstance(type, alpha));
	//		
	//	}

}
