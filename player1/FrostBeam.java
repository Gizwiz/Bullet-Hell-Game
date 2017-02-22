package com.game.player1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.MainSettings;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class FrostBeam extends GameObject {
	private Handler handler;
	private Image frostbeam;
	private int length = 10;
	
	public FrostBeam (float x, float y, ID Id, Handler handler){
		super(x, y, Id);
		this.handler = handler;
		velY=0;
		try{
			frostbeam = ImageIO.read(new File("bin/used/frostbeam1.png"));
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}


	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y-length, 25, 225);
	}
	
	
	public void tick() {
	y-=velY;
	if(y <= 0){
		handler.removeObject(this);
	}
	length+=15;
	if(length >= 500){
		velY=15;
	}

	}

	public void render(Graphics2D g2d) {
		g2d.drawImage(frostbeam, (int)x, (int)y, 25, -length, null);
		g2d.setColor(Color.red);
		if(MainSettings.showHitBoxes){
		g2d.drawRect((int) x, (int) y-length, 25, 225);
		}
	}


}
