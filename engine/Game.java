package com.game.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import com.game.displays.CharacterSelect;
import com.game.displays.ContinueMenu;
import com.game.displays.Controls;
import com.game.displays.GameHUD;
import com.game.displays.GameOver;
import com.game.displays.InfoMenu;
import com.game.displays.IntroDisplay;
import com.game.displays.LoadingScreen;
import com.game.displays.MainMenu;
import com.game.displays.MainSettings;
import com.game.displays.NewGame;
import com.game.displays.PatchNotes;
import com.game.displays.Pause;
import com.game.displays.SkillPointSelect;
import com.game.displays.SkillSelect;
import com.game.displays.SmallSettings;
import com.game.enums.STATE;
import com.game.levels.LevelOneEasy;
import com.game.levels.LevelTwoEasy;
import com.game.levels.TestLevel;
import com.game.maps.P1Map1;
import com.game.player1.NewHighScorePlayer1;
import com.game.player1.Player1HighScores;
import com.game.player2.NewHighScorePlayer2;
import com.game.player2.Player2HighScores;
import com.game.player3.NewHighScorePlayer3;
import com.game.player3.Player3HighScores;

public class Game extends Canvas implements Runnable {

	// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private static final long serialVersionUID = -5752820279252417109L;
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	public static boolean reRead = false;
	public static boolean load = false;
	public static boolean allLoaded = false; // determines if everything is loaded
	public static boolean loadGame = false;
	public static boolean logging = true; // determines if logging will display console messages
	public static float frames = 0;
	public static double amountToLoad = 20;
	public static boolean paused = false;
	private Thread thread;
	private boolean running = false;
	private CharacterSelect characterSelect;
	private ContinueMenu continueMenu;
	private Controls controls;
	private ExperienceHandler experienceHandler;
	private GameHUD gameHUD;
	private GameOver gameOver;
	private Handler handler;
	private InfoMenu infoMenu;
	private IntroDisplay introDisplay;
	private LevelOneEasy levelOneEasy;
	private LevelTwoEasy levelTwoEasy;
	private LoadingScreen loadingScreen;
	private MainMenu mainMenu;
	private MainSettings settingsMenu;
	private NewGame newGame;
	private NewHighScorePlayer1 newHighScorePlayer1;
	private NewHighScorePlayer2 newHighScorePlayer2;
	private NewHighScorePlayer3 newHighScorePlayer3;
	private P1Map1 p1map1;
	private PatchNotes patchNotes;
	private Pause pause;
	private Player1HighScores player1HighScores;
	private Player2HighScores player2HighScores;
	private Player3HighScores player3HighScores;
	private SkillHandler skillHandler;
	private SkillPointSelect skillPointSelect;
	private SkillSelect skillSelect;
	private SmallSettings smallSettings;
	private TestLevel testLevel;

	private int fps = 60;
	private int frameCount = 0;

	// LAUNCH STATE! DEFAULT: gameSTATE = STATE.IntroDisplay
	public static STATE gameState = STATE.IntroDisplay;

