package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.engine.SkillHandler;
import com.game.enums.STATE;

public class CharacterSelect extends KeyInput{
	Handler handler;
	
	private Image[] playerCharacters = new Image[3];
	private Image[] background = new Image[3];
	private Image[] pcBackground = new Image[3];
	private Image arrowLeft, arrowRight, locked, speedStar;
	private int loopTimer = 6;
	private int i = 0;
	public static int cursorX, cursorY;
	public static int player = 0;
	public static int pattern = 1;
	private int currentCharacter = 0; //POSITION IN ARRAY
	
	public static boolean unlockedOne;
	public static boolean unlockedTwo;
	
	public CharacterSelect(Handler handler){
		super(handler);
		try {
//			cursor = ImageIO.read(new File("resources/used/arrowRight.png"));
//			cursor[0]= ImageIO.read(new File("bin/used/lightingL-0.png"));
//			cursor[1]= ImageIO.read(new File("bin/used/lightingL-1.png"));
//			cursor[2]= ImageIO.read(new File("bin/used/lightingL-2.png"));
//			cursor[3]= ImageIO.read(new File("bin/used/lightingL-3.png"));
//			cursor[4]= ImageIO.read(new File("bin/used/lightingL-4.png"));
//			cursor[5]= ImageIO.read(new File("bin/used/lightingL-5.png"));
//			cursor[6]= ImageIO.read(new File("bin/used/lightingL-6.png"));
//			cursor[7]= ImageIO.read(new File("bin/used/lightingL-7.png"));
//			cursor[8]= ImageIO.read(new File("bin/used/lightingL-8.png"));
//			cursor[9]= ImageIO.read(new File("bin/used/lightingL-9.png"));
//			cursor[10]= ImageIO.read(new File("bin/used/lightingL-10.png"));
//			cursor[11]= ImageIO.read(new File("bin/used/lightingL-11.png"));
//			cursor[12]= ImageIO.read(new File("bin/used/lightingL-12.png"));
//			cursor[13]= ImageIO.read(new File("bin/used/lightingL-13.png"));	
//			cursor[14]= ImageIO.read(new File("bin/used/lightingL-14.png"));
//			cursor[15]= ImageIO.read(new File("bin/used/lightingL-15.png"));
//			cursor[16]= ImageIO.read(new File("bin/used/lightingL-16.png"));
//			cursor[17]= ImageIO.read(new File("bin/used/lightingL-17.png"));
//			
//			buttons[0] = ImageIO.read(new File("bin/used/button-left.png"));
//			buttons[1] = ImageIO.read(new File("bin/used/button-middle.png"));
//			buttons[2] = ImageIO.read(new File("bin/used/button-right.png"));
			
			pcBackground[0] = ImageIO.read(new File("bin/used/p1back.jpg"));
			pcBackground[1] = ImageIO.read(new File("bin/used/p2back.jpg"));
			pcBackground[2] = ImageIO.read(new File("bin/used/p3back.jpg"));
			
			background[0] = ImageIO.read(new File("bin/used/pc1back.png"));
			background[1] = ImageIO.read(new File("bin/used/pc2back.png"));
			background[2] = ImageIO.read(new File("bin/used/pc3back.png"));
			

			playerCharacters[0] = ImageIO.read(new File("bin/used/player1DOWN.png"));
			playerCharacters[1] = ImageIO.read(new File("bin/used/player2DOWN.png"));
			playerCharacters[2]  =ImageIO.read(new File("bin/used/player3DOWN.png"));
			
			
			arrowRight = ImageIO.read(new File("bin/used/arrowRight.png"));
			arrowLeft = ImageIO.read(new File("bin/used/arrowLeft.png"));
			locked = ImageIO.read(new File("bin/used/locked.png"));
			speedStar = ImageIO.read(new File("bin/used/speedStar.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public void tick(){
		currentCharacter = player;
		loopTimer --;
		if(i>=16){
			i=0;
		}
		if (loopTimer ==0){
			loopTimer = 4;
			i++;
			}
		
		//KEYINPUT
		/*
		 * keyOne = up
		 * keyTwo = down
		 * keyThree=left
		 * keyFour=right
		 */
		if (keyThree){
			keyThree = false;
			player-=1;
			if(player<0){
				player=2;
			}
		}
		
		if(keyFour){
			keyFour = false;
			player+=1;
			if(player>2){
				player=0;
			}
		}
		
		if(keySix && player == 0){
			keySix = false;
			SkillHandler.currentPlayer = player;
			Game.gameState = STATE.Map;			
		}
		
		if(keySix && player == 1 && unlockedOne){
			keySix = !keySix;
			SkillHandler.currentPlayer = player;
			Game.gameState = STATE.Map;
		}
		
		if(keySix && player == 2 && unlockedTwo){
			keySix = !keySix;
			SkillHandler.currentPlayer = player;
			Game.gameState = STATE.Map;
		}
		
		if(keyEight){
			keyEight = false;
			player=0;
			Game.gameState = STATE.MainMenu;
		}
	
	}
	

	public void render(Graphics2D g2d){
		Font fnt1 = new Font("Calisto MT", 1, 50);
		Font fnt2 = new Font("Calisto MT", 1, 35);
		Font fnt3 = new Font("Calisto MT", 2, 25);
		Color purple = new Color(56, 0, 152);
		g2d.setColor(Color.WHITE);
			
		

		g2d.setFont(fnt2);
		if (currentCharacter==0){
			g2d.setColor(Color.CYAN);
			g2d.setComposite(makeComposite(0.4f));
			g2d.drawImage(pcBackground[0], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(0.7f));
			g2d.drawImage(background[0], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(playerCharacters[currentCharacter], Game.WIDTH/2-(Game.WIDTH/6), 220, 245, 450, null);
			g2d.drawImage(arrowRight, Game.WIDTH/2, 550, 100, 100, null);
			g2d.drawImage(arrowLeft, Game.WIDTH/2-(Game.WIDTH/4), 550, 100, 100, null);
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(playerCharacters[1],  Game.WIDTH/2-(Game.WIDTH/35), 200, 145, 250, null);
			g2d.drawImage(playerCharacters[2], Game.WIDTH/2-(Game.WIDTH/4), 200, 145, 250, null);
			g2d.setComposite(makeComposite(1.0f));
			
			g2d.drawString("Character1.NAME", Game.WIDTH-(Game.WIDTH/3), 235);
			g2d.setFont(fnt3);
			g2d.drawString("Shoot: ", Game.WIDTH-(Game.WIDTH/3), 300);
			g2d.drawString("Spread", Game.WIDTH-(Game.WIDTH/3.75f), 330);
			g2d.drawString("Frost Beam", Game.WIDTH-(Game.WIDTH/3.75f), 360);
			g2d.drawString("Phase: ", Game.WIDTH-(Game.WIDTH/3), 400);
			g2d.drawString("Compressed", Game.WIDTH-(Game.WIDTH/3.75f), 430);
			g2d.drawString("Bomb: ", Game.WIDTH-(Game.WIDTH/3), 500);
			g2d.drawString("Summon Blizzard-", Game.WIDTH-(Game.WIDTH/3.75f), 530);
			g2d.drawString("Safezone", Game.WIDTH-(Game.WIDTH/3.75f), 550);
			g2d.drawString("Random forward moving projectiles", Game.WIDTH-(Game.WIDTH/3.75f), 570);
			g2d.drawString("Speed: ", Game.WIDTH-(Game.WIDTH/3), 600);
			g2d.drawString("Normal speed :", Game.WIDTH-(Game.WIDTH/3.75f), 630);
			g2d.drawString("Phased speed :", Game.WIDTH-(Game.WIDTH/3.75f), 665);
			g2d.drawString("Dash speed :", Game.WIDTH-(Game.WIDTH/3.75f), 700);
			//NORMAL SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 608, 25, 25, null);
			//PHASE SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 643, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 643, 25, 25, null);
			//DASH SPEED STARS
			
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-150), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-180), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-210), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-240), 680, 25, 25, null);
		}
		if (currentCharacter==1){
			g2d.setColor(Color.WHITE);
			g2d.setComposite(makeComposite(0.4f));
			g2d.drawImage(pcBackground[1], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(0.7f));
			g2d.drawImage(background[1], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(playerCharacters[currentCharacter], Game.WIDTH/2-(Game.WIDTH/6), 220, 245, 450, null);
			g2d.drawImage(arrowRight, Game.WIDTH/2, 550, 100, 100, null);
			g2d.drawImage(arrowLeft, Game.WIDTH/2-(Game.WIDTH/4), 550, 100, 100, null);
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(playerCharacters[2],  Game.WIDTH/2-(Game.WIDTH/35), 200, 145, 250, null);
			g2d.drawImage(playerCharacters[0], Game.WIDTH/2-(Game.WIDTH/4), 200, 145, 250, null);
			g2d.setComposite(makeComposite(1.0f));
			
			g2d.drawString("Character2.Name", Game.WIDTH-(Game.WIDTH/3), 235);
			g2d.setFont(fnt3);
			g2d.drawString("Shoot: ", Game.WIDTH-(Game.WIDTH/3), 300);
			g2d.drawString("Placeholder description of character2.Name shot", Game.WIDTH-(Game.WIDTH/3.75f), 330);
			g2d.drawString("Phase: ", Game.WIDTH-(Game.WIDTH/3), 400);
			g2d.drawString("Placeholder description of character2.Name phase", Game.WIDTH-(Game.WIDTH/3.75f), 430);
			g2d.drawString("Bomb: ", Game.WIDTH-(Game.WIDTH/3), 500);
			g2d.drawString("Placeholder description of character2.Name bomb", Game.WIDTH-(Game.WIDTH/3.75f), 530);
			g2d.drawString("Speed: ", Game.WIDTH-(Game.WIDTH/3), 600);
			g2d.drawString("Normal speed :", Game.WIDTH-(Game.WIDTH/3.75f), 630);
			g2d.drawString("Phased speed :", Game.WIDTH-(Game.WIDTH/3.75f), 665);
			g2d.drawString("Dash speed :", Game.WIDTH-(Game.WIDTH/3.75f), 700);
			//NORMAL SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 608, 25, 25, null);
			//PHASE SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 643, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 643, 25, 25, null);
			//DASH SPEED STARS
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-150), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-180), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-210), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-240), 680, 25, 25, null);
//			
			if(!unlockedOne){
			g2d.drawImage(locked,  Game.WIDTH/2-(Game.WIDTH/3), 250, 900, 350, null);
			}
		}
		if (currentCharacter==2){
			g2d.setComposite(makeComposite(0.6f));
			g2d.drawImage(pcBackground[2], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(0.7f));
			g2d.drawImage(background[2], 0, 0, Game.WIDTH, Game.HEIGHT, null);
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(playerCharacters[currentCharacter], Game.WIDTH/2-(Game.WIDTH/6), 220, 245, 450, null);
			g2d.drawImage(arrowRight, Game.WIDTH/2, 550, 100, 100, null);
			g2d.drawImage(arrowLeft, Game.WIDTH/2-(Game.WIDTH/4), 550, 100, 100, null);
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(playerCharacters[0],  Game.WIDTH/2-(Game.WIDTH/35), 200, 145, 250, null);
			g2d.drawImage(playerCharacters[1], Game.WIDTH/2-(Game.WIDTH/4), 200, 145, 250, null);
			g2d.setComposite(makeComposite(1.0f));
			
			g2d.drawString("Character3.Name", Game.WIDTH-(Game.WIDTH/3), 235);
			g2d.setFont(fnt3);
			g2d.drawString("Shoot: ", Game.WIDTH-(Game.WIDTH/3), 300);
			g2d.drawString("Placeholder description of character3.Name shot", Game.WIDTH-(Game.WIDTH/3.75f), 330);
			g2d.drawString("Phase: ", Game.WIDTH-(Game.WIDTH/3), 400);
			g2d.drawString("Placeholder description of character3.Name phase", Game.WIDTH-(Game.WIDTH/3.75f), 430);
			g2d.drawString("Bomb: ", Game.WIDTH-(Game.WIDTH/3), 500);
			g2d.drawString("Placeholder description of character3.Name bomb", Game.WIDTH-(Game.WIDTH/3.75f), 530);
			g2d.drawString("Speed: ", Game.WIDTH-(Game.WIDTH/3), 600);
			g2d.drawString("Normal speed :", Game.WIDTH-(Game.WIDTH/3.75f), 630);
			g2d.drawString("Phased speed :", Game.WIDTH-(Game.WIDTH/3.75f), 665);
			g2d.drawString("Dash speed :", Game.WIDTH-(Game.WIDTH/3.75f), 700);
			//NORMAL SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 608, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 608, 25, 25, null);
			//PHASE SPEED STARS
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 643, 25, 25, null);
			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 643, 25, 25, null);
			//DASH SPEED STARS
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-30), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-60), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-90), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-120), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-150), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-180), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-210), 680, 25, 25, null);
//			g2d.drawImage(speedStar, Game.WIDTH-(Game.WIDTH/5-240), 680, 25, 25, null);
			
			if(!unlockedTwo){
			g2d.drawImage(locked, Game.WIDTH/2-(Game.WIDTH/3), 250, 900, 350, null);
			}
		}
		g2d.setColor(Color.white);
		g2d.setFont(fnt1);
		g2d.drawString("Select Character", Game.WIDTH/2-150, 100);	
	}
}
