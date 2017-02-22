package com.game.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.enums.ID;

public abstract class GameObject {
	protected float x, y;
	protected float xx, yy;
	protected double controlPoint;
	protected ID id;
	protected float velX, velY;
	protected float warningTime;
	
	public GameObject(){
		
	}
	public GameObject(float x, float y, ID Id){
		this.x=x;
		this.y=y;
		this.id=Id;
	}
	public GameObject(float x, float y, float velX, float velY, ID Id){
		this.x = x;
		this.y = y;
		this.id = Id;
		this.velX = velX;
		this.velY = velY;
	}
	public GameObject(float x, float y, float velX, float velY, float warningTime, ID Id){
		this.x = x;
		this.y = y;
		this.id = Id;
		this.velX = velX;
		this.velY = velY;
		this.warningTime = warningTime;
	}
//	public GameObject(float x, float y, float xx, float yy, double controlPoint, ID Id){
//		this.x = x;
//		this.y = y;
//		this.xx = xx;
//		this.yy = yy;
//		this.controlPoint = controlPoint;
//		this.id = Id;
//	}
	
	public abstract void tick();
	public abstract void render(Graphics2D g2d);
	public abstract Rectangle getBounds();
	
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public ID getId(){
		return id;
	}
	public void setVelX(float velX){
		this.velX = velX;
	}
	public void setVelY(float velY){
		this.velY= velY;
	}
	public float getVelX(){
		return velX;
	}
	public float getVelY(){
		return velY;
	}
}

