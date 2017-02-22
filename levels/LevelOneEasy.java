package com.game.levels;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.GameHUD;
import com.game.displays.MainMenu;
import com.game.displays.Pause;
import com.game.displays.Progress;
import com.game.displays.MainSettings;
import com.game.engine.AudioPlayer;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.levelOne.BasicEnemy;
import com.game.levelOne.BasicEnemyTwo;
import com.game.levelOne.BasicMovingEnemy;
import com.game.levelOne.BasicSlowShotEnemy;
import com.game.levelOne.BigEnemy;
import com.game.levelOne.MiniBoss1;
import com.game.levelOne.ShieldEnemy;
import com.game.levelOne.ShieldEnemy2;
import com.game.levelOne.TripleShotEnemy;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;
//ALWAYS SPAWN ENEMIES AT REASONABLE Y VALUES!!
public class LevelOneEasy{
	private Handler handler;
	private Pause pause;
	public static int level = 1;
	public static final int levelTime = 25500; //THIS SHOULD REMAIN UNCHANGED
	public static int levelTimer = levelTime;
	public static boolean bossEngage = false; //This is to stop timers from running when boss engaged in the middle of a level
	public static boolean bossDefeat = false;
	public static AudioPlayer levelOne;
	private boolean musicLoaded = false;
	private boolean backr1 = true, backr2 = false;
	private int endTimer = 200;
	private int backPosTimer = 50;
	private int smokeTimer = 15, i;
	private String file;
	private double backPos = -1900, backPos2 = 0;
	private Image back;
	private Image[] smoke = new Image[2];
	
