	package com.game.displays;

	import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;
import com.game.levels.LevelOneEasy;
import com.game.levels.LevelTwoEasy;
import com.game.levels.TestLevel;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


	public class GameOver extends KeyInput implements KeyListener {
		public static int cursorY = 210;
		Handler handler;
		private BufferedImage cursor;
		private Image[] buttons =  new Image[3];
		public GameOver(Handler handler) {
			super(handler);
			this.handler = handler;
			
			try {
				cursor = ImageIO.read(new File("bin/used/arrowRight.png"));
				buttons[0] = ImageIO.read(new File("bin/used/button-left.png"));
				buttons[1] = ImageIO.read(new File("bin/used/button-middle.png"));
				buttons[2] = ImageIO.read(new File("bin/used/button-right.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		public void tick(){
			handler.clearEnemies();
			
			if(keySix){
				if(cursorY == 210){
					handler.clearEnemies();
					keySix = false;
					Game.gameState = STATE.MainMenu;			
				}
				if(cursorY == 310){
					keySix = !keySix;
					Pause.resetLevel = true;
					Game.gameState = STATE.Level;
				}
//				if(cursorY == 310){
//					keySix = false;
//					if(MainSettings.currentLevel == 1){
//						handler.clearEnemies();
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
//						LevelOneEasy.reset=true;
////						Game.gameState = STATE.LevelOne;
//					}
//					if(MainSettings.currentLevel == 2){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
//						LevelTwoEasy.reset=true;
////						Game.gameState = STATE.LevelTwo;
//					}
//					if(MainSettings.currentLevel == 3){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelThree;
//					}
//					if(MainSettings.currentLevel == 4){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelFour;
//					}
//					if(MainSettings.currentLevel == 5){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelFive;
//					}
//					if(MainSettings.currentLevel == 6){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelSix;
//					}
//					if(MainSettings.currentLevel == 7){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelSeven;
//					}
//					if(MainSettings.currentLevel == 8){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelEight;
//					}
//					if(MainSettings.currentLevel == 9){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelNine;
//					}
//					if(MainSettings.currentLevel == 10){
//						keySix = false;
//						Player1.playerScore = 0;
//						Player2.playerScore = 0;
//						Player3.playerScore = 0;
////						Game.gameState = STATE.LevelTen;
//					}
					if(MainSettings.currentLevel == 999){
						handler.clearEnemies();
						keySix = false;
						Player1.playerScore = 0;
						Player2.playerScore = 0;
						Player3.playerScore = 0;
						TestLevel.reset = true;
						Game.gameState = STATE.TestLevel;
					}
				}
			if(keyOne){keyOne = false; cursorY -= 100; if(cursorY < 210){cursorY = 410;}}
			if(keyTwo){keyTwo = false; cursorY += 100; if(cursorY > 410){cursorY = 210;}}
		}

		public void render(Graphics2D g2d){
			Font fnt1 = new Font("Trebuchet MS", 1, 50);
			g2d.setColor(Color.white);
			g2d.setFont(fnt1);
			g2d.drawString("Game Over", Game.WIDTH/2-110, 50);
			g2d.drawString("Main Menu", Game.WIDTH/2-110, 250);
			g2d.drawString("Retry", Game.WIDTH/2-55, 350);
			g2d.drawString("WAT", Game.WIDTH/2-45, 450);
			g2d.setColor(Color.white);
			g2d.drawImage(buttons[0], Game.WIDTH/2-126, 200, 16, 71, null);
			g2d.drawImage(buttons[1], Game.WIDTH/2-110, 200, 250, 71, null);
			g2d.drawImage(buttons[2], Game.WIDTH/2+140, 200, 16, 71, null);

			g2d.drawImage(buttons[0], Game.WIDTH/2-126, 300, 16, 71, null);
			g2d.drawImage(buttons[1], Game.WIDTH/2-110, 300, 250, 71, null);
			g2d.drawImage(buttons[2], Game.WIDTH/2+140, 300, 16, 71, null);
			
			g2d.drawImage(buttons[0], Game.WIDTH/2-126, 400, 16, 71, null);
			g2d.drawImage(buttons[1], Game.WIDTH/2-110, 400, 250, 71, null);
			g2d.drawImage(buttons[2], Game.WIDTH/2+140, 400, 16, 71, null);
			
			g2d.drawImage(cursor, Game.WIDTH/2-195, cursorY, 55, 55, null);
//			g2d.drawRect(175, 313, 25, 25);
//			g2d.drawRect(175, 413, 25, 25);
//			g2d.drawRect(175, 513, 25, 25);
			
		}

	}

