package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.AudioPlayer;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;


public class MainMenu extends KeyInput{ 
	
	private CharacterSelect characterSelect;	
	
	private int startButtonsAt = 3;
	private int maxButtons = 7;
	private int selectedButton = startButtonsAt;
	private double posX =  Game.WIDTH/15;
	private double posX2 = Game.WIDTH/8;
	private float alpha;
	private Image titleAfter, titleRebirth;
	private Image[] button = new Image[maxButtons];
	private Image background;
	private String[] menuText = new String[maxButtons];
	public static AudioPlayer player;
	public static boolean musicChanged = false;
	public static boolean musicVolumeChanged = false;
	public static boolean music;
	public static boolean loaded = false;
	
	public MainMenu(Handler handler) {
		super(handler);
		music = MainSettings.music;
		if(MainSettings.music){
			player = new AudioPlayer("/usedMusic/Path_to_Lake_Land.mp3");
		}
//		ClassLoader classLoader = getClass().getClassLoader();
		alpha=0.0f;
		try {
//			cursor = ImageIO.read(new File("resources/used/arrowRight.png"));
			button[3] = ImageIO.read(new File("bin/used/menu-Play.png"));
			button[4] = ImageIO.read(new File("bin/used/menu-HighScores.png"));
			button[5] = ImageIO.read(new File("bin/used/menu-Settings.png"));
			button[6] = ImageIO.read(new File("bin/used/menu-Quit.png"));
			titleAfter = ImageIO.read(new File("bin/used/title-after.png"));
			titleRebirth = ImageIO.read(new File("bin/used/title-rebirth.png"));
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
//			background = ImageIO.read(getClass().getResource("/Resources/mainMenuBack.jpg"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		loaded = true;
//		if(!MainSettings.music){
//			loadMusic();
//		}
	}
	
	
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public void loadMusic(){
		player = new AudioPlayer("/usedMusic/Path_to_Lake_Land.mp3");
	}
	
	public void tick(){
		if(alpha<0.99f){
		alpha+=0.01f;
		}
		
		if(keySix){
			alpha=1.0f;
		}
		
		if(musicVolumeChanged && music){
			musicVolumeChanged = false;
			player.stop();
			player.resume();
		}
		
		if(music == true && musicChanged == true){
			loadMusic();
			musicChanged = false;
			player.loop();

		}
		if(music == false && musicChanged == true){
			musicChanged = false;
			player.stop();
		}

			if (keySix && Game.allLoaded){
				keySix = false;
				alpha=0.0f;
				if (selectedButton == 3){
					Game.gameState = STATE.CharacterSelect;
				}
				if (selectedButton == 4){
					Game.gameState = STATE.Player1HighScores;
				}
				if (selectedButton == 5){
					MainSettings.selectedButton = MainSettings.startButtonsAt;
					Game.gameState = STATE.MainSettings;
				}
				if (selectedButton == 6){
					System.exit(1);
				}
			}
			
			if(keyEight){
				keyEight = !keyEight;
				alpha=0.0f;
				selectedButton = startButtonsAt;
				Game.gameState = STATE.IntroDisplay;
			}

			if(keyEscape){
				System.exit(1);
			}
			
			if(keyOne){
				if(selectedButton != 3){
				posX2 = posX;
				}
				keyOne=false;
				selectedButton--;
				if(selectedButton < startButtonsAt){
					selectedButton = startButtonsAt;
				}

			}
			if(keyTwo){
				if(selectedButton != 6){
				posX2 = posX;
				}
				keyTwo=false;
				selectedButton++;
				if(selectedButton > maxButtons-1){
					selectedButton = maxButtons-1;
				}
			}
			
//			(int)posX, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8

		}
	
	public void render(Graphics2D g2d){
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g2d.drawImage(titleAfter, Game.WIDTH/5,  Game.HEIGHT/100-(Game.HEIGHT/20), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH/2-(Game.WIDTH/10), Game.HEIGHT/100+(Game.HEIGHT/10), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/20), null);

		
		Font fnt2 = new Font("Trebuchet MS", 4, 15);
		g2d.setColor(Color.white);
		g2d.setFont(fnt2);
		if(Game.allLoaded){
		g2d.drawString("version "+PatchNotes.gameVersion+" (pre-alpha)" , Game.WIDTH/100, Game.HEIGHT/50);
		}else{
			g2d.drawString("Fetching build version..." , Game.WIDTH/100, Game.HEIGHT/50);	
		}

		
		//DRAWS ALL UNSELECTED BUTTONS
		//Loop's second number (i<x) determines number of buttons on screen.
		//First number is the button to start drawing from. ie 0 will draw button 0 at y = 0 and 1 will draw at 1*gameheight/8
		
		g2d.setComposite(makeComposite(alpha));
		for(int i = startButtonsAt; i< maxButtons; i++){
			if(i != selectedButton){
				g2d.drawImage(button[i], (int)posX, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
			//DRAW SELECTED BUTTON MOVED TO THE RIGHT
			if(i == selectedButton){
				if(posX2 <= Game.WIDTH/8){
				posX2+=4;
				}
				g2d.drawImage(button[i], (int)posX2, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
		}
		

		
	}


}
