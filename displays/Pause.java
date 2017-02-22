package com.game.displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.AudioPlayer;
import com.game.engine.ExperienceHandler;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;
import com.game.levels.TestLevel;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


public class Pause extends KeyInput{

	private Handler handler;
	private Image[] image = new Image[18];
	private Image[] buttons =  new Image[3];
	public static AudioPlayer player;
	private int loopTimer = 6;
	private int i=0;
	public static int cursorX = Game.WIDTH/2-200, cursorY = 175;
	public static boolean resetLevel = false;
	
	public Pause(Handler handler) {
		super(handler);
		this.handler = handler;
		try {
//			cursor = ImageIO.read(new File("resources/used/arrowRight.png"));
			image[0]= ImageIO.read(new File("bin/used/lightingL-0.png"));
			image[1]= ImageIO.read(new File("bin/used/lightingL-1.png"));
			image[2]= ImageIO.read(new File("bin/used/lightingL-2.png"));
			image[3]= ImageIO.read(new File("bin/used/lightingL-3.png"));
			image[4]= ImageIO.read(new File("bin/used/lightingL-4.png"));
			image[5]= ImageIO.read(new File("bin/used/lightingL-5.png"));
			image[6]= ImageIO.read(new File("bin/used/lightingL-6.png"));
			image[7]= ImageIO.read(new File("bin/used/lightingL-7.png"));
			image[8]= ImageIO.read(new File("bin/used/lightingL-8.png"));
			image[9]= ImageIO.read(new File("bin/used/lightingL-9.png"));
			image[10]= ImageIO.read(new File("bin/used/lightingL-10.png"));
			image[11]= ImageIO.read(new File("bin/used/lightingL-11.png"));
			image[12]= ImageIO.read(new File("bin/used/lightingL-12.png"));
			image[13]= ImageIO.read(new File("bin/used/lightingL-13.png"));	
			image[14]= ImageIO.read(new File("bin/used/lightingL-14.png"));
			image[15]= ImageIO.read(new File("bin/used/lightingL-15.png"));
			image[16]= ImageIO.read(new File("bin/used/lightingL-16.png"));
			image[17]= ImageIO.read(new File("bin/used/lightingL-17.png"));
			
			buttons[0] = ImageIO.read(new File("bin/used/button-left.png"));
			buttons[1] = ImageIO.read(new File("bin/used/button-middle.png"));
			buttons[2] = ImageIO.read(new File("bin/used/button-right.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		
	}

		
	public void tick(){
		loopTimer --;
		if(i>=16){
			i=0;
		}
		if (loopTimer ==0){
			loopTimer = 4;
			i++;
			}
		
		
		//If gameState is paused and Continue is pressed, continue game from level that pause was pressed
		//Currrent level is determined by MainSettings class
		if(keySix){
			//CONTINUE
			keySix = false;
			if(cursorY == 175){
				Game.gameState = STATE.Level;				
			}
			//RETRY
			
			//Reloads current level, calling level reset methods and clearing all enemies
			if(cursorY == 275){
				keySix = false;
				ExperienceHandler.saveXpToFile(CharacterSelect.player);
				restart();

			}
			
			
			//375
			if(cursorY == 375){
				keySix = false;
				cursorY = 175;
				handler.clearEnemies();
				Game.gameState = STATE.Map;
				
			}
			
			//MAIN MENU
			//returns to main menu
			if(cursorY == 475){
				keySix = false;
				cursorY = 175;
				handler.clearEnemies();
				Game.gameState = STATE.MainMenu;
				keySix = false;
				
			}
			//QUIT GAME
			//Closes game
			if(cursorY == 575){
				System.exit(1);
			}
	}
		if(keyOne){
			keyOne = false;
			cursorY -= 100; 
				if(cursorY < 175){
					cursorY = 575;
				}
			}
		
		if(keyTwo){
			keyTwo = false;
			cursorY += 100;
				if(cursorY > 575){
					cursorY = 175;
				}
			}
		
		
		
	}
	
	
	public void render(Graphics2D g2d){
		Font fnt1 = new Font("Trebuchet MS", 1, 50);
		g2d.setColor(Color.white);
		g2d.setFont(fnt1);
		g2d.drawString("Pause", Game.WIDTH/2-50, 100);
		
		g2d.drawString("Continue", Game.WIDTH/2-83, 250);
		g2d.drawString("Retry", Game.WIDTH/2-45, 350);
		g2d.drawString("Map", Game.WIDTH/2-33, 450);
		g2d.drawString("Main Menu", Game.WIDTH/2-108, 550);
		g2d.drawString("Quit Game", Game.WIDTH/2-107, 650);
		
		for(int i = 0; i<5; i++){
			g2d.drawImage(buttons[0], Game.WIDTH/2-126, 200+(i*100), 16, 71, null);
			g2d.drawImage(buttons[1], Game.WIDTH/2-110, 200+(i*100), 250, 71, null);
			g2d.drawImage(buttons[2], Game.WIDTH/2+140, 200+(i*100), 16, 71, null);
		}
		


//		g2d.drawImage(buttons[0], Game.WIDTH/2-126, 300, 16, 71, null);
//		g2d.drawImage(buttons[1], Game.WIDTH/2-110, 300, 250, 71, null);
//		g2d.drawImage(buttons[2], Game.WIDTH/2+140, 300, 16, 71, null);
//
//		g2d.drawImage(buttons[0], Game.WIDTH/2-126, 400, 16, 71, null);
//		g2d.drawImage(buttons[1], Game.WIDTH/2-110, 400, 250, 71, null);
//		g2d.drawImage(buttons[2], Game.WIDTH/2+140, 400, 16, 71, null);
//		
//		g2d.drawImage(buttons[0], Game.WIDTH/2-126, 500, 16, 71, null);
//		g2d.drawImage(buttons[1], Game.WIDTH/2-110, 500, 250, 71, null);
//		g2d.drawImage(buttons[2], Game.WIDTH/2+140, 500, 16, 71, null);

		g2d.drawImage(image[i], Game.WIDTH/2-275, cursorY, 150, 150, null);

		
	}
	private void restart(){
		
		handler.clearEnemies();
		keySix = false;
		Player1.invulnerableTimer = 650;
		Player2.invulnerableTimer = 650;
		Player3.invulnerableTimer = 650;
		Player1.playerScore = 0;
		Player2.playerScore = 0;
		Player3.playerScore = 0;
		resetLevel = true;
		Game.gameState = STATE.Level;
	

	if(MainSettings.currentLevel == 999){
		handler.clearEnemies();
		keySix = false;
		Player1.invulnerableTimer = 650;
		Player2.invulnerableTimer = 650;
		Player3.invulnerableTimer = 650;
		Player1.playerScore = 0;
		Player2.playerScore = 0;
		Player3.playerScore = 0;
		TestLevel.reset = true;
		Game.gameState = STATE.TestLevel;
	}
		
		
	}

}
