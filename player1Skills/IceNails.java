package com.game.player1Skills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class IceNails extends GameObject{
	Handler handler;
	
	private Image skill;
	
	private static String skillName = "Ice Nail";
	private static String[] skillDescription = new String[3];
	private static int skillID = 121;
	public static int slot;
	private int cooldown = 15000;
	private static int level1Duration = 0;
	private static int level2Duration = 0;
	private static int level3Duration = 0;
	private static int resultDuration; //for getDuration return
	private static int level1Cooldown = 10000;
	private static int level2Cooldown = 15000;
	private static int level3Cooldown = 20000;
	private static int resultCooldown; //for getcooldown return
	private int level;
	private int maxTime = 300; //max time to start moving
	private long duration = System.currentTimeMillis();
	private long timeNow;
	private boolean show = true;
	public static boolean exists;
	private Random r = new Random();
	private int moveTimer;
	private int moveTimer2;
	private int moveTimer3;
	private int moveTimer4;
	private int moveTimer5;
	private int i;
	private double velY, velY2, velY3, velY4, velY5;
	private int posX, posY, posY2, posY3, posY4, posY5;
		
	public IceNails(float x, float y, Handler handler, ID Id, int level){
		super(x, y, Id);
		System.out.println("Creating ice nails level: " + level);
		if(level == 1){
			cooldown = 10000;
		}
		if(level == 2){
			cooldown = 15000;
		}
		if(level == 3){
			cooldown = 20000;
		}
		this.handler = handler;
		this.level = level;
		try {
			skill = ImageIO.read(new File("bin/used/skills/skill121.png"));
		} catch (Exception e) {
		}

		this.posX = (int) x;
		this.posY = (int) y;
		this.posY2 = (int) y;
		this.posY3 = (int) y;
		this.posY4 = (int) y;
		this.posY5 = (int) y;
		System.out.println("level 1 rancdoms");
		moveTimer = r.nextInt(maxTime);
		System.out.println(moveTimer);
		
		if(level == 2){
			moveTimer2 = r.nextInt(maxTime);
			moveTimer3 = r.nextInt(maxTime);
			System.out.println("level 2 rancdoms created");
		}
		if(level == 3){
			moveTimer2 = r.nextInt(maxTime);
			moveTimer3 = r.nextInt(maxTime);
			moveTimer4 = r.nextInt(maxTime);
			moveTimer5 = r.nextInt(maxTime);
		}

	}
	public void removeSkill(){
		handler.removeObject(this);
	}


	public void tick() {
		collision();
//		getBounds();
		timeNow = System.currentTimeMillis();
		if(timeNow - duration >= 1000){
			duration = System.currentTimeMillis();

//			exists = !exists;
//			handler.removeObject(this);
		}
		if(level == 1){
		moveTimer--;
		if(moveTimer <= 0){
			if(velY < 10){
			velY+=0.15;
			}
			posY-=velY;
		}
		if(posY < -50){
			handler.removeObject(this);
		}
		}
		
		if(level == 2){
			moveTimer--;
			moveTimer2--;
			moveTimer3--;
			
		if(moveTimer <= 0){
			if(velY < 10){
				velY+=0.15;
			}
			posY-=velY;
		}
		
		if(moveTimer2 <= 0){
			if(velY2 < 10){
				velY2+=0.15;
			}
			posY2-=velY2;
		}
		
		if(moveTimer3 <= 0){
			if(velY3 < 10){
				velY3+=0.15;
			}
			posY3-=velY3;
		}
			
		if(posY < -50 && posY2 < -50 && posY3 < -50){
			handler.removeObject(this);
		}
		
		if (i<3){
			i++;
		}
		if(i>2){
			i=0;
		}
		}
		
		
		if(level == 3){
			moveTimer--;
			moveTimer2--;
			moveTimer3--;
			moveTimer4--;
			moveTimer5--;
			
		if(moveTimer <= 0){
			if(velY < 10){
				velY+=0.15;
			}
			posY-=velY;
		}
		
		if(moveTimer2 <= 0){
			if(velY2 < 10){
				velY2+=0.15;
			}
			posY2-=velY2;
		}
		
		if(moveTimer3 <= 0){
			if(velY3 < 10){
				velY3+=0.15;
			}
			posY3-=velY3;
		}
		if(moveTimer4 <=0){
			if(velY4 < 10){
				velY4+=0.15;
			}
			posY4-=velY4;
		}
		if(moveTimer5 <=0){
			if(velY5 < 10){
				velY5+=0.15;
			}
			posY5-=velY5;
		}
			
		if(posY < -50 && posY2 < -50 && posY3 < -50 && posY4 <- 50 && posY5 < -50){
			handler.removeObject(this);
		}
		
		if (i<5){
			i++;
		}
		if(i>4){
			i=0;
		}
		}

		

	}

	public void render(Graphics2D g2d) {

		
		if(level == 1){
		
		g2d.setColor(Color.RED);
		g2d.drawRect(posX, posY-3, 30, 60);
		g2d.drawImage(skill, posX, posY, 25, 55, null);
		}
		
		if(level == 2){
			
		g2d.setColor(Color.RED);

		g2d.drawImage(skill, posX, posY, 25, 55, null);
		g2d.drawImage(skill, posX-100, posY2+100, 25, 55, null);
		g2d.drawImage(skill, posX+100, posY3+100, 25, 55, null);
		
		if(i==0){
			g2d.drawRect(posX, posY-3, 30,60);
		}
		if(i==1){
			g2d.drawRect(posX-100, posY2+100, 30, 60);
		}
		if(i==2){
			g2d.drawRect(posX+100, posY3+100, 30, 60);	
		}

		}
		
		if(level == 3){
			
		g2d.setColor(Color.RED);

		g2d.drawImage(skill, posX, posY, 25, 55, null);
		g2d.drawImage(skill, posX-100, posY2+100, 25, 55, null);
		g2d.drawImage(skill, posX+100, posY3+100, 25, 55, null);
		g2d.drawImage(skill, posX-200, posY4-100, 25, 55, null);
		g2d.drawImage(skill, posX+200, posY5-50, 25, 55, null);
		
		if(i==0){
			g2d.drawRect(posX, posY-3, 30,60);
		}
		if(i==1){
			g2d.drawRect(posX-100, posY2+100, 30, 60);
		}
		if(i==2){
			g2d.drawRect(posX+100, posY3+100, 30, 60);	
		}
		if(i==3){
			g2d.drawRect(posX-200, posY4-100, 30, 60);
		}
		if(i==4){
			g2d.drawRect(posX+200, posY5-50, 30, 60);
		}

		}

		
	}

	public void collision(){

	}
	public Rectangle getBounds() {
//		if(level == 1){
//			return new Rectangle(posX, posY-3, 30, 60);
//		}
//		if(level == 2){
//			if(i==0){
//			return new Rectangle(posX, posY-3, 30,60);
//			}
//			if(i==1){
//			return new Rectangle(posX-100, posY+100, 30, 60);
//			}
//			if(i==2){
//				
//			}
//			
//		}
		
		switch(level){
		
		case 1: return new Rectangle(posX, posY-3, 30, 60);
		case 2: 			
			if(i==0){;
			return new Rectangle(posX, posY-3, 30,60);
			}
			if(i==1){
			return new Rectangle(posX-100, posY2+100, 30, 60);
			}
			if(i==2){
			return new Rectangle(posX+100, posY3+100, 30, 60);	
			}
		case 3:
			if(i==0){
			return new Rectangle(posX, posY-3, 30,60);
			}
			if(i==1){
			return new Rectangle(posX-100, posY2+100, 30, 60);
			}
			if(i==2){
			return new Rectangle(posX+100, posY3+100, 30, 60);	
			}
			if(i==3){
			return new Rectangle(posX-200, posY4-100, 30, 60);
			}
			if(i==4){
			return new Rectangle(posX+200, posY5-50, 30, 60);
			}
		default: 
			return new Rectangle(posX, posY-3, 30, 60);
		}

	}

	public int getCooldown(){
		return cooldown;
	}
	
	public static int getCooldown(int level){
		
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
	
	public static int getDuration(int level) {
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
			skillDescription [0] = "Launches 2 icy nails";
			skillDescription [1] = "that will each accelerate after";
			skillDescription [2] = "a random delay (max 30s)";
			break;
		case 3:
			skillDescription [0] = "Launches 3 icy nails";
			skillDescription [1] = "that will each accelerate after";
			skillDescription [2] = "a random delay (max 30s)";
			break;
		default:
			skillDescription [0] = "Launches an icy nail";
			skillDescription [1] = "that will accelerate after";
			skillDescription [2] = "a random delay (max 30s)";
			break;
		}

		return skillDescription;
	}


}
