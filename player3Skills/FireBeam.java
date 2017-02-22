package com.game.player3Skills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class FireBeam extends GameObject{

	Handler handler;
	
	public static int skillId = 311;
	public static int slot;
	
	public static boolean exists;
	
	private int cooldown = 1000;
	private int level;
	private int posX, posY;
	
	private boolean show = true;
	
	private Color red = new Color(200, 200, 200);
	
	public FireBeam(float x, float y, Handler handler, ID Id, int level){
		super(x, y, Id);
		this.handler = handler;
		this.level = level;
		
		this.posX = (int) x;
		this.posY = (int) y;
		
	}
	
	public void removeSkill(){
		handler.removeObject(this);
	}
	
	public void tick(){
	
		
	}
	
	public void render(Graphics2D g2d){
		
		g2d.setColor(red);
		g2d.fillRect(posX, posY, 100, 100);
		
	}
	
	public void collision(){
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.Enemy){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(tempObject);
				}
			}

		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(posX, posY-3, 30, 60);
	}
	
	public int getCooldown(){
		return cooldown;
	}
	
}
