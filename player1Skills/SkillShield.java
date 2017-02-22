package com.game.player1Skills;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

public class SkillShield extends GameObject{
	Handler handler;
	
	private Image skill;
	
	public static int slot;
	
	private static String skillName = "Frost Shield";
	private static String[] skillDescription = new String[3];
	private static int skillID = 111;
	private int level; //DETERMINES VERSION OF SKILL
	private float cooldown = 60000;
	private static float level1Duration = 500;
	private static float level2Duration = 615;
	private static float level3Duration = 725;
	private static float resultDuration; //for getDuration return
	private static float level1Cooldown = 60000;
	private static float level2Cooldown = 50000;
	private static float level3Cooldown = 42500;
	private static float resultCooldown; //for getcooldown return
	
	private long duration = System.currentTimeMillis();
	private long timeNow;
	private boolean show = true;
	public static boolean exists;
	private float alpha = 1.0f;
		
	public SkillShield(float x, float y, Handler handler, ID Id, int level){
		super(x, y, Id);
		this.handler = handler;
		this.level = level;
		
		try {
			skill = ImageIO.read(new File("bin/used/skills/skill111.png"));
		} catch (Exception e) {
		}
		
		if(level == 2){
			cooldown = level2Cooldown;
		}
		if(level == 3){
			cooldown = level3Cooldown;
		}

	}
	public void removeSkill(){
		handler.removeObject(this);
	}

	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
		
	}

	public void tick() {

		collision();
		timeNow = System.currentTimeMillis();
		if(timeNow - duration >= 500){
			duration = System.currentTimeMillis();

//			exists = !exists;
			handler.removeObject(this);
		}
		if(alpha >0.06f){
		alpha-=0.05f;
		}
		
	}

	public void render(Graphics2D g2d) {

		g2d.setColor(Color.RED);
		g2d.drawRect((int)Player1.playerX-38, (int)Player1.playerY-24, 110, 110);
		g2d.setComposite(makeComposite(alpha));
		g2d.drawImage(skill, (int)Player1.playerX-45, (int)Player1.playerY-30, 125, 125, null);
		g2d.setComposite(makeComposite(1.0f));
//		g2d.drawOval((int)Player1.playerX, (int)Player1.playerY, 25, 25);
		
	}
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
	
	public Rectangle getBounds() {
		return new Rectangle((int)Player1.playerX-38, (int)Player1.playerY-24, 110, 110);
	}

	public int getCooldown(){
		return (int)cooldown;
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
	
	public static String[] getSkillDescription(){
		
		skillDescription [0] = "Creates a shield of frost,";
		skillDescription [1] = "granting invulnerability";
		skillDescription [2] = "from most damage.";
		return skillDescription;
	}


}
