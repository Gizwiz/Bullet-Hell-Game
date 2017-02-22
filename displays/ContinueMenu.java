package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.engine.MouseInput;
import com.game.engine.SkillHandler;
import com.game.enums.STATE;
import com.game.maps.P1Map1;
import com.game.player1.Player1Info;
import com.game.player1Skills.P1SkillHandler;
import com.game.player2.Player2Info;
import com.game.player2Skills.P2SkillHandler;
import com.game.player3.Player3Info;
import com.game.player3Skills.P3SkillHandler;

public class ContinueMenu extends KeyInput {

	private Image titleAfter, titleRebirth, background, underline;

	private int selectedSave;

	private float unselectAlpha = 0.5f;
	private float alpha;
	private float fadeAlpha = 0.0f;
	private boolean fadeActive = false;

	private int currentMap = 1;
	private int currentLevel;
	private int nextLevel;
	private int xp;
	private int nextXp;
	private int xpToNextLevel;
	private int unlockedOne;
	private int unlockedTwo;
	private int unlockedMap;

	private int player;

	private double[] infoArray = new double[5];
	private int[] skillInfoArray = new int[99];
	private int[] slotInfoArray = new int[4];
	private int[] generalInfoArray = new int[256];

	private int[] p1Map1 = new int[10];
	private int[] p1Map2 = new int[10];
	private int[] p1Map3 = new int[10];
	private int[] p1Map4 = new int[10];
	private int[] p1Map5 = new int[10];

	private int[] p2Map1 = new int[10];
	private int[] p2Map2 = new int[10];
	private int[] p2Map3 = new int[10];
	private int[] p2Map4 = new int[10];
	private int[] p2Map5 = new int[10];

	private int[] p3Map1 = new int[10];
	private int[] p3Map2 = new int[10];
	private int[] p3Map3 = new int[10];
	private int[] p3Map4 = new int[10];
	private int[] p3Map5 = new int[10];

	private String fileName, skillFileName, slotFileName;
	private String line = null;
	private String character;
	private boolean rowsLeft = true, end = false;
	private int i = 0, j = 0, k = 0, l = 0;
	private BufferedReader reader;

	private Font customFont;

	public ContinueMenu(Handler handler) {
		super(handler);

		try {
			titleAfter = ImageIO.read(new File("bin/used/title-after.png"));
			titleRebirth = ImageIO.read(new File("bin/used/title-rebirth.png"));
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
			underline = ImageIO.read(new File("bin/used/underline2.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Covington-sc.shadow.ttf")).deriveFont(75f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Covington-sc.shadow.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public void tick() throws IOException {

		if (keyOne && selectedSave > 0) {
			keyOne = !keyOne;
			selectedSave--;
		}

		if (keyTwo && selectedSave < 2) {
			keyTwo = !keyTwo;
			selectedSave++;

		}

		if (keySix && selectedSave == 0) {
			keySix = !keySix;
			//Assigns info from save folder 0
			//read(saveFolderNumber, playerNumber)
			MainSettings.currentSaveFile = 0;
			readGeneral(0);
			read(0, 0);
			read(0, 1);
			read(0, 2);
			Game.gameState = STATE.LoadingScreen;

		}
		if (keySix && selectedSave == 1) {
			keySix = !keySix;
			//Assigns info from save folder 1

			MainSettings.currentSaveFile = 1;
			readGeneral(1);
			read(1, 0);
			read(1, 1);
			read(1, 2);
			Game.gameState = STATE.LoadingScreen;
		}
		if (keySix && selectedSave == 2) {
			keySix = !keySix;
			//Assigns info from save folder 2
			//read(saveFolderNumber, playerNumber)
			MainSettings.currentSaveFile = 2;
			readGeneral(2);
			read(2, 0);
			read(2, 1);
			read(2, 2);
			//			Game.loadGame = true;
			//			Game.gameState = STATE.LoadingScreen;
		}
		if (keyEight) {
			keyEight = !keyEight;
			Game.gameState = STATE.IntroDisplay;
		}

		if (MouseInput.mouseClicked) {

			if (MouseInput.getX() > Game.WIDTH / 2 - Game.WIDTH / 4) {
				MouseInput.mouseClicked = false;

				if (MouseInput.getY() > (Game.HEIGHT / 2 - Game.HEIGHT / 10) - 75 && MouseInput.getY() < (Game.HEIGHT / 2 - Game.HEIGHT / 10) + 20) {

					if (selectedSave != 0) {
						selectedSave = 0;
					} else {
						//Assigns info from save folder 0
						//read(saveFolderNumber, playerNumber)
						MainSettings.currentSaveFile = 0;
						readGeneral(0);
						read(0, 0);
						read(0, 1);
						read(0, 2);
						Game.gameState = STATE.LoadingScreen;
					}
				}

				if (MouseInput.getY() > (Game.HEIGHT / 2 + Game.HEIGHT / 9) - 75 && MouseInput.getY() < (Game.HEIGHT / 2 + Game.HEIGHT / 9) + 20) {
					if(selectedSave != 1){
						selectedSave = 1;
					} else{
						//Assigns info from save folder 1

						MainSettings.currentSaveFile = 1;
						readGeneral(1);
						read(1, 0);
						read(1, 1);
						read(1, 2);
						Game.gameState = STATE.LoadingScreen;
					}
				}

				if (MouseInput.getY() > (Game.HEIGHT / 2 + Game.HEIGHT / 3) - 75 && MouseInput.getY() < (Game.HEIGHT / 2 + Game.HEIGHT / 3) + 20) {
					if(selectedSave != 2){
						selectedSave = 2;
					} else {
						//Assigns info from save folder 2
						//read(saveFolderNumber, playerNumber)
						MainSettings.currentSaveFile = 2;
						readGeneral(2);
						read(2, 0);
						read(2, 1);
						read(2, 2);
					}
				}

			}

		}

	}

	public void render(Graphics2D g2d) {
		Font fnt = new Font("Colonna MT", 0, 45);
		g2d.setFont(customFont);
		g2d.setColor(Color.BLACK);

		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

		g2d.drawImage(titleAfter, Game.WIDTH / 5, Game.HEIGHT / 100 - (Game.HEIGHT / 20), Game.WIDTH / 3, Game.HEIGHT / 3 - (Game.HEIGHT / 25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH / 2 - (Game.WIDTH / 10), Game.HEIGHT / 100 + (Game.HEIGHT / 10), Game.WIDTH / 3, Game.HEIGHT / 3 - (Game.HEIGHT / 20), null);

		if (selectedSave == 0) {
			g2d.setComposite(makeComposite(1.0f));
			g2d.drawString("Save 1", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 - Game.HEIGHT / 10);
		} else {
			g2d.setComposite(makeComposite(unselectAlpha));
			g2d.drawString("Save 1", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 - Game.HEIGHT / 10);
		}

		if (selectedSave == 1) {
			g2d.setComposite(makeComposite(1.0f));
			g2d.drawString("Save 2", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 + Game.HEIGHT / 9);
		} else {
			g2d.setComposite(makeComposite(unselectAlpha));
			g2d.drawString("Save 2", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 + Game.HEIGHT / 9);
		}

		if (selectedSave == 2) {
			g2d.setComposite(makeComposite(1.0f));
			g2d.drawString("Save 3", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 + Game.HEIGHT / 3);
		} else {
			g2d.setComposite(makeComposite(unselectAlpha));
			g2d.drawString("Save 3", Game.WIDTH / 2 - Game.WIDTH / 4, Game.HEIGHT / 2 + Game.HEIGHT / 3);
		}

	}

	public void read(int save, int player) throws IOException {

		fileName = "bin/Saves/" + Integer.toString(save) + "/p" + Integer.toString(player) + "/" + Integer.toString(player) + ".txt";
		skillFileName = "bin/Saves/" + Integer.toString(save) + "/p" + Integer.toString(player) + "/skillData.txt";
		slotFileName = "bin/Saves/" + Integer.toString(save) + "/p" + Integer.toString(player) + "/skillSlot.txt";

		//READ GENERAL PLAYER INFO
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.charAt(0) != '/') {
					//Character at pos. 0 to string
					character = "" + line.charAt(0);
					//String to integer -> for use in assignInfo method
					infoArray[i] = Integer.parseInt(line);
					i++;
				}
			}
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//READ SKILL INFO

		try {
			FileReader fileReader = new FileReader(skillFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				//Character at pos. 4 to string
				//String to integer -> for use in assignSkillInfo method
				character = "" + line.charAt(4);
				skillInfoArray[j] = Integer.parseInt(character);
				j++;
			}
			bufferedReader.close();
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		//READ SLOTTED SKILLS
		try {
			FileReader fileReader = new FileReader(slotFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				//String to integer -> for use in assignSkillInfo method
				slotInfoArray[k] = Integer.parseInt(line);
				k++;
			}
			bufferedReader.close();
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		//ASSIGN INFO
		assignInfo(player, infoArray);
		assignSkillInfo(player, slotInfoArray);

		//reset i,j,k variables
		//safety feature if need to re-read and assign new variables to array
		//prevents possible out of bounds error
		i = 0;
		j = 0;
		k = 0;
	}

	private void readGeneral(int save) {
		String file = "bin/Saves/" + Integer.toString(save) + "/General.txt";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				//String to integer -> for use in assignSkillInfo method
				if (line.charAt(0) != '/') {
					generalInfoArray[l] = Integer.parseInt(line);
					l++;
				}
			}
			bufferedReader.close();
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		l = 0;

		unlockedOne = generalInfoArray[0];
		unlockedTwo = generalInfoArray[1];
		if (unlockedOne == 1) {
			CharacterSelect.unlockedOne = true;
		} else {
			CharacterSelect.unlockedOne = false;
		}

		if (unlockedTwo == 1) {
			CharacterSelect.unlockedTwo = true;
		} else {
			CharacterSelect.unlockedTwo = false;
		}
		assignGeneral(generalInfoArray);

	}

	private void assignInfo(int player, double[] infoArray) {

		if (player == 0) {
			Player1Info info = new Player1Info(infoArray);
		}
		if (player == 1) {
			Player2Info info = new Player2Info(infoArray);
		}
		if (player == 2) {
			Player3Info info = new Player3Info(infoArray);
		}

	}

	private void assignSkillInfo(int player, int[] skillSlotInfo) {

		//info array is [99], holds info of unlocked skills
		//slotInfo array is [4], holds info of what skill is slotted to where
		if (player == 0) {
			P1SkillHandler skillHandler = new P1SkillHandler(skillInfoArray);
			SkillHandler slotHandler = new SkillHandler(skillSlotInfo, player);
			//				SkillSelect skillSelect = new SkillSelect(handler, "reload");
			SkillSelect.loaded = true;
		}

		if (player == 1) {
			P2SkillHandler.checkUnlocks(skillInfoArray);
			SkillHandler slotHandler = new SkillHandler(skillSlotInfo, player);
			//				SkillSelect sklillSelect = new SkillSelect(handler);
			//				SkillSelect.loaded = true;
		}

		if (player == 2) {
			P3SkillHandler skillHandler = new P3SkillHandler(skillInfoArray);
			SkillHandler slotHandler = new SkillHandler(skillSlotInfo, player);
			//				SkillSelect skillSelect = new SkillSelect(handler);
			//			SkillSelect.loaded = true;
		}
		SkillSelect.loaded = true;
	}

	private void assignGeneral(int[] generalInfoArray) {

		for (int i = 2; i < 12; i++) {
			p1Map1[i - 2] = generalInfoArray[i];
		}
		P1Map1.unlock(p1Map1);
		for (int i = 12; i < 22; i++) {
			p2Map1[i - 12] = generalInfoArray[i];
		}

	}

	private void flashSelected() {

	}
}
