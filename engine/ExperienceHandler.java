package com.game.engine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.game.displays.MainSettings;
import com.game.enums.STATE;
import com.game.player1.Player1Info;
import com.game.player2.Player2Info;
import com.game.player3.Player3Info;

public class ExperienceHandler implements Runnable {

	private int startingLevel = 1;
	private static double currentXp;

	private static double playerLevel;
	public static double playerXp;
	private static double xpToNextLevel;
	public static double xpRate = 0.010; //can be adjusted by level classes for faster gains at higher xp amounts. default 0.010 at 60 tick 5 sleep

	private double deltaXp;

	private Thread thread;
	private boolean running;

	private static String fileName;
	private static String line = null; //for writer
	private static String[] writerArray = new String[10];

	public ExperienceHandler() {
		start();
	}

	public void start() {
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

	public void run() {
		// convert the time to seconds
		double nextTime = (double) System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.01;
		int skippedFrames = 1;
		int maxSkippedFrames = 5;
		double delta = 0.0001;
		while (running) {

			// convert the time to seconds
			double currTime = (double) System.nanoTime() / 1000000000.0;
			if ((currTime - nextTime) > maxTimeDiff)
				nextTime = currTime;
			if (currTime >= nextTime) {

				// assign the time for the next update
				nextTime += delta;
				update();
				if ((currTime < nextTime) || (skippedFrames > maxSkippedFrames)) {
					skippedFrames = 1;
				} else {
					skippedFrames++;
				}
			} else {
				// calculate the time to sleep
				int sleepTime = (int) (1000.0 * (nextTime - currTime));

				// sanity check
				if (sleepTime > 0) {
					// sleep until the next update
					try {
						Thread.sleep(sleepTime);
						Game.log("xp Thread sleeping for " + sleepTime);
					} catch (InterruptedException e) {
						// do nothing
					}
				}
			}
		}
		stop();
	}

	public void update() {
		if (Game.gameState == STATE.Level || Game.gameState == STATE.TestLevel) {
			if (SkillHandler.currentPlayer == 0) {
				if (currentXp > getXpToNext(0)) {
					levelUp(0);
				}
			}
			if (SkillHandler.currentPlayer == 1) {
				if (currentXp > getXpToNext(1)) {
					levelUp(1);
				}

			}
			if (SkillHandler.currentPlayer == 2) {
				if (currentXp > getXpToNext(2)) {
					levelUp(2);
				}
			}
		}

	}
	
	public static void updateXp(double xp){
		
		if (SkillHandler.currentPlayer == 0) {
			Player1Info.xp += xp;
		}
		if (SkillHandler.currentPlayer == 1) {
			Player2Info.xp += xp;
		}
		if (SkillHandler.currentPlayer == 2) {
			Player3Info.xp += xp;
		}		
	}

	public void levelUp(int player) {

		switch (player) {
		case 0:
			deltaXp = currentXp - getXpToNext(0);
			currentXp = 0;
			Player1Info.currentLevel++;
			Player1Info.xp = 0;
			currentXp += deltaXp;
			Player1Info.xp = deltaXp;
			deltaXp = 0;
			break;
		case 1:
			deltaXp = currentXp - getXpToNext(1);
			currentXp = 0;
			Player2Info.currentLevel++;
			Player2Info.xp = 0;
			currentXp += deltaXp;
			Player2Info.xp = deltaXp;
			deltaXp = 0;
			break;
		case 2:
			//			deltaXp = currentXp - getXpToNext(2);
			//			currentXp = 0;
			//			Player3Info.currentLevel++;
			//			Player3Info.xp = 0;
			//			currentXp += deltaXp;
			//			Player3Info.xp = deltaXp;
			//			deltaXp = 0;
			break;
		default:
			break;
		}

	}

	public static double getCurrentXp(int player) {

		if (SkillHandler.currentPlayer == 0) {
			currentXp = Player1Info.getCurrentPlayerXp();
			return currentXp;
		} else if (SkillHandler.currentPlayer == 0) {
			currentXp = Player2Info.getCurrentPlayerXp();
			return currentXp;
		} else if (SkillHandler.currentPlayer == 0) {
			currentXp = Player3Info.getCurrentPlayerXp();
			return currentXp;
		} else

			return player;
	}

	public static double getXpToNext(int player) {
		if (SkillHandler.currentPlayer == 0) {
			playerLevel = Player1Info.getCurrentPlayerLevel();
		}
		if (SkillHandler.currentPlayer == 1) {
			playerLevel = Player2Info.getCurrentPlayerLevel();
		}
		if (SkillHandler.currentPlayer == 2) {
			playerLevel = Player3Info.getCurrentPlayerLevel();
		}
		xpToNextLevel = 5000 / 3 * ((4 * (Math.pow(playerLevel, 3))) - (3 * (Math.pow(playerLevel, 2))) + 1.25 * Math.pow(1.8, (playerLevel - 60)));

		return xpToNextLevel;
	}
	
	public static int getCurrentPlayerLevel(int player){
		

		if (SkillHandler.currentPlayer == 0) {
			playerLevel = Player1Info.getCurrentPlayerLevel();
		}
		if (SkillHandler.currentPlayer == 1) {
			playerLevel = Player2Info.getCurrentPlayerLevel();
		}
		if (SkillHandler.currentPlayer == 2) {
			playerLevel = Player3Info.getCurrentPlayerLevel();
		}
		
		return (int)playerLevel;
		
	}

	public static void saveXpToFile(int player) {

		writerArray[0] = "//current map";
		writerArray[1] = Integer.toString(MainSettings.currentMap);
		writerArray[2] = "//level";

		switch (player) {
		case 0:
			writerArray[3] = Integer.toString((int) Player1Info.currentLevel);
			break;
		case 1:
			writerArray[3] = Integer.toString((int) Player2Info.currentLevel);
			break;
		case 2:
			writerArray[3] = Integer.toString((int) Player3Info.currentLevel);
			break;
		default:
			break;
		}
		writerArray[4] = "//xp";
		writerArray[5] = Integer.toString((int) getCurrentXp(player));
		writerArray[6] = "//to next level";
		writerArray[7] = Integer.toString((int) getXpToNext(player));
		writerArray[8] = "//skillpoints";
		switch (player) {
		case 0:
			writerArray[9] = Integer.toString((int) Player1Info.skillPoints);
			break;
		case 1:
			writerArray[9] = Integer.toString((int) Player2Info.skillPoints);
			break;
		case 2:
			writerArray[9] = Integer.toString((int) Player3Info.skillPoints);
			break;
		default:
			break;
		}

		switch (player) {
		case 0:
			fileName = "bin/saves/" + MainSettings.currentSaveFile + "/p0/0.txt";
			break;
		case 1:
			fileName = "bin/saves/" + MainSettings.currentSaveFile + "/p1/1.txt";
			break;
		case 2:
			fileName = "bin/saves/" + MainSettings.currentSaveFile + "/p2/2.txt";
			break;
		default:
			break;
		}

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < writerArray.length; i++) {
				writer.write(writerArray[i]);
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