	public static boolean reset = false;
	public static boolean paused = false;
	
	
public LevelOneEasy(Handler handler, GameHUD hud){
	this.handler = handler;

	try {
		back = ImageIO.read(new File("bin/used/l1back.jpg"));
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	handler.clearEnemies();
	reset();
	
}



public void tick(){

	MainSettings.currentLevelTime = levelTimer;
	
	if(Pause.resetLevel){
		reset = Pause.resetLevel;
		Pause.resetLevel = false;
	}
	
	if(!paused){
		
	if(!bossEngage){
		levelTimer--;
		}

	if(reset){
		reset();
		reset = false;
	}

	
	if(MainSettings.music == true){
	if(!musicLoaded){
		MainMenu.player.stop();
		levelOne=new AudioPlayer("/usedMusic/battleThemeA.mp3");
		levelOne.loop();
		musicLoaded = true;
		}
	}

	if (levelTimer == levelTime-500){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-560){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-610){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-670){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-900){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-960){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1010){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1070){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1270){
		handler.addObject(new BasicEnemy(300, 0 , 0, -1, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-500, 0 , 0, -1, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1500){	
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1560){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1610){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1670){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1900){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-1960){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2010){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2070){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2270){
		handler.addObject(new TripleShotEnemy(300, 100 , ID.Enemy, handler));
		handler.addObject(new TripleShotEnemy(Game.WIDTH-400, 100 , ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2270){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2470){
		handler.addObject(new BasicEnemy(215, 0, 1, 3, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-400, 0, -1, 2, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2600){
		handler.addObject(new TripleShotEnemy(300, 100 , ID.Enemy, handler));
		handler.addObject(new TripleShotEnemy(Game.WIDTH-400, 100 , ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2760){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2820){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-2880){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}

	if (levelTimer == levelTime-2960){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(300, 0 , 0, -1, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-500, 0 , 0, -1, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3050){
		handler.addObject(new ShieldEnemy(300, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new ShieldEnemy2(Game.WIDTH-400, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
	}

	if (levelTimer == levelTime-3210){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3260){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3310){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3470){
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3500){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3560){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3610){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3670){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(215, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-3800){
		handler.addObject(new TripleShotEnemy(Game.WIDTH-15, 100 , ID.Enemy, handler));
	}
	if (levelTimer == levelTime-4000){
		handler.addObject(new ShieldEnemy(300, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new ShieldEnemy2(Game.WIDTH-400, 0, 0, 1, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-4600){
		handler.addObject(new ShieldEnemy(400, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new ShieldEnemy2(Game.WIDTH-500, 0, 0, 1, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-4900){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));
	}
	if (levelTimer == levelTime-5000){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5100){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5200){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5300){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5400){
		handler.addObject(new BasicEnemy(215, 225 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(2150, 225 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5500){
		handler.addObject(new BasicEnemy(215, 125 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-400, 125 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5600){
		handler.addObject(new BasicEnemy(215, 125 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-400, 125 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5900){
		handler.addObject(new BasicEnemy(215, 125 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-400, 125 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-5900){
		handler.addObject(new BasicEnemy(215, 125 , 2, 0, ID.Enemy, handler));
		handler.addObject(new BasicEnemy(Game.WIDTH-400, 125 , -2, 0, ID.Enemy, handler));	
	}
	if (levelTimer == levelTime-6100){
		handler.addObject(new ShieldEnemy(300, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new ShieldEnemy2(Game.WIDTH-400, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
	}

	if (levelTimer == levelTime-7000){
		bossEngage = true;
		Progress.bossEngaged = true;
		levelTimer-=1; //prevents infinite boss spawning
		handler.addObject(new MiniBoss1(Game.WIDTH/2-150, 0, ID.Enemy, handler));
	}
	
	if(levelTimer == levelTime-8000){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8050){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8100){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8150){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8200){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
	}
	//BasicEnemyTwo constructor
	//(float x, float y, float velX, float velY, float warningTime, ID id, Handler handler)
	if(levelTimer == levelTime-8200){
		handler.addObject(new BasicEnemyTwo(300, Game.HEIGHT-50,0, -2, 500, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(400, Game.HEIGHT-20,0, -2, 600, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(350, Game.HEIGHT-10,0, -2, 700, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(450, Game.HEIGHT,0, -2, 800, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(400, Game.HEIGHT-20,0, -2, 900, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8230){
		handler.addObject(new BasicMovingEnemy(300, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8260){
		handler.addObject(new BasicMovingEnemy(300, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8290){
		handler.addObject(new BasicMovingEnemy(300, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8310){
		handler.addObject(new BasicMovingEnemy(300, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(Game.WIDTH-400, 0, ID.Enemy, handler));
		handler.addObject(new BigEnemy(Game.WIDTH/2-Game.WIDTH/20, 0 , 0, 1, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8360){
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2, 1, 0, 500, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2-75, 1, 0, 600, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2-125, 1, 0, 700, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2-25, 1, 0, 800, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2-50, 1, 0, 900, ID.Enemy, handler));
		handler.addObject(new BasicEnemyTwo(215, Game.HEIGHT/2-200, 1, 0, 1000, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8400){
		handler.addObject(new TripleShotEnemy(300, 100 , ID.Enemy, handler));
		handler.addObject(new TripleShotEnemy(Game.WIDTH-400, 100 , ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8450){
		handler.addObject(new BigEnemy(Game.WIDTH/2-Game.WIDTH/20, 0 , 0, 1, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8525){
		handler.addObject(new ShieldEnemy(300, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new ShieldEnemy2(Game.WIDTH-400, 0, 0, 1, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
		handler.addObject(new BasicMovingEnemy(500, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8600){
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH/2, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(200, 0, ID.Enemy, handler));
		handler.addObject(new BasicSlowShotEnemy(Game.WIDTH-315, 0, ID.Enemy, handler));
	}
	if(levelTimer == levelTime-8700){
		handler.addObject(new BigEnemy(Game.WIDTH/2-Game.WIDTH/20, 0 , 0, 1, ID.Enemy, handler));
	}
	}
	if(bossDefeat){
		Player1.currentLevel=2;
		Player2.currentLevel=2;
		Player3.currentLevel=2;
	}
}

public void reset(){
	Pause.resetLevel = false;
	
	backPos = -3800;
	backPos2 = -2700;
	backr1 = true;
	backr2 = false;
	
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
	
	MainSettings.currentLevel  = 1;
	Player1.playerScore = 0;
	Player2.playerScore = 0;
	Player3.playerScore = 0;	
	Player1.power = 0;
	Player2.power = 0;
	Player3.power = 0;
	
	levelTimer = levelTime;
	bossDefeat = false;
	bossEngage = false;
	Progress.bossEngaged = false;
	
	GameHUD.setIcons();
}
}




