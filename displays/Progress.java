package com.game.displays;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.enums.STATE;
import com.game.levels.LevelOneEasy;
import com.game.levels.LevelTwoEasy;
import com.game.levels.TestLevel;

public class Progress extends GameObject{
	
	private BufferedImage progressicon;
	private Handler handler;
	private long startTime = System.currentTimeMillis();
	private Image currentImage, progressBar;
	private Image[] players = new Image[3];
	private float progTimer;
	private float barTimer;
	private float startY = 760.0f;
	private int red = 255;
	private double delta = 0;
	private float deltaY;
	public static boolean remove = false;
	public static boolean bossEngaged;
	
	public Progress(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, velY, velY, id);
		this.handler = handler;
		velY = startY/progTimer;
		
		if(Game.gameState == STATE.Level){
			progTimer = MainSettings.currentLevelTime;
			barTimer = MainSettings.currentLevelTime;
		}

//		if(Game.gameState == STATE.LevelThree){
//			progTimer = LevelThree.levelTime;
//			barTimer = LevelThree.levelTime;
//		}
//		if(Game.gameState == STATE.LevelFour){
//			progTimer = LevelFour.levelTime;
//			barTimer = LevelFour.levelTime;
//		}
//		if(Game.gameState == STATE.LevelFive){
//			progTimer = LevelFive.levelTime;
//			barTimer = LevelFive.levelTime;
//		}
//		if(Game.gameState == STATE.LevelSix){
//			progTimer = LevelSix.levelTime;
//			barTimer = LevelSix.levelTime;
//		}
//		if(Game.gameState == STATE.LevelSeven){
//			progTimer = LevelSeven.levelTime;
//			barTimer = LevelSeven.levelTime;
//		}
//		if(Game.gameState == STATE.LevelEight){
//			progTimer = LevelEight.levelTime;
//			barTimer = LevelEight.levelTime;
//		}
//		if(Game.gameState == STATE.LevelNine){
//			progTimer = LevelNine.levelTime;
//			barTimer = LevelNine.levelTime;
//		}
//		if(Game.gameState == STATE.LevelTen){
//			progTimer = LevelTen.levelTime;
//			barTimer = LevelTen.levelTime;
//		}
		
		if(Game.gameState == STATE.TestLevel){
			progTimer = TestLevel.levelTime;
			barTimer = TestLevel.levelTime;
		}
		try {
			progressicon=ImageIO.read(new File("bin/used/progressicon.png"));
			currentImage = progressicon;
			progressBar = ImageIO.read(new File("bin/used/buttonWholeVert.png"));
			
			players[0] = ImageIO.read(new File("bin/used/player1DOWN.png"));
			players[1] = ImageIO.read(new File("bin/used/player2DOWN.png"));
			players[2] = ImageIO.read(new File("bin/used/player3DOWN.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void tick() {
		if(!bossEngaged){
		progTimer--;

		if (red>1){
			red = (int) (progTimer/100);
			}
		long now = System.currentTimeMillis();
		delta = (now / startTime);
		if (delta == 1.0){
			deltaY -= ((90-850)/barTimer);
			//if deltaY is bigger than progress bar make it exact
			if(deltaY >= Game.HEIGHT-(Game.HEIGHT/5)){ deltaY = Game.HEIGHT-(Game.HEIGHT/5); }
			delta--;
		}
		}
	}

	public void render(Graphics2D g2d) {
		g2d.setColor(new Color(red,75,0));
		g2d.fillRect((int)10,Game.HEIGHT-(Game.HEIGHT/9), 25, -(int)deltaY);
		g2d.setColor(Color.white);
//		g2d.drawRect(3,90, 25, 760);
		g2d.drawImage(progressBar, 10,70,25,Game.HEIGHT-(Game.HEIGHT/6),null);
		if(CharacterSelect.player == 0){
			g2d.drawImage(players[0], (int) 10, -(int)deltaY+Game.HEIGHT-(Game.HEIGHT/7), 25, 35, null);
		}
		if(CharacterSelect.player == 1){
			g2d.drawImage(players[1], (int) 10, -(int)deltaY+Game.HEIGHT-(Game.HEIGHT/7), 25, 35, null);
		}
		if(CharacterSelect.player == 2){
			g2d.drawImage(players[2], (int) 10, -(int)deltaY+Game.HEIGHT-(Game.HEIGHT/7), 25, 35, null);
		}
		
//		g2d.drawImage(currentImage, (int) 4, -(int)deltaY+835, 25, 25, null);
	}

	public Rectangle getBounds() {
		return null;
	}
	

}