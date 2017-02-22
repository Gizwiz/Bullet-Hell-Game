package com.game.player1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.MainSettings;
import com.game.engine.AudioPlayer;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class BasicPlayerBullet extends GameObject{
	
	private Handler handler;
	private BufferedImage basicPlayerBullet;
	private AudioPlayer bullet;
	public BasicPlayerBullet(float x, float y, float velX, float velY, ID Id, Handler handler) {
		super(x, y, velX, velY, Id);
		
		this.handler = handler;
		try {
			basicPlayerBullet = ImageIO.read(new File("bin/used/basicPlayerBullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(MainSettings.music == true){
			
		/*
		 * 
		 * 
		 * MEMORY LEAK !!!!!!!!!!!!!!!
		 * DO NOT USE AUDIOPLAYER!!!!!
		 * 
		 * 
		 */
//		bullet = new AudioPlayer("/usedMusic/click.mp3");
//		bullet.play();
		}
	}
	public Rectangle getBounds(){
		return new Rectangle((int) x, (int) y, 25, 25);
	}
	public void collision(){
		
	}	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0){
			handler.removeObject(this);
		}
		if( x > Game.WIDTH-300){
			handler.removeObject(this);
		}
		collision();
 
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(basicPlayerBullet, (int)x,(int)y,25,25,null);
//		g2d.setColor(Color.blue);
//		g2d.fillRect((int) x,(int) y, 16, 16);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x,(int)y, 25, 25);
		}
	}
	
	

}