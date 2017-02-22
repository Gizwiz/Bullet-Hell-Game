package com.game.levels;

import com.game.displays.GameHUD;
import com.game.displays.Progress;
import com.game.displays.MainSettings;
import com.game.engine.AudioPlayer;
import com.game.engine.Handler;
import com.game.enums.ID;
//ALWAYS SPAWN ENEMIES AT REASONABLE Y VALUES!!
public class LevelTwoEasy{
	private Handler handler;
	public static int level = 2;
	public int score = 0;
	public static float levelTime = 5000;
	public static float levelTimer = levelTime;
	public static boolean reset;
	private AudioPlayer levelTwo;
	private boolean bossDefeat = false;
	private boolean musicLoaded = false;
	
public LevelTwoEasy(Handler handler, GameHUD hud){
	
	this.handler=handler;
//	Player1.currentLevel = 2;
//	Player2.currentLevel = 2;
//	Player3.currentLevel = 2;
	handler.clearEnemies();
	levelTimer = levelTime;
	bossDefeat = false;
}

public void tick(){
	if(MainSettings.music == true){
	if(!musicLoaded){
		LevelOneEasy.levelOne.stop();
		levelTwo=new AudioPlayer("/Dark Descent_0.mp3");
		levelTwo.loop();
		musicLoaded=true;
	}
	}
	levelTimer--;
	score++;
	if (levelTimer == levelTime-10){
		handler.addObject (new Progress(3, 700, 0, 0, ID.Progress, handler));
	}
	if (levelTimer == levelTime-100){
		
	}
}


}

