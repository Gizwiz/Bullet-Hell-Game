package com.game.player1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.MainSettings;
import com.game.engine.AudioPlayer;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class BombOne extends GameObject{
	private Handler handler;
	private Image[] bomb = new Image[4]; 
	private int timer = 550;
	private int sideTimer = 25;
	private int loopTimer = 6;
	private int i=0;
	private int cooldown = 30000;
	public static int bombTimer = 25;
	public BombOne(float x, float y, ID id, float velX, float velY, Handler handler) {
		/*///////////////////////////////////////////////////////
		!!!!!!!!!!!!!!			BOMBS			!!!!!!!!!!!!!!
		!!!!!!!!!!!!!!			ARE				!!!!!!!!!!!!!!
		!!!!!!!!!!!!!!			OBJECTS			!!!!!!!!!!!!!!
		///////////////////////////////////////////////////////*/
		super(x, y, velY, velY, id);
		this.handler = handler;

		try {
			bomb[0] = ImageIO.read(new File("bin/used/snowflake1Glow.png"));
			bomb[1] = ImageIO.read(new File("bin/used/snowflake1Glow1.png"));
			bomb[2] = ImageIO.read(new File("bin/used/snowflake1Glow2.png"));
			bomb[3] = ImageIO.read(new File("bin/used/snowflake1Glow3.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(MainSettings.music == true){
		AudioPlayer bombSound = new AudioPlayer("/DeathFlash.mp3");
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
		if(sideTimer==0 && timer>100){
		handler.addObject(new BombOneSide((int)x, Game.HEIGHT-(Game.HEIGHT/1000), ID.Bomb, 0, 2, handler));
		sideTimer=5;
		}
		loopTimer --;
		if(i>=3){
			i=0;
		}

		if (loopTimer ==0){
			loopTimer = 4;
			i++;
			}
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setColor(Color.red);
	
		g2d.drawImage(bomb[i], (int)x-200, (int)y-150, 400, 400, null);

		if(MainSettings.showHitBoxes){
			g2d.setColor(Color.red);
			g2d.drawRect((int) x-200, (int) y-150, 400, 400);
		}
		
	}
	
	public int getCooldown(){
		
		return cooldown;
		
	}
	
	
}
