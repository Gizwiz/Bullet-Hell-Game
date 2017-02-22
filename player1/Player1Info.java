package com.game.player1;

import com.game.displays.CharacterSelect;
import com.game.engine.ExperienceHandler;

public class Player1Info {

	public static double currentMap = 1;
	public static double currentLevel;
	public static double nextLevel;
	public static double xp;
	public static double nextXp;
	public static double xpToNextLevel;
	public static double skillPoints;

	
	public Player1Info(double[] infoArray){
		//currentMap, currentLevel, xp, nextXp, skillPoints
		
		this.currentMap = infoArray[0];
		this.currentLevel = infoArray[1];
		this.xp = infoArray[2];
		this.nextLevel = infoArray[3]+1;
		this.skillPoints = infoArray[4];
		
	}
	
	
	public static double getCurrentPlayerLevel(){
		
		return currentLevel;
	}
	
	public static double getCurrentPlayerXp(){

		return xp;
	}

	
}
