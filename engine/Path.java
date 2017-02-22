package com.game.engine;

public class Path {
	private float x, y;
	private float xx, yy;
	private float controlPointX, controlPointY;
	private float velocity;
	private double d = 1;
	private double deltaX, deltaY;
	private double newDeltaX, newDeltaY;
	private double curTheta, deltaTheta, newTheta;
	private double radius = 1;
	
	
	public Path(float x, float y, float xx, float yy, float controlPointX, float controlPointY, float velocity){
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
		this.controlPointX = controlPointX;
		this.controlPointY = controlPointY;
		this.velocity = velocity;

		calculatePath(x, y, xx, yy, controlPointX, controlPointY);
	}
	
	public void calculatePath(float x, float y, float xx, float yy, float controlPointX, float controlPointY){
		if(controlPointX > x){
			x+=velocity;
		}
		if(controlPointX < x){
			x-=velocity;
		}
		
		if(controlPointY > y){
			y+=velocity;
		}
		if(controlPointY < y){
			y-=velocity;
		}
		
		

	}
	
	public float moveToX(float newX){

		return newX;
		
	}
	
	public float moveToY(float newY){

		return newY;
	}
}