	public Game() {
		handler = new Handler();
		if (Window.borderlessFullScreen || Window.fullScreen) {
			HEIGHT = getToolkit().getScreenSize().height;
			WIDTH = getToolkit().getScreenSize().width;
		}
		if (Window.windowed169) {
			HEIGHT = 720;
			WIDTH = 1280;
		}
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler));
		introDisplay = new IntroDisplay(handler);
		loadingScreen = new LoadingScreen(handler);
		newGame = new NewGame(handler);
		;
		//skillHandler must be loaded early in order to make sure skills are read and redfreshed properly from save files
		skillHandler = new SkillHandler();
		loadingScreen.progress++;

		// CREATE NEW WINDOW
		new Window(WIDTH, HEIGHT, "Game", this);

		load();
		
	}

	public void load() {

		// REMEMBER TO SET AMOUNT TO LOAD VARIABLE TO MAKE LOADING SCREEN WORK
		// PROPERLY
		loadingScreen.progress++;

		settingsMenu = new MainSettings(handler);
		loadingScreen.progress++;

		continueMenu = new ContinueMenu(handler);
		loadingScreen.progress++;

		mainMenu = new MainMenu(handler);
		loadingScreen.progress++;

		characterSelect = new CharacterSelect(handler);
		loadingScreen.progress++;

		controls = new Controls(handler);
		loadingScreen.progress++;

		p1map1 = new P1Map1();
		loadingScreen.progress++;

		skillSelect = new SkillSelect(handler);
		loadingScreen.progress++;

		smallSettings = new SmallSettings(handler);
		loadingScreen.progress++;

		infoMenu = new InfoMenu(handler);
		loadingScreen.progress++;

		try {
			patchNotes = new PatchNotes(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadingScreen.progress++;

		gameHUD = new GameHUD();
		loadingScreen.progress++;

		levelOneEasy = new LevelOneEasy(handler, gameHUD);
		loadingScreen.progress++;

		levelTwoEasy = new LevelTwoEasy(handler, gameHUD);
		loadingScreen.progress++;

		testLevel = new TestLevel(handler, gameHUD);
		loadingScreen.progress++;

		pause = new Pause(handler);
		loadingScreen.progress++;

		gameOver = new GameOver(handler);
		loadingScreen.progress++;

		experienceHandler = new ExperienceHandler();

		try {
			player1HighScores = new Player1HighScores();
			player2HighScores = new Player2HighScores();
			player3HighScores = new Player3HighScores();
			newHighScorePlayer1 = new NewHighScorePlayer1();
			newHighScorePlayer2 = new NewHighScorePlayer2();
			newHighScorePlayer3 = new NewHighScorePlayer3();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadingScreen.progress++;

		skillPointSelect = new SkillPointSelect(handler);
		loadingScreen.progress++;
		
		allLoaded = true;
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {

		}
	}

	//	public void run() {
	//		this.requestFocus();
	//		long lastTime = System.nanoTime();
	//		double amountOfTicks = 120.0;
	//		double ns = 1000000000 / amountOfTicks;
	//		double delta = 1;
	//		long timer = System.currentTimeMillis();
	//		frames = 0;
	//
	//		while (running) {
	//			long now = System.nanoTime();
	//			delta += (now - lastTime) / ns;
	//			lastTime = now;
	//			while (delta >= 1) {
	//				tick();
	//				//				render();
	//				delta--;
	//			}
	//			if (running)
	//				render();
	//				
	//			frames++;
	//			if (System.currentTimeMillis() - timer > 1000) {
	//				
	//				timer += 1000;
	//				 System.out.println("FPS: "+frames);
	//				frames = 0;
	////				try {
	////					Thread.sleep(timer);
	////				} catch (InterruptedException e) {
	////
	////				}
	//
	//			}
	//		}
	//		stop();
	//
	//	}

	//	 public void run()
	//	 {
	//	 // convert the time to seconds
	//	 this.requestFocus();
	//	 double nextTime = (double)System.nanoTime() / 1000000000.0;
	//	 double maxTimeDiff = 0.01;
	//	 int skippedFrames = 1;
	//	 int maxSkippedFrames = 5;
	//	 double delta = 0.01;
	//	 double frames = 0;
	//	 while(running)
	//	 {
	//	
	//	
	//	 // convert the time to seconds
	//	 double currTime = (double)System.nanoTime() / 1000000000.0;
	//	 if((currTime - nextTime) > maxTimeDiff) nextTime = currTime;
	//	 if(currTime >= nextTime)
	//	 {
	//	
	//	 // assign the time for the next update
	//	 nextTime += delta;
	//	 tick();
	//	 if((currTime < nextTime) || (skippedFrames > maxSkippedFrames))
	//	 {
	//	 render();
	//	 skippedFrames = 1;
	//	 }
	//	 else
	//	 {
	//	 skippedFrames++;
	//	 }
	//	 }
	//	 else
	//	 {
	//	 // calculate the time to sleep
	//	 int sleepTime = (int)(1000.0 * (nextTime - currTime));
	//	
	//	 // sanity check
	//	 if(sleepTime > 0)
	//	 {
	//	 // sleep until the next update
	//	 try
	//	 {
	//	 Thread.sleep(sleepTime);
	//	 }
	//	 catch(InterruptedException e)
	//	 {
	//	 // do nothing
	//	 }
	//	 }
	//	 }
	//	 }
	//	 stop();
	//	 }

	public synchronized void run(){
	      //This value would probably be stored elsewhere.
	      final double GAME_HERTZ = 120.0;
	      //Calculate how many ns each frame should take for our target game hertz.
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	      //At the very most we will update the game this many times before a new render.
	      //If you're worried about visual hitches more than perfect timing, set this to 1.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;
	      //We will need the last update time.
	      double lastUpdateTime = System.nanoTime();
	      //Store the last time we rendered.
	      double lastRenderTime = System.nanoTime();
	      
	      //If we are able to get as high as this FPS, don't render again.
	      final double TARGET_FPS = 60;
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	      
	      //Simple way of finding FPS.
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
	      
	      while (running)
	      {
	         double now = System.nanoTime();
	         int updateCount = 0;
	         
	         if (!paused)
	         {
	             //Do as many game updates as we need to, potentially playing catchup.
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
	            {
	               tick();
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	   
	            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
	            {
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	         
	            //Render. To do so, we need to calculate interpolation for a smooth render.
	            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
	            render();
	            lastRenderTime = now;
	         
	            //Update the frames we got.
	            int thisSecond = (int) (lastUpdateTime / 1000000000);
	            if (thisSecond > lastSecondTime)
	            {
//	               System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	            
//	               This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	   }

	public void tick() {

		handler.tick();

		if (gameState == STATE.IntroDisplay) {
			introDisplay.tick();
		}
		if (gameState == STATE.ContinueMenu) {
			try {
				continueMenu.tick();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (gameState == STATE.NewGame) {
			newGame.tick();
		}
		if (gameState == STATE.MainMenu) {
			mainMenu.tick();
		}
		if (gameState == STATE.LoadingScreen) {
			loadingScreen.tick();
		}
		if (gameState == STATE.Controls) {
			controls.tick();
		}
		if (gameState == STATE.CharacterSelect) {
			characterSelect.tick();
		}
		if (gameState == STATE.MainSettings) {
			settingsMenu.tick();
			mainMenu.tick();
		}
		if (gameState == STATE.SmallSettings) {
			smallSettings.tick();
			mainMenu.tick();
		}
		if (gameState == STATE.Info) {
			infoMenu.tick();
		}
		if (gameState == STATE.GameOver) {
			gameOver.tick();
		}
		if (gameState == STATE.PatchNotes) {
			patchNotes.tick();
		}
		if (gameState == STATE.TestLevel) {
			gameHUD.tick();
			testLevel.tick();
		}
		if (gameState == STATE.Pause) {
			pause.tick();
		}
		if (gameState == STATE.SkillSelect) {
			skillSelect.tick();
		}
		if (gameState == STATE.SkillPointSelect) {
			skillPointSelect.tick();
		}

		if (gameState == STATE.Level) {
			gameHUD.tick();
			if (MainSettings.currentLevel == 1 && MainSettings.currentLevelDifficulty == 1) {
				levelOneEasy.tick();
			}
			if (MainSettings.currentLevel == 1 && MainSettings.currentLevelDifficulty == 2) {
				// levelOneNormal.tick();
			}
			if (MainSettings.currentLevel == 1 && MainSettings.currentLevelDifficulty == 3) {
				// levelOneHard.tick();
			}
			if (MainSettings.currentLevel == 1 && MainSettings.currentLevelDifficulty == 4) {
				// levelOneInsane.tick();
			}
			if (MainSettings.currentLevel == 2 && MainSettings.currentLevelDifficulty == 1) {
				levelTwoEasy.tick();
			}
			if (MainSettings.currentLevel == 999) {
				testLevel.tick();
			}
		}
		if (gameState == STATE.Map) {
			if (MainSettings.currentMap == 1) {
				p1map1.tick();
			}
		}

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);

		if (gameState == STATE.IntroDisplay) {
			introDisplay.render(g2d);
		}
		if (gameState == STATE.ContinueMenu) {
			continueMenu.render(g2d);
		}
		if (gameState == STATE.NewGame) {
			newGame.render(g2d);
		}
		if (gameState == STATE.MainMenu) {
			mainMenu.render(g2d);
		}
		if (gameState == STATE.Controls) {
			controls.render(g2d);
		}
		if (gameState == STATE.CharacterSelect) {
			characterSelect.render(g2d);
		}
		if (gameState == STATE.MainSettings) {
			settingsMenu.render(g2d);
		}
		if (gameState == STATE.SmallSettings) {
			smallSettings.render(g2d);
		}
		if (gameState == STATE.GameOver) {
			gameOver.render(g2d);
		}
		if (gameState == STATE.PatchNotes) {
			patchNotes.render(g2d);
		}
		if (gameState == STATE.Info) {
			infoMenu.render(g2d);
		}
		if (gameState == STATE.LoadingScreen) {
			loadingScreen.render(g2d);
		}
		if (gameState == STATE.SkillSelect) {
			skillSelect.render(g2d);
		}
		if (gameState == STATE.TestLevel) {
			g2d.setColor(Color.gray);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			gameHUD.render(g2d);
			handler.render(g2d);
		}
		if (gameState == STATE.Pause) {
			pause.render(g2d);
		}

		if (gameState == STATE.Map) {
			if (MainSettings.currentMap == 1) {
				p1map1.render(g2d);
			}
		}
		if (gameState == STATE.Level) {
			gameHUD.render(g2d);
			handler.render(g2d);
		}
		if (gameState == STATE.SkillPointSelect) {
			skillPointSelect.render(g2d);
		}
		if (gameState == STATE.Player1HighScores) {
			player1HighScores = new Player1HighScores();
			if (reRead) {
				newHighScorePlayer1.read();
				reRead = false;
			}
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			player1HighScores.render(g2d);
		}
		if (gameState == STATE.Player2HighScores) {
			try {
				player2HighScores = new Player2HighScores();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (reRead) {
				newHighScorePlayer2.read();
				reRead = false;
			}
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			player2HighScores.render(g2d);
		}
		if (gameState == STATE.Player3HighScores) {
			try {
				player3HighScores = new Player3HighScores();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (reRead) {
				newHighScorePlayer3.read();
				reRead = false;
			}
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			player3HighScores.render(g2d);
		}

		if (gameState == STATE.NewHighScorePlayer1) {
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			if (reRead) {
				newHighScorePlayer1.read();
				reRead = false;
			}
			newHighScorePlayer1.render(g2d);

		}
		if (gameState == STATE.NewHighScorePlayer2) {
			if (reRead) {
				newHighScorePlayer2.read();
				reRead = false;
			}
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			newHighScorePlayer2.render(g2d);
		}
		if (gameState == STATE.NewHighScorePlayer3) {
			if (reRead) {
				newHighScorePlayer3.read();
				reRead = false;
			}
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			newHighScorePlayer3.render(g2d);
		}

		g2d.dispose();
		bs.show();
	}

	// CLAMP METHOD to restrict movement beyond borders
	public static float clamp(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	//LOG METHOD for easy logging, toggleable.
	public static void log(String log) {
		if (logging)
			System.out.println(log);
	}

	public static void main(String[] args) {
		new Game();

	}

}
