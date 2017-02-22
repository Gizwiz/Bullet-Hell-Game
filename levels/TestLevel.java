package com.game.levels;

import com.game.displays.CharacterSelect;
import com.game.displays.GameHUD;
import com.game.displays.Progress;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.Handler;
//import com.game.engine.BulletHandler;
import com.game.enums.ID;
import com.game.levelOne.BigEnemy2;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;

public class TestLevel {
	
	private Handler handler;
	private int timer = 100;
	private boolean playerLoaded = false;
	public static int levelTime = 25000;
	private int levelTimer = 25000;
	private int spawnTimer = 100;
	private boolean miniBossDefeat = false;
	private boolean bossDefeat=false;
	public static boolean bossEngage = false;
	public static boolean reset = false;
	
	
	private double theta = 0;
	private float x = 500, y = 300;
	private int i = 0;
	//float x, float y, float velX, float velY, ID id
public TestLevel(Handler handler, GameHUD hud){
	this.handler=handler;
	handler.clearEnemies();
	levelTimer = levelTime;
	
	reset();
	MainSettings.currentLevel = 999;
	Player1.power = 4;

}

public void tick(){
	spawnTimer--;
	if(reset){
		reset();
		reset = false;
	}
	if(!bossEngage){
	levelTimer--;
	}
	if (levelTimer == levelTime-50){
//		handler.addObject(new BigEnemy2(Game.WIDTH/2-Game.WIDTH/20, 0 , 0, 1, ID.Enemy, handler));


	}
	if(bossDefeat){
		Player1.currentLevel=2;
		Player2.currentLevel=2;
		Player3.currentLevel=2;
	}

	}



public void reset(){
	i=0;
	MainSettings.currentLevel = 999;
	spawnTimer = 50;
	handler.clearEnemies();
	handler.clearEnemies();
	handler.clearEnemies();
	handler.addObject(new Progress(3, 0 , 0, 0, ID.Progress, handler));
	if(CharacterSelect.player == 0 && CharacterSelect.pattern == 1){
	handler.addObject(new Player1(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, 0, 0, handler));
	}
	if(CharacterSelect.player == 1 && CharacterSelect.pattern == 1){
	handler.addObject(new Player2(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, 0, 0, handler));
	}
	if(CharacterSelect.player == 2 && CharacterSelect.pattern == 1){
	handler.addObject(new Player3(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, 0, 0, handler));
	}
//	handler.addObject(new TestPlayer(Game.WIDTH/2-32, Game.HEIGHT/2-32,ID.Player, 0, 0, handler));
	Player1.playerScore = 0;
	Player2.playerScore = 0;
	Player3.playerScore = 0;
	Player1.power = 4;
	levelTimer = levelTime;
	bossDefeat = false;
	bossEngage = false;
	GameHUD.setIcons();
}

}
