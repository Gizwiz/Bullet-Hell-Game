package com.game.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.enums.STATE;
import com.game.player1.Player1HighScores;


public class KeyInput implements KeyListener{

	protected Handler handler;
	private float velX=4f, velY=4f;
	//Movement keys
	public static boolean keyOne = false, keyTwo = false, keyThree = false, keyFour = false;
	//Utility keys
	public static boolean keySix = false, keySeven = false, keyEight = false, keyNine = false, keyEscape = false;
	//Skill keys
	public static boolean keySkill1 = false, keySkill2 = false, keySkill3 = false;
	
	//Vertical double pressing
	public static boolean[] keyDownVert = new boolean[3];
	//Horizontal double pressing
	public static boolean[] keyDownHor = new boolean[3];
	
	private AudioPlayer gameStart;
	private Player1HighScores highScore;
	
	public static String keyString = "";
	
	public KeyInput(Handler handler){
		this.handler = handler;
		gameStart = new AudioPlayer("/usedMusic/gameStart.mp3");
	}
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		//GENERAL KEYS
		if(key == KeyEvent.VK_Z){keySix=true;}
		if(key == KeyEvent.VK_SHIFT){keySeven=true;}
		if(key == KeyEvent.VK_X){keyEight = true;}
		if(key == KeyEvent.VK_CONTROL){keyNine = true;}
		if(key == KeyEvent.VK_ESCAPE){keyEscape = true;}
		//PLAYER MOVEMENT
		if(key == KeyEvent.VK_UP) {
			keyOne = true;
			keyDownHor[0] = true;
			keyDownHor[1] = false;
			}
		
		if(key == KeyEvent.VK_DOWN) {
			keyTwo = true;
			keyDownHor[1] = true;
			keyDownHor[2] = true;
			}
		
		if(key == KeyEvent.VK_LEFT) {
			keyThree = true; 
			keyDownVert[0] = true;
			keyDownVert[1] = false;
			}
		
		if(key == KeyEvent.VK_RIGHT){
			keyFour=true; 
			keyDownVert[1] = true;
			keyDownVert[2] = true;
			}
		//SKILL KEYS (ASD)
		if(key == KeyEvent.VK_A){keySkill1=true;}
		if(key == KeyEvent.VK_S){keySkill2=true;}
		if(key == KeyEvent.VK_D){keySkill3=true;}

		

					
		
		//PAUSE
		//If gameState is a playable level & key pressed is ESCAPE => Pause game
		if (Game.gameState == STATE.Level ||
			Game.gameState == STATE.TestLevel
				){
			if(key == KeyEvent.VK_ESCAPE){
				Game.gameState = STATE.Pause;
			}
		}
	}

		

	public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_Z){keySix=false;}
			if(key == KeyEvent.VK_SHIFT){keySeven=false;}
			if(key == KeyEvent.VK_X){keyEight = false;}
			if(key == KeyEvent.VK_CONTROL){keyNine = false;}
			if(key == KeyEvent.VK_ESCAPE){keyEscape = false;}
			
			if(key == KeyEvent.VK_UP) {keyOne = false;  keyDownHor[0] = false; keyDownHor[1] = false;}
			if(key == KeyEvent.VK_DOWN) {keyTwo = false; keyDownHor[2] = false; keyDownHor[1] = false;}
			if(key == KeyEvent.VK_LEFT) {keyThree = false; keyDownVert[0] = false; keyDownVert[1] = false;}
			if(key == KeyEvent.VK_RIGHT){keyFour=false; keyDownVert[2] = false; keyDownVert[1] = false;}
			
			//SKILL KEYS (ASD)
			if(key == KeyEvent.VK_A){keySkill1=false;}
			if(key == KeyEvent.VK_S){keySkill2=false;}
			if(key == KeyEvent.VK_D){keySkill3 = false;}
			
}
	@Override
	public void keyTyped(KeyEvent arg0) {

			
		
	}
	

	

}

