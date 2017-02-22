package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.AudioPlayer;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class MainSettings extends KeyInput implements KeyListener{
	
	public static AudioPlayer player;
	public static int startButtonsAt = 2;
	private int maxButtons = 8;
	public static int selectedButton = startButtonsAt;
	private int selectedMusicButton = 0;
	
	private double pos;
	private double posX =  Game.WIDTH/7;
	private double posX2 = Game.WIDTH/5;
	private double musicBarVolume;
	private double min  =Game.WIDTH/2+(Game.WIDTH/11), max = Game.WIDTH/4;
	private double minSound = -30.0, maxSound = 6.0, currentSound;
	private double widthMin =(Game.WIDTH/2+(Game.WIDTH/11)), widthMax = Game.WIDTH/4, widthDivision;
	private Image[] button = new Image[maxButtons];
	
	private Image[] musicButton = new Image[8];
	private Image[] musicMenuButton = new Image[3];
	private Image[] numbers = new Image[4];
	private Image[] fadedNumbers = new Image[4];
	private Image title, underline, background, popUp;

	private int numWidth = 35, numHeight = 50;
	private float alpha = 0.85f;
	private boolean livesPopUp = false, hitBoxesPopUp = false;
	private boolean livesConfirm = false, hitBoxesConfirm = false, musicSettingsPopUp = false;
	
	public static int playerLives = 3, playerBombs = 3;
	public static int currentLevel = 1;
	public static int currentLevelDifficulty = 1;
	public static int currentMap = 1;
	public static int currentLevelTime;
	public static int currentSaveFile;
	public static float musicVolume = -10.0f;
	public static boolean showHitBoxes = false;
	public static boolean music = false;
	public static boolean musicVolumeChanged;
	
	
	
	private Font customFont;
	
	public MainSettings(Handler handler) {
		super(handler);
		

		try{
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
			title = ImageIO.read(new File("bin/used/settings-Title.png"));
			popUp = ImageIO.read(new File("bin/used/buttonWholeVert.png"));

			button[2] = ImageIO.read(new File("bin/used/settings-Music.png")); 
			button[3] = ImageIO.read(new File("bin/used/settings-Controls.png")); 
			button[4] = ImageIO.read(new File("bin/used/settings-Lives.png")); 
			button[5] = ImageIO.read(new File("bin/used/settings-Hitboxes.png")); 
			button[6] = ImageIO.read(new File("bin/used/settings-Info.png")); 
			button[7] = ImageIO.read(new File("bin/used/settings-Back.png")); 
			
			musicButton[0] = ImageIO.read(new File("bin/used/music-MusicToggle.png")); 
			musicButton[1] = ImageIO.read(new File("bin/used/music-MusicVolume.png")); 
			musicButton[2] = ImageIO.read(new File("bin/used/music-EffectsToggle.png")); 
			musicButton[3] = ImageIO.read(new File("bin/used/music-EffectsVolume.png"));
			
			musicMenuButton[0] = ImageIO.read(new File("bin/used/musicMinus.png"));
			musicMenuButton[1] = ImageIO.read(new File("bin/used/musicPlus.png"));
			musicMenuButton[2] = ImageIO.read(new File("bin/used/buttonWhole.png"));
			
			numbers[0] = ImageIO.read(new File("bin/used/default-3.png"));
			numbers[1] = ImageIO.read(new File("bin/used/default-4.png"));
			numbers[2] = ImageIO.read(new File("bin/used/default-5.png"));
			numbers[3] = ImageIO.read(new File("bin/used/default-6.png"));
			
			fadedNumbers[0] = ImageIO.read(new File("bin/used/default-3faded.png"));
			fadedNumbers[1] = ImageIO.read(new File("bin/used/default-4faded.png"));
			fadedNumbers[2] = ImageIO.read(new File("bin/used/default-5faded.png"));
			fadedNumbers[3] = ImageIO.read(new File("bin/used/default-6faded.png"));
			
			underline = ImageIO.read(new File("bin/used/underline.png"));
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(75f);
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	//This constructor is used in SkillSelect and SmallSettings to avoid loading
	//when creating object to call getCurrentMap()
	//int parameter to distinguish from the other constructor
	public MainSettings(Handler handler, int one){
		super(handler);
	}
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public void tick(){
		pos  = (musicVolume+30);
		
		musicBarVolume = -1*(widthMax-widthMin) * pos/87;
		if(musicBarVolume > -1*(widthMax-widthMin)){
			musicBarVolume = -1*(widthMax-widthMin);
		}
		//X MAX = Game.HEIGHT/4;
		if(!livesPopUp && !hitBoxesPopUp && !musicSettingsPopUp){
		if(keyOne){
			if(selectedButton != 2){
				posX2 = posX;
				}
			keyOne=false;
			selectedButton--;
			if(selectedButton < startButtonsAt){
				selectedButton = startButtonsAt;
			}
		}
		if(keyTwo){
			if(selectedButton != 7){
			posX2 = posX;
			}
			keyTwo=false;
			selectedButton++;
			if(selectedButton > maxButtons-1){
				selectedButton = maxButtons-1;
			}
		}	
		if(keySix){
			keySix = false;
		if(selectedButton == 2){
			musicSettingsPopUp = true;
		}
		if(selectedButton == 3){
			Game.gameState = STATE.Controls;
		}
		if(selectedButton == 4){
			livesPopUp = true;
		}
		if(selectedButton == 5){
			hitBoxesPopUp = true;
		}
		if(selectedButton == 6){
			Game.gameState = STATE.Info;
		}
		if(selectedButton == 7){
			Game.gameState = STATE.MainMenu;
		}
		}
		if(keyEight){
			keyEight = false;
			Game.gameState = STATE.MainMenu;
		}
		}
		//Moves music buttons menu up/down
		if (musicSettingsPopUp){
			if(keyOne){
				keyOne = false;
				if(selectedMusicButton > 0){
				selectedMusicButton -= 1;
				}
			}
			if(keyTwo){
				keyTwo = false;
				if(selectedMusicButton < 3){
				selectedMusicButton += 1;
			}
				
			}
			
					//Music on/off
					if(selectedMusicButton == 0 && keySix && music){
							keySix = false;
							music = false;
							MainMenu.music = false;
							MainMenu.musicChanged = true;

					}
					if(selectedMusicButton == 0 && keySix && !music){
							keySix = false;
							music = true;
							MainMenu.music = true;
							MainMenu.musicChanged = true;
						}
					if(selectedMusicButton == 0 && keyFour && music){
							keyFour = false;
							music = false;
							MainMenu.music = false;
							MainMenu.musicChanged = true;

					}
					if(selectedMusicButton == 0 && keyThree && !music){
							keyThree = false;
							music = true;
							MainMenu.music = true;
							MainMenu.musicChanged = true;
						}
					
					//Music volume
					if(selectedMusicButton == 1 && keyThree){
						keyThree = false;
						if(musicVolume>-30.0f){
							musicVolume -= 1f;
							MainMenu.musicVolumeChanged = true;
						}
						}
					if(selectedMusicButton == 1 && keyFour){
						keyFour = false;
						if(musicVolume<6.0f){
							musicVolume += 1f;
							MainMenu.musicVolumeChanged = true;
						}
						}
					
					//Effects on/off
					if(selectedMusicButton == 2 && keyOne){
						keyOne = false;
					}
					
			//Close music settings		
			if(keyEight){
				keyEight = false;
				musicSettingsPopUp = false;
			}
		}
		if(livesPopUp && !livesConfirm){
			if(keyFour){
				keyFour = false;
				playerLives++;
				if(playerLives>6){
					playerLives = 6;
				}
			}
			if(keyThree){
				keyThree = false;
				playerLives--;
				if(playerLives<3){
					playerLives = 3;
				}
			}
			if(keySix){
				keySix = false;
				livesConfirm = true;
			}
			if(keyEight){
				keyEight = false;
				livesConfirm = false;
				livesPopUp = false;
			}
		}
		if(livesConfirm && livesPopUp){
			if(keyEight || keySix){
				keySix = false;
				keyEight = false;
				livesConfirm = false;
				livesPopUp = false;
			}
		}
		
		if(hitBoxesPopUp && !hitBoxesConfirm){
			if(keyFour){
				keyFour = false;
				if(showHitBoxes){
					showHitBoxes = false;
				}
			}
			if(keyThree){
				keyThree = false;
				if(!showHitBoxes){
					showHitBoxes = true;
				}
			}
			if(keyEight || keySix){
				keyEight = false;
				keySix = false;
				hitBoxesPopUp = false;
			}
		}
	}
	public void render(Graphics2D g2d){
		Color purple = new Color(125, 46, 145);
		Font fnt1 = new Font ("Colonna MT", 3, 55);
		Font fnt2 = new Font ("Colonna MT", 3, 45);
		
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g2d.drawImage(title, Game.WIDTH/10, Game.WIDTH/1000, 700, 300, null);
		
		//DRAWS ALL UNSELECTED BUTTONS
		//Loop's second number (i<x) determines number of buttons on screen.
		//First number is the button to start drawing from. ie 0 will draw button 0 at y = 0 and 1 will draw at 1*gameheight/8
		for(int i = startButtonsAt; i< maxButtons; i++){
			if(livesPopUp || hitBoxesPopUp || musicSettingsPopUp){
				g2d.setComposite(makeComposite(0.5f));
			}
			if(i != selectedButton){
				g2d.drawImage(button[i],(int)posX, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
			//DRAW SELECTED BUTTON MOVED TO THE RIGHT
			if(i == selectedButton){
				if(posX2 <= Game.WIDTH/5){
					posX2 += 4;
				}
				g2d.drawImage(button[i],(int)posX2, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
		}
		
		if(musicSettingsPopUp){
			//Draw back panel
			g2d.setComposite(makeComposite(0.9f));
			g2d.fillRect(Game.WIDTH/2,Game.WIDTH/10,Game.WIDTH/2-(Game.WIDTH/15),Game.HEIGHT-(Game.HEIGHT/4));
			//Draw buttons
			for(int i = 0; i<musicButton.length; i++){
				if(i != selectedMusicButton){
					g2d.setComposite(makeComposite(0.5f));
				g2d.drawImage(musicButton[i], Game.WIDTH/2, i*Game.HEIGHT/7+(Game.WIDTH/8), Game.WIDTH/3, Game.HEIGHT/9, null);
			}
			if(i == selectedMusicButton){
				g2d.setComposite(makeComposite(1f));
				g2d.drawImage(musicButton[i], Game.WIDTH/2+(Game.WIDTH/10), i*Game.HEIGHT/7+(Game.WIDTH/8), Game.WIDTH/3, Game.HEIGHT/9, null);
			}
			}
			
			
			//Draw menuButtons on unselected
			if(selectedMusicButton != 0){
				g2d.setColor(purple);
				g2d.setFont(fnt1);
				if(music){
					g2d.setComposite(makeComposite(0.9f));
					g2d.drawString("on",  Game.WIDTH/2+(Game.WIDTH/15), 1*Game.HEIGHT/7+(Game.WIDTH/8));
					g2d.setComposite(makeComposite(0.4f));
					g2d.drawString("off",  Game.WIDTH/2+(Game.WIDTH/5), 1*Game.HEIGHT/7+(Game.WIDTH/8));
				}
				if(!music){
					g2d.setComposite(makeComposite(0.4f));
					g2d.drawString("on",  Game.WIDTH/2+(Game.WIDTH/15), 1*Game.HEIGHT/7+(Game.WIDTH/8));
					g2d.setComposite(makeComposite(0.9f));
					g2d.drawString("off",  Game.WIDTH/2+(Game.WIDTH/5), 1*Game.HEIGHT/7+(Game.WIDTH/8));
				}
			}
			if(selectedMusicButton != 1){
				g2d.setComposite(makeComposite(0.2f));
				g2d.drawImage(musicMenuButton[0], Game.WIDTH/2+(Game.WIDTH/22), 2*Game.HEIGHT/7+(Game.WIDTH/10), null);
				g2d.drawImage(musicMenuButton[1], Game.WIDTH/2+(Game.WIDTH/4), 2*Game.HEIGHT/7+(Game.WIDTH/10), null);
				g2d.drawImage(musicMenuButton[2], Game.WIDTH/2+(Game.WIDTH/11), 2*Game.HEIGHT/7+(Game.WIDTH/10), Game.HEIGHT/4, Game.WIDTH/40, null);
				g2d.setComposite(makeComposite(0.4f));
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(Game.WIDTH/2+(Game.WIDTH/11), 2*Game.HEIGHT/7+(Game.WIDTH/10), (int)musicBarVolume, Game.WIDTH/40, 25, 10);
			}
			
			
			if(selectedMusicButton == 0){
				g2d.setColor(purple);
				g2d.setFont(fnt1);
				if(music){
				g2d.setComposite(makeComposite(1f));
				g2d.drawString("on",  Game.WIDTH/2+(Game.WIDTH/7), 1*Game.HEIGHT/7+(Game.WIDTH/8));	
				g2d.setComposite(makeComposite(0.5f));
				g2d.drawString("off",  Game.WIDTH/2+(Game.WIDTH/3)-(Game.WIDTH/25), 1*Game.HEIGHT/7+(Game.WIDTH/8));
				}
				if(!music){
				g2d.setComposite(makeComposite(0.5f));
				g2d.drawString("on",  Game.WIDTH/2+(Game.WIDTH/7), 1*Game.HEIGHT/7+(Game.WIDTH/8));	
				g2d.setComposite(makeComposite(1f));
				g2d.drawString("off",  Game.WIDTH/2+(Game.WIDTH/3)-(Game.WIDTH/25), 1*Game.HEIGHT/7+(Game.WIDTH/8));
				}
			}
			
			if(selectedMusicButton == 1){
				g2d.setComposite(makeComposite(1f));
				g2d.drawImage(musicMenuButton[0], Game.WIDTH/2+(Game.WIDTH/7), 2*Game.HEIGHT/7+(Game.WIDTH/10), null);
				g2d.drawImage(musicMenuButton[1], Game.WIDTH/2+(Game.WIDTH/3), 2*Game.HEIGHT/7+(Game.WIDTH/10), null);
				g2d.drawImage(musicMenuButton[2], Game.WIDTH/2+(Game.WIDTH/5)-(Game.WIDTH/50), 2*Game.HEIGHT/7+(Game.WIDTH/10), Game.HEIGHT/4, Game.WIDTH/40, null);
				g2d.setComposite(makeComposite(0.4f));
				g2d.setColor(Color.WHITE);
				g2d.fillRoundRect(Game.WIDTH/2+(Game.WIDTH/5)-(Game.WIDTH/50), 2*Game.HEIGHT/7+(Game.WIDTH/10), (int)musicBarVolume, Game.WIDTH/40, 25, 9);
			}
		}
		
		
		
		if(livesPopUp){
			//Draw back panel
			g2d.setComposite(makeComposite(0.85f));
			g2d.fillRect(Game.WIDTH/2,Game.WIDTH/5,Game.WIDTH/3-(Game.WIDTH/15),Game.HEIGHT/2-(Game.HEIGHT/4));
		for(int i = 0; i<4; i++){
		g2d.drawImage(fadedNumbers[i], Game.WIDTH/2+(i*60)+(Game.WIDTH/13), Game.WIDTH/3-(Game.WIDTH/13), numWidth, numHeight, null);
		}
		if(playerLives == 3){
		g2d.drawImage(numbers[0], Game.WIDTH/2+(0*60)+(Game.WIDTH/13),  Game.WIDTH/3-(Game.WIDTH/13), numWidth, numHeight, null);
		}
		if(playerLives == 4){
		g2d.drawImage(numbers[1], Game.WIDTH/2+(1*60)+(Game.WIDTH/13),  Game.WIDTH/3-(Game.WIDTH/13), numWidth, numHeight, null);
		}
		if(playerLives == 5){
		g2d.drawImage(numbers[2], Game.WIDTH/2+(2*60)+(Game.WIDTH/13),  Game.WIDTH/3-(Game.WIDTH/13), numWidth, numHeight, null);
		}
		if(playerLives == 6){
		g2d.drawImage(numbers[3], Game.WIDTH/2+(3*60)+(Game.WIDTH/13),  Game.WIDTH/3-(Game.WIDTH/13), numWidth, numHeight, null);
		}

		}
		if(livesConfirm && livesPopUp){
			g2d.setColor(purple);
			g2d.setFont(fnt2);
			
			g2d.drawString("Starting lives set to "+playerLives,  Game.WIDTH/2+(Game.WIDTH/50),  Game.WIDTH/3-(Game.WIDTH/50));
		}
		
		if(hitBoxesPopUp){
			//Draw back panel
			g2d.setComposite(makeComposite(0.85f));
			g2d.fillRect(Game.WIDTH/2,Game.WIDTH/5,Game.WIDTH/3-(Game.WIDTH/15),Game.HEIGHT/2-(Game.HEIGHT/4));
			
			//Draw ON/OFF strings
			g2d.setColor(purple);
			g2d.setFont(fnt1);
			if(showHitBoxes){
				g2d.drawImage(underline,  Game.WIDTH/2+(Game.WIDTH/40),  Game.WIDTH/3-(Game.WIDTH/15), Game.WIDTH/10, Game.WIDTH/40, null);
				g2d.drawString("ON", Game.WIDTH/2+(Game.WIDTH/20),  Game.WIDTH/3-(Game.WIDTH/15));
				g2d.setComposite(makeComposite(0.5f));
				g2d.drawString("OFF", Game.WIDTH/2+(Game.WIDTH/6),  Game.WIDTH/3-(Game.WIDTH/15));
			}
			if(!showHitBoxes){
				g2d.drawImage(underline,  Game.WIDTH/2+(Game.WIDTH/7),  Game.WIDTH/3-(Game.WIDTH/15), Game.WIDTH/10, Game.WIDTH/40, null);
				g2d.setComposite(makeComposite(0.5f));
				g2d.drawString("ON", Game.WIDTH/2+(Game.WIDTH/20),  Game.WIDTH/3-(Game.WIDTH/15));
				g2d.setComposite(makeComposite(1f));
				g2d.drawString("OFF", Game.WIDTH/2+(Game.WIDTH/6),  Game.WIDTH/3-(Game.WIDTH/15));
			}
			
		}


		
	}
	
	public int getCurrentMap(){
		return currentMap;
	}

	public int getCurrentLevel(){
		return currentLevel;
	}
	
	public void setCurrentMap(int map){
		currentMap = map;
	}
	
	public void setCurrentLevel(int level){
		currentLevel = level;
	}
}
