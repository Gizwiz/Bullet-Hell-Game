package com.game.maps;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.displays.SkillSelect;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;
import com.game.levels.LevelOneEasy;
import com.game.levels.TestLevel;
import com.game.player1.Player1Info;

public class P1Map1 extends KeyInput{

	static Handler handler;

	
	private Image[] playerCharacter = new Image[3];
	private Image map;
	private Image point;
	private Image underline;
	
	private Font customFont;
	private Font customFont2;
	
	private int selectedMapPoint = 1;
	private int selectedMenuPoint = 1;
	private int selectedLevelMenuPoint = 1;
	private int underlineWidth = 10;
	private int underlinePosY = 190;
	
	private int underlineWidth2 = 10;
	private int underlineposY2 = 170;
	private int moveTimer = 500;
	private int j=0;
	private int levelMenuPosX, levelMenuPosY;

	private boolean menu;
	private boolean levelMenu;
	private boolean move;
	
	private static boolean[] unlocked = new boolean[10];
	private static float[] lockAlpha = new float[10];
	
	private float levelMenuTextPos = 55;
	private float pcPosX = 311, pcPosY = 155; //Player character posX and posY for calulation
	private float velX, velY;
	private float destinationX = 212, destinationY = 555;
	private float[] posX = new float[101];
	private float[] posY = new float[101];
	
	private String[] levelName = new String[2];
	
