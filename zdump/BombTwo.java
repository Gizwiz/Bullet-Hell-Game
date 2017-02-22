package com.game.zdump;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.MainSettings;
import com.game.engine.AudioPlayer;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class BombTwo extends GameObject{
	private Handler handler;
	private BufferedImage bomb;
	private AudioPlayer bombSound;
	private int timer = 450;
	private int sideTimer = 15;
	public static int bombTimer = 25;
	public BombTwo(float x, float y, ID id, float velX, float velY, Handler handler) {
		/*///////////////////////////////////////////////////////
		!!!!!!!!!!!!!!			BOMBS			!!!!!!!!!!!!!!
		!!!!!!!!!!!!!!			ARE				!!!!!!!!!!!!!!
		!!!!!!!!!!!!!!			OBJECTS			!!!!!!!!!!!!!!
		///////////////////////////////////////////////////////*/
		super(x, y, velY, velY, id);
		this.handler = handler;
		try {
			bomb = ImageIO.read(new File("bin/used/bomb1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(MainSettings.music == true){
		bombSound = new AudioPlayer("/DeathFlash.mp3");
		bombSound.play();
		}
	}
	public Rectangle getBounds(){
		return new Rectangle((int) x-200, (int) y-150, 400, 400);
	}
	public void collision(){

	}	
	public void tick() {
		timer--;
		sideTimer--;
		if(timer == 0){
			handler.removeObject(this);
		}
		if(sideTimer==0){
		handler.addObject(new BombTwoSide((int)x, 900, ID.Bomb, 0, 2, handler));
//		sideTimer = 3;
		}
 
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.red);
		g2d.drawRect((int) x-200, (int) y-150, 400, 400);
		g2d.drawImage(bomb, (int)x-200, (int)y-150, 400, 400, null);
//		g2d.setColor(new Color(75,0,150));
//		g2d.fillRect((int) x,(int) y, 16, 16);
		
	}
	
	
}
