package com.game.player1Skills;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.displays.GameHUD;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;

public class FrostballSide extends GameObject {

	Handler handler;
	private Image bullet;
	private Random r = new Random(); //velX, 0 -> 2
	private Random t = new Random(); //velY, 0 -> 2
	private Random f = new Random(); //direction of travel, pos or neg
	private Random g = new Random(); //despawn timer

	private float velX, velY;
	private float maxVelX = 5.0f, maxVelY = 5.0f;
	private float minVelX = 0.0f, minVelY = 0.0f;
	private int dir;
	
	private long spawnTime;
	private long timeNow;
	private float despawnTime;

	public FrostballSide(float x, float y, ID Id, Handler handler) {
		super(x, y, Id);

		this.handler = handler;
		spawnTime = System.currentTimeMillis();

		try {
			bullet = ImageIO.read(new File("bin/used/basicPlayerBullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		velX = r.nextFloat() * (maxVelX - minVelX) + minVelX;
		velY = t.nextFloat() * (maxVelY - minVelY) + minVelY;
		dir = f.nextInt(4);
		despawnTime = g.nextFloat() * 5000;
		Game.log(""+despawnTime);
		if(despawnTime < 1000){
			despawnTime = 1000;
		}
		
		//if dir = 0 do nothing

		//if dir = 1 swap x velocity to negative
		if (dir == 1) {
			velX *= -1;
		}

		//if dir = 2 swap y velocity to negative
		if (dir == 2) {
			velY *= -1;
		}

		//if dir = 3 swap x and y velocity to negative
		if (dir == 3) {
			velX *= -1;
			velY *= -1;
		}
		


	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 25, 25);
	}

	public void collision() {

	}

	public void tick() {
		x += velX;
		y += velY;
		timeNow = System.currentTimeMillis();
		
		if(timeNow - spawnTime > despawnTime){
			handler.removeObject(this);
		}

		if (x <= GameHUD.leftBorderSize) {
			velX *= -1.0f;
		}
		if (x > Game.WIDTH - GameHUD.rightBorderSize - 10) {
			velX *= -1.0f;
		}
		collision();

	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(bullet, (int) x, (int) y, 25, 25, null);
		//		g2d.setColor(Color.blue);
		//		g2d.fillRect((int) x,(int) y, 16, 16);
		if (MainSettings.showHitBoxes) {
			g2d.setColor(Color.red);
			g2d.drawRect((int) x, (int) y, 25, 25);
		}
	}

}