	public P1Map1(){
		super(handler);

		Player1Info.currentMap = 1;
		MainSettings.currentMap = 1;
		
		
		try {
			map = ImageIO.read(new File("bin/used/map1.png"));
			point = ImageIO.read(new File("bin/used/mapCircleWhite.png"));
			underline = ImageIO.read(new File("bin/used/underline.png"));
			playerCharacter[0] = ImageIO.read(new File("bin/used/player1DOWN.png"));
			playerCharacter[1] = ImageIO.read(new File("bin/used/player2DOWN.png"));
			playerCharacter[2]  =ImageIO.read(new File("bin/used/player3DOWN.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(45f);
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(35f);
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public void tick(){
		if(keyNine){
			keyNine = false;
			TestLevel.reset = true;
			Game.gameState = STATE.TestLevel;
		}
		
		//MENU ACITVATE/DEACTIVATE KEYS
		if(!levelMenu && !menu && (keyEight || keyEscape)){
			keyEscape = false;
			keyEight = false;
			menu = true;
		}
		if(menu && keyEight){
			keyEight = false;
			menu = false;
		}
		if(menu && keyEscape){
			keyEscape = false;
			menu = false; 
			Game.gameState = STATE.MainMenu;
		}
		if(underlineWidth<300){
			underlineWidth+=6;
		}

		//move up/down in menu
		
		//DOWN IN MENU
		if(menu && keyTwo){
			keyTwo = false;
			if(underlinePosY<340){
			underlineWidth = 10;
			selectedMenuPoint++;
			underlinePosY += 50;
			}
			
		}
		//UP IN MENU
		if(menu && keyOne){
			keyOne = false;
			if(underlinePosY>190){
			underlineWidth = 10;
			selectedMenuPoint--;
			underlinePosY -= 50;
			}
		}
		
		//MENU ACTIONS
		if(menu && !levelMenu){
			
		if(selectedMenuPoint == 1 && keySix){
			keySix = !keySix;
			if(CharacterSelect.player == 0){
//				Game.gameState = STATE.Player1Info;
			}
			if(CharacterSelect.player == 1){
//				Game.gameState = STATE.Player2Info;
			}
			if(CharacterSelect.player == 2){
//				Game.gameState = STATE.Player3Info;
			}

		}
		if(selectedMenuPoint == 2 && keySix){
			keySix = !keySix;
			SkillSelect.loaded = true;
			Game.gameState = STATE.SkillSelect;
		}
		if(selectedMenuPoint == 3 && keySix){
			keySix = !keySix;
			Game.gameState = STATE.SmallSettings;
		}
		if(selectedMenuPoint == 4 && keySix){
			keySix = !keySix;
			menu = !menu;
			Game.gameState = STATE.MainMenu;
		}
		
	}

		if(!menu && !levelMenu){
			
			//POINT 1 300, 150
		if(!move && selectedMapPoint == 1){
		
			//TO POINT 2
			if(keyTwo || keyFour){
				moveTo(212, 555, 2);
			}
			
		}
			
		//POINT 2 212, 555
		if(!move && selectedMapPoint == 2){
			
			//TO POINT 1
			if(keyOne){
				moveTo(311,155,1);
			}
			
			//TO POINT 3
			if(keyTwo || keyFour){
				moveTo(413, 880, 3);
			}
				
		}

		//POINT 3
		if(!move && selectedMapPoint == 3){
			
			//TO POINT 2
			if(keyOne || keyThree){
				moveTo(212, 555, 2);
			}
			
			//TO POINT 4
			if(keyFour){
				moveTo(813, 880, 4);
			}
					
		}
		
		//Point 4  813, 880	
		if(!move && selectedMapPoint == 4){
			
			//To Point 3
			if(keyTwo || keyThree){
				moveTo(413, 880, 3);			
			}
			
			//To Point 5
			if(keyFour){
				moveTo(1113, 880, 5);
			}
				
		}
		
		//POINT5 1100, 875
		if(!move && selectedMapPoint == 5){
			
			//To point 4
			if(keyThree){
				moveTo(813, 880, 4);
			}
			
			//To Point 6 1100, 575
			if(keyOne){
				moveTo(1113, 580, 6);
			}
			
		}
		
		if(!move && selectedMapPoint == 6){
			
			//To point 5
			if(keyTwo){
				moveTo(1113, 880, 5);
			}
			
			//to Point 7
			if(keyFour){
				moveTo(1513, 580, 7);
			}
			
			
		}
		
		if(!move && selectedMapPoint == 7){
			
			//to Point 6
			if(keyThree){
				moveTo(1113, 580, 6);
			}
			
			//to Point 8
			if(keyOne){
				moveTo(1363, 205, 8);
			}
		}
		
		if(!move && selectedMapPoint == 8){
			
			//To point 7
			if(keyTwo){
				moveTo(1513, 580, 7);
			}
			
			//To Point 9
			if(keyThree){
				moveTo(713, 80, 9);
			}
			
		}
		
		if(!move && selectedMapPoint == 9){
			
			//to Point 8
			if(keyFour){
				moveTo(1363, 205, 8);
			}
			
			//To Point 10
			if(keyTwo){
				moveTo(905, 420, 10);
			}
			
		}
		
		if(!move && selectedMapPoint == 10){
			//To Point 9
			if(keyOne || keyThree){
				moveTo(713, 80, 9);
			}
			
		}
		
			
		}
		
		//This moves the player to destinatio cordinates
		if(move){
			j++;
			if(j<posX.length){
			pcPosX = posX[j];
			pcPosY = posY[j];
			}
			//if j goes over the array length, it's time to stop moving
			if(j>posX.length){
				j=0;
				move = !move;
			}
		}
		
		
		//MAP ACTIONS
		if(!levelMenu && !move && !menu){
			if (keySix){
				keySix = false;
				getLevelName(selectedMapPoint);
				levelMenu = true;
			}
		}
		
		//LEVEL MENU ACTIONS
		if(levelMenu && keyEight){
			keyEight = !keyEight;
			levelMenu = false;
		}
		
		//DOWN IN LEVEL MENU
		if(levelMenu && keyTwo){
			keyTwo = false;
			if(underlineposY2<270){
			underlineWidth2 = 10;
			selectedLevelMenuPoint++;
			underlineposY2 += 50;
			levelMenuTextPos=70;
			}
			
		}
		//UP IN MENU
		if(levelMenu && keyOne){
			keyOne = false;
			if(underlineposY2>170){
			underlineWidth2 = 10;
			selectedLevelMenuPoint--;
			underlineposY2 -= 50;
			levelMenuTextPos=70;
			}
		}

		
		levelMenuPosX = (int)pcPosX;
		levelMenuPosY = (int)pcPosY;
		
		if(levelMenuTextPos < 100){
			levelMenuTextPos += 1.5;
		}

		//START LEVELS FORM LEVEL MENU
		if(levelMenu && selectedMapPoint == 1 && selectedLevelMenuPoint == 1 && keySix){
			System.out.println("Hello");
			keySix = !keySix;

			MainSettings.currentLevel = 1;
			MainSettings.currentLevelDifficulty = 1;
			System.out.println("Opening level " + MainSettings.currentLevel + " Difficulty " + MainSettings.currentLevelDifficulty);
			LevelOneEasy.reset = true;
			Game.gameState = STATE.Level;
			
		}
		if(selectedMapPoint == 1 && selectedLevelMenuPoint == 2 && keySix){
			keySix = !keySix;
		}
		if(selectedMapPoint == 1 && selectedLevelMenuPoint == 3 && keySix){
			keySix = !keySix;
		}

//		
//		if(keyNine){
//			for(int i = 0; i<unlocked.length; i++){
//				unlocked[i] = true;
//				lockAlpha[i] = 1;
//			}
//		}
	}
	
	private void moveTo(float x, float y, int mapPoint){
		

		if(unlocked[mapPoint-1]){
			selectedMapPoint = mapPoint;
			destinationX = x;
			destinationY = y;
		
		float deltaX =  destinationX - pcPosX;
		float deltaY = destinationY - pcPosY;
		int i = 0;
		for (float t = 0.0f; t < 1.0f; t+= 0.01f) {
			  float next_point_x = pcPosX + deltaX*t;
			  float next_point_y = pcPosY + deltaY*t;
			  posX[i] = next_point_x;
			  posY[i] = next_point_y;
//			  System.out.println(i + " array x" + posX[i] + " array y " + posY[i]);
			  i++;

			 
//			  System.out.println(next_point_x + ", " + next_point_y);
		}
		move = true;
		}
	}
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
		
	}
	
	public static void unlock(int[] unlockArray){

		
		for(int i = 0; i<unlockArray.length; i++){
			if(unlockArray[i] == 1){
				unlocked[i] = true;
				lockAlpha[i] = 1.0f;
			}
			else{
				lockAlpha[i] = 0.4f;
			}
			
		}
		
	}
	
	
	
	public void render(Graphics2D g2d){
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
		Color purple = new Color(175, 55, 170);
		
		g2d.setComposite(makeComposite(0.85f));
		g2d.drawImage(map, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g2d.setColor(Color.BLACK);
		g2d.setComposite(makeComposite(0.65f));
		g2d.fillRoundRect(Game.WIDTH-500, Game.HEIGHT-125, 500, 125, 50, 50);
		g2d.setColor(Color.GRAY);
		g2d.setFont(customFont);
		g2d.drawString("Cluster 1", Game.WIDTH-450, Game.HEIGHT-75);
		g2d.drawString("" + ft.format(date), Game.WIDTH-150, Game.HEIGHT-75);
		g2d.drawString("X for menu", Game.WIDTH-450, Game.HEIGHT-25);
		
		//POINT1
		g2d.setComposite(makeComposite(lockAlpha[0]));
		g2d.drawImage(point, 300, 150, 50, 50, null);
		g2d.setColor(Color.WHITE);
		
		g2d.setComposite(makeComposite(lockAlpha[1]));
		g2d.drawLine(315, 225, 230, 540);
		//POINT2
		g2d.drawImage(point, 200, 550, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[2]));
		g2d.drawLine(245, 615, 405, 865);
		//POINT3
		g2d.drawImage(point, 400, 875, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[3]));
		g2d.drawLine(470, 900, 785, 900);
		//POINT4
		g2d.drawImage(point, 800, 875, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[4]));
		g2d.drawLine(865, 900, 1085, 900);
		//POINT5
		g2d.drawImage(point, 1100, 875, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[5]));
		g2d.drawLine(1125, 865, 1125, 635);
		//POINT6
		g2d.drawImage(point, 1100, 575, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[6]));
		g2d.drawLine(1165, 600, 1485, 600);
		//POINT7
		g2d.drawImage(point, 1500, 575, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[7]));
		g2d.drawLine(1515, 565, 1385, 265);
		//POINT8
		g2d.drawImage(point, 1350, 200, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[8]));
		g2d.drawLine(1335, 225, 765, 100);
		//POINT9
		g2d.drawImage(point, 700, 75, 50, 50, null);
		
		g2d.setComposite(makeComposite(lockAlpha[9]));
		g2d.drawLine(730, 135, 875, 400);
		//POINT10
		g2d.drawImage(point, 875, 400, 85, 85, null);
		

		g2d.setComposite(makeComposite(0.9f));
		if(CharacterSelect.player == 0){
			g2d.drawImage(playerCharacter[0],(int) pcPosX,(int) pcPosY, 25, 40, null);
		}
		if(CharacterSelect.player == 1){
			g2d.drawImage(playerCharacter[1],(int) pcPosX,(int) pcPosY, 25, 40, null);
		}
		if(CharacterSelect.player == 2){
			g2d.drawImage(playerCharacter[2],(int) pcPosX,(int) pcPosY, 25, 40, null);
		}
		
		
		if(levelMenu){
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(customFont);
			g2d.setComposite(makeComposite(0.6f));
			
			if(pcPosX < Game.WIDTH/2 && pcPosY<Game.HEIGHT/2+(Game.HEIGHT/4)){
				g2d.fillRoundRect(levelMenuPosX + 50, levelMenuPosY-50, 400, 400, 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawImage(underline, levelMenuPosX+20,  levelMenuPosY+underlineposY2, 275, 35, null);
				
				
				g2d.drawString("Point " + selectedMapPoint, levelMenuPosX+75, levelMenuPosY-15);
				g2d.setFont(customFont2);
				getLevelName(selectedMapPoint);
				g2d.drawString(""+levelName[0], levelMenuPosX+90, levelMenuPosY+40);
				g2d.drawString(""+levelName[1], levelMenuPosX+160, levelMenuPosY+75);
				
				g2d.setFont(customFont);
				g2d.drawString("_________________",levelMenuPosX + 55, levelMenuPosY+100);
				
				if(selectedLevelMenuPoint == 1){
					g2d.drawString("Easy", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+175);	
				}else{
					g2d.drawString("Easy", levelMenuPosX + 70, levelMenuPosY+175);
				}
				
				if(selectedLevelMenuPoint == 2){
					g2d.drawString("Normal", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+225);
				}
				else{
					g2d.drawString("Normal", levelMenuPosX + 70, levelMenuPosY+225);
				}
				
				if(selectedLevelMenuPoint == 3){
					g2d.drawString("Hard", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+275);
				}
				else{
					g2d.drawString("Hard", levelMenuPosX + 70, levelMenuPosY+275);
				}
			}
			else if(pcPosX < Game.WIDTH/2 && pcPosY>Game.HEIGHT/2+(Game.HEIGHT/4)){
				
				levelMenuPosY = levelMenuPosY-300;
				g2d.fillRoundRect(levelMenuPosX + 50, levelMenuPosY-50, 400, 400, 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawImage(underline, levelMenuPosX+20,  levelMenuPosY+underlineposY2, 275, 35, null);
				
				
				g2d.drawString("Point " + selectedMapPoint, levelMenuPosX+75, levelMenuPosY-15);
				g2d.setFont(customFont2);
				g2d.drawString(""+levelName[0], levelMenuPosX+90, levelMenuPosY+40);
				g2d.drawString(""+levelName[1], levelMenuPosX+160, levelMenuPosY+75);
				
				g2d.setFont(customFont);
				g2d.drawString("_________________",levelMenuPosX + 55, levelMenuPosY+100);
				
				if(selectedLevelMenuPoint == 1){
					g2d.drawString("Easy", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+175);	
				}else{
					g2d.drawString("Easy", levelMenuPosX + 70, levelMenuPosY+175);
				}
				
				if(selectedLevelMenuPoint == 2){
					g2d.drawString("Normal", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+225);
				}
				else{
					g2d.drawString("Normal", levelMenuPosX + 70, levelMenuPosY+225);
				}
				
				if(selectedLevelMenuPoint == 3){
					g2d.drawString("Hard", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+275);
				}
				else{
					g2d.drawString("Hard", levelMenuPosX + 70, levelMenuPosY+275);
				}
			}
			else if(pcPosX > Game.WIDTH/2 && pcPosY<Game.HEIGHT/2+(Game.HEIGHT/4)){
				levelMenuPosX = levelMenuPosX - 475;
				g2d.fillRoundRect(levelMenuPosX + 50, levelMenuPosY-50, 400, 400, 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawImage(underline, levelMenuPosX+20,  levelMenuPosY+underlineposY2, 275, 35, null);
				
				
				g2d.drawString("Point " + selectedMapPoint, levelMenuPosX+75, levelMenuPosY-15);
				g2d.setFont(customFont2);
				g2d.drawString(""+levelName[0], levelMenuPosX+90, levelMenuPosY+40);
				g2d.drawString(""+levelName[1], levelMenuPosX+160, levelMenuPosY+75);
				
				g2d.setFont(customFont);
				g2d.drawString("_________________",levelMenuPosX + 55, levelMenuPosY+100);
				
				if(selectedLevelMenuPoint == 1){
					g2d.drawString("Easy", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+175);	
				}else{
					g2d.drawString("Easy", levelMenuPosX + 70, levelMenuPosY+175);
				}
				
				if(selectedLevelMenuPoint == 2){
					g2d.drawString("Normal", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+225);
				}
				else{
					g2d.drawString("Normal", levelMenuPosX + 70, levelMenuPosY+225);
				}
				
				if(selectedLevelMenuPoint == 3){
					g2d.drawString("Hard", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+275);
				}
				else{
					g2d.drawString("Hard", levelMenuPosX + 70, levelMenuPosY+275);
				}
			}
			else if(pcPosX > Game.WIDTH/2 && pcPosY>Game.HEIGHT/2+(Game.HEIGHT/4)){
				levelMenuPosX = levelMenuPosX - 475;
				levelMenuPosY = levelMenuPosY - 300;
				g2d.fillRoundRect(levelMenuPosX + 50, levelMenuPosY-50, 400, 400, 100, 100);
				g2d.setColor(Color.WHITE);
				g2d.drawImage(underline, levelMenuPosX+20,  levelMenuPosY+underlineposY2, 275, 35, null);
				
				
				g2d.drawString("Point " + selectedMapPoint, levelMenuPosX+75, levelMenuPosY-15);
				g2d.setFont(customFont2);
				g2d.drawString(""+levelName[0], levelMenuPosX+90, levelMenuPosY+40);
				g2d.drawString(""+levelName[1], levelMenuPosX+160, levelMenuPosY+75);
				
				g2d.setFont(customFont);
				g2d.drawString("_________________",levelMenuPosX + 55, levelMenuPosY+100);
				
				if(selectedLevelMenuPoint == 1){
					g2d.drawString("Easy", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+175);	
				}else{
					g2d.drawString("Easy", levelMenuPosX + 70, levelMenuPosY+175);
				}
				
				if(selectedLevelMenuPoint == 2){
					g2d.drawString("Normal", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+225);
				}
				else{
					g2d.drawString("Normal", levelMenuPosX + 70, levelMenuPosY+225);
				}
				
				if(selectedLevelMenuPoint == 3){
					g2d.drawString("Hard", levelMenuPosX + (int)levelMenuTextPos, levelMenuPosY+275);
				}
				else{
					g2d.drawString("Hard", levelMenuPosX + 70, levelMenuPosY+275);
				}
			}
			else{
				g2d.fillRoundRect((int)pcPosX - 150, (int)pcPosY, 500, 300, 100, 100);	
			}
			g2d.setComposite(makeComposite(1f));
		}
		
		
		if(menu){
			
			g2d.setFont(customFont);
			g2d.setColor(Color.BLACK);
			if(pcPosX < Game.WIDTH/2){
			g2d.setComposite(makeComposite(0.8f));
			g2d.fillRoundRect(Game.WIDTH-300, 50, 300, 600, 100, 100);
			g2d.setComposite(makeComposite(1f));
			g2d.setColor(purple);
			g2d.drawImage(underline,  Game.WIDTH-320, underlinePosY, underlineWidth, 35, null);
			g2d.drawString("Character", Game.WIDTH-245, 200);
			g2d.drawString("Skills", Game.WIDTH-245, 250);
			g2d.drawString("Settings", Game.WIDTH-245, 300);
			g2d.drawString("Main Menu", Game.WIDTH-245, 350);
			}

		}
//		else{
//			g2d.setFont(customFont);
//			g2d.setColor(Color.BLACK);
//			g2d.setComposite(makeComposite(0.5f));
//			g2d.fillRoundRect(Game.WIDTH-300, 50, 300, 600, 100, 100);
//			g2d.setColor(purple);
//			g2d.drawString("Character", Game.WIDTH-245, 200);
//			g2d.drawString("Skills", Game.WIDTH-245, 250);
//			g2d.drawString("Settings", Game.WIDTH-245, 300);
//			g2d.drawString("Main Menu", Game.WIDTH-245, 350);
//		}


		
		
	}
	
	private void getLevelName(int point){

		switch(point){
		
		
		case 1:
			levelName[0] = "One of";
			levelName[1] = "Repetition";
			break;
		
		case 2:
			levelName[0] = "Two of";
			levelName[1] = "Continuation";
			break;
			
		case 3:
			levelName[0] = "Three of";
			levelName[1] = "Renewal";
			break;

		case 4:
			levelName[0] = "Four of";
			levelName[1] = "Conservation";
			break;
			
		case 5:
			levelName[0] = "Five of";
			levelName[1] = "Depravation";
			break;
			
		case 6:
			levelName[0] = "Six of";
			levelName[1] = "";
			break;
			
		case 7:
			levelName[0] = "Seven of";
			levelName[1] = "";
			break;
			
		case 8:
			levelName[0] = "Eight of";
			levelName[1] = "";
			break;
			
		case 9:
			levelName[0] = "Nine of";
			levelName[1] = "";
			break;
			
		case 10:
			levelName[0] = "She Fell Through";
			levelName[1] = "Time and Space";
			break;
			
		default: 
			levelName[0] = "Failed to get level name";
			levelName[1] = "Failed to get level name";		
		}

	}
}
