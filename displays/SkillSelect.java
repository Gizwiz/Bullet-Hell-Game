package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.ExperienceHandler;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.engine.SkillHandler;
import com.game.enums.STATE;
import com.game.player1Skills.P1SkillHandler;
import com.game.player2Skills.P2SkillHandler;
import com.game.player3Skills.P3SkillHandler;

public class SkillSelect extends KeyInput {

	Handler handler;

	public static int slot1, slot2, slot3;

	public static boolean loaded = false;

	private int frameWidth = 100;
	private int frameHeight = 100;
	private int iconWidth = 75;
	private int iconHeight = 75;
	private int skillWidth = 90;
	private int skillHeight = 90;

	private int currentPlayer;
	private int currentMap;
	private double levelProgress;

	private int selectedMenuPoint;
	private int selectedSkill;
	private int selectedSkillChoice;
	private int selectedSkillChoiceHorizontal;
	private int selectedSkillPoint;

	//slected skill tooltip rendering
	private float tooltipWidth = 200;
	private float tooltipHeight = 200;
	private String skillName;
	private float cooldown;
	private float duration;
	private String[] skillDescription = new String[3]; //max 4 lines
	private boolean tooltip = false;
	private int tooltipX = Game.WIDTH / 10 + 23 + (selectedSkill * 150) + 4 * iconWidth;
	private int tooltipY = Game.HEIGHT - Game.HEIGHT / 5 + selectedSkillChoice * -iconHeight + (selectedSkillChoice * -10) - (Game.HEIGHT / 6) + iconWidth / 6;
	private int tooltipMaxWidth = Game.WIDTH / 3;
	private int tooltipMaxHeight = Game.WIDTH / 7;
	private float textAlpha = 0.00f;

	private String currentSelectedSkillAsString;
	private int currentSelectedSkillAsInteger;

	private int underlineWidth;

	private boolean menu = true;
	private boolean skillMenu;
	private boolean skillSelectMenu;
	private boolean skillPoint;
	private boolean fadeIn = true, fadeOut;
	private boolean fade;

	private boolean showSkills;

	private String fileName;
	private String line = null; //for reader
	private String saveLine; //for reader;
	private int a; //for reader line
	private int[] saveSkills = new int[4];

	private Image[] player = new Image[3];
	private Image[] background = new Image[3];
	private Image[] pcBackground = new Image[3];
	private Image[] underline = new Image[3];

	private Image[] frames = new Image[4];
	private Image[] skills = new Image[4];

	private Image[] p1SkillsL1 = new Image[9];
	private Image[] p2SkillsL1 = new Image[9];
	private Image[] p3SkillsL1 = new Image[9];

	private Image[] p1SkillsL2 = new Image[9];
	private Image[] p2SkillsL2 = new Image[9];
	private Image[] p3SkillsL2 = new Image[9];

	private Image[] p1SkillsL3 = new Image[9];
	private Image[] p2SkillsL3 = new Image[9];
	private Image[] p3SkillsL3 = new Image[9];

	private Font customFont;

	private String skillID;
	private int skillIDNumber;
	private float alpha = 0.9f;

	public SkillSelect(Handler handler) {
		super(handler);
		this.handler = handler;
		currentPlayer = SkillHandler.currentPlayer;
		try {
			frames[0] = ImageIO.read(new File("bin/used/framep1.png"));
			frames[1] = ImageIO.read(new File("bin/used/framep2.png"));
			frames[2] = ImageIO.read(new File("bin/used/framep3.png"));

			player[0] = ImageIO.read(new File("bin/used/player1DOWN.png"));
			player[1] = ImageIO.read(new File("bin/used/player2DOWN.png"));
			player[2] = ImageIO.read(new File("bin/used/player3DOWN.png"));

			pcBackground[0] = ImageIO.read(new File("bin/used/p1back.jpg"));
			pcBackground[1] = ImageIO.read(new File("bin/used/p2back.jpg"));
			pcBackground[2] = ImageIO.read(new File("bin/used/p3back.jpg"));

			background[0] = ImageIO.read(new File("bin/used/pc1back.png"));
			background[1] = ImageIO.read(new File("bin/used/pc2back.png"));
			background[2] = ImageIO.read(new File("bin/used/pc3back.png"));

			underline[0] = ImageIO.read(new File("bin/used/underline2Blue.png"));
			underline[1] = ImageIO.read(new File("bin/used/underline2.png"));
			underline[2] = ImageIO.read(new File("bin/used/underline2Red.png"));
			//			skills[0] = ImageIO.read(new File("bin/used/skillIcons/skill"+SkillHandler.slotOne+".png"));
			//			skills[1] = ImageIO.read(new File("bin/used/skillIcons/skill"+SkillHandler.slotTwo+".png"));
			//			skills[2] = ImageIO.read(new File("bin/used/skillIcons/skill"+SkillHandler.slotThree+".png"));
			//			skills[3] = ImageIO.read(new File("bin/used/skillIcons/p"+currentPlayer+"bomb"+SkillHandler.slotBomb+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Level 1 icons
		try {
			for (int i = 1; i < 10; i++) {
				p1SkillsL1[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill1" + i + "1.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p2SkillsL1[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill2" + i + "1.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p3SkillsL1[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill3" + i + "1.png"));
			}
		} catch (IOException e) {

		}

		//level 2 icons
		try {
			for (int i = 1; i < 10; i++) {
				p1SkillsL2[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill1" + i + "2.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p2SkillsL2[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill2" + i + "2.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p3SkillsL2[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill3" + i + "2.png"));
			}
		} catch (IOException e) {

		}

		//Level 3 icons
		try {
			for (int i = 1; i < 10; i++) {
				p1SkillsL3[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill1" + i + "3.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p2SkillsL3[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill2" + i + "3.png"));
			}
		} catch (IOException e) {

		}
		try {
			for (int i = 1; i < 10; i++) {
				p3SkillsL3[i - 1] = ImageIO.read(new File("bin/used/skillIcons/skill3" + i + "3.png"));
			}
		} catch (IOException e) {

		}

		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(55f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public SkillSelect(Handler handler, String reload) {
		super(handler);
		this.handler = handler;
		currentPlayer = SkillHandler.currentPlayer;
		loaded = true;
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public void tick() {
		currentPlayer = SkillHandler.currentPlayer;
		if (loaded) {
			refreshIcons();
			loaded = false;
		}

		if (keyEight && !skillMenu && !skillPoint && !skillSelectMenu) {
			keyEight = !keyEight;
			fade = true;
		}

		//Screen fade alpha
		if (!fade && alpha > 0.01f) {
			alpha -= 0.01f;
		}
		if (fade && alpha < 0.99f) {
			alpha += 0.01f;
			if (alpha > 0.95f) {
				Game.gameState = STATE.Map;
				fade = false;
				alpha = 1f;
			}
		}

		//MAIN MENU
		if (keySix && !menu && !skillMenu && !skillPoint) {
			keySix = !keySix;
			menu = true;
		}

		if (menu) {
			if (keyOne && selectedMenuPoint > 0) {
				keyOne = !keyOne;
				selectedMenuPoint--;
			}
			if (keyTwo && selectedMenuPoint < 2) {
				keyTwo = !keyTwo;
				selectedMenuPoint++;
			}

			if (keySix) {
				keySix = !keySix;

				if (selectedMenuPoint == 0) {
					menu = !menu;
					skillMenu = true;
				}
				if (selectedMenuPoint == 1) {
					Game.gameState = STATE.SkillPointSelect;
				}
				if (selectedMenuPoint == 2) {
					fade = true;
				}

			}
		}

		if (skillMenu && !skillSelectMenu && !skillPoint) {
			if (keySix && selectedSkill < 3) {
				keySix = !keySix;
				skillSelectMenu = true;
			}
			if (keyEight) {
				keyEight = !keyEight;
				skillMenu = false;
				menu = true;
			}
			if (keyThree && selectedSkill > 0) {
				keyThree = !keyThree;
				selectedSkill--;
			}
			if (keyFour && selectedSkill < 3) {
				keyFour = !keyFour;
				selectedSkill++;
			}
		}

		if (skillSelectMenu) {
			if (keySix) {
				keySix = !keySix;
				assignSkill(currentPlayer, selectedSkill, selectedSkillChoice, selectedSkillChoiceHorizontal);
				selectedSkillChoice = 0;
				selectedSkillChoiceHorizontal = 0;
				skillSelectMenu = false;
			}
			if (keyEight) {
				keyEight = !keyEight;
				selectedSkillChoice = 0;
				selectedSkillChoiceHorizontal = 0;
				skillSelectMenu = false;
			}

			if (keyOne && selectedSkillChoice < 8) {
				keyOne = !keyOne;
				selectedSkillChoice++;
				tooltipWidth = 0;
				tooltipHeight = 0;
			}

			if (keyTwo && selectedSkillChoice > 0) {
				keyTwo = !keyTwo;
				selectedSkillChoice--;
				tooltipWidth = 0;
				tooltipHeight = 0;
			}

			if (keyThree && selectedSkillChoiceHorizontal > 0) {
				keyThree = !keyThree;
				selectedSkillChoiceHorizontal--;
				textAlpha = 0.0f;

			}
			if (keyFour && selectedSkillChoiceHorizontal < 2) {
				keyFour = !keyFour;
				selectedSkillChoiceHorizontal++;
				textAlpha = 0.0f;
			}

		}

		if (tooltipWidth >= tooltipMaxWidth - 20) {
			tooltip = true;
		} else {
			tooltip = false;
		}
	}

	public void render(Graphics2D g2d) {
		//		g2d.setComposite(makeComposite(1));
		//		g2d.setColor(Color.BLACK);
		//		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g2d.setComposite(makeComposite(0.4f));
		g2d.drawImage(pcBackground[currentPlayer], 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g2d.setComposite(makeComposite(0.8f));
		g2d.drawImage(background[currentPlayer], 0, 0, Game.WIDTH, Game.HEIGHT, null);

		g2d.setComposite(makeComposite(0.9f));
		//BOTTOM BAR
		g2d.fillRoundRect(25, Game.HEIGHT - Game.HEIGHT / 5, Game.WIDTH / 2, Game.HEIGHT / 5, 45, 45);
		g2d.setComposite(makeComposite(1f));

		//		g2d.fillRect(Game.WIDTH-(Game.WIDTH/4), 0, Game.WIDTH/4, Game.HEIGHT-(Game.HEIGHT/5));

		//DRAWING OF PLAYER IN UPPER RIGHT CORNER
		g2d.drawImage(player[currentPlayer], Game.WIDTH - (Game.WIDTH / 7)+(Game.WIDTH/250), Game.HEIGHT / 20, Game.WIDTH / 10, Game.HEIGHT / 4 + Game.HEIGHT / 14, null);
		
		//DRAWING OF XP BAR BELOW CHARACTER
		g2d.setColor(Color.WHITE);
		//BAR BACKGROUND
		g2d.setComposite(makeComposite(0.3f));
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6)+(Game.WIDTH/150), Game.HEIGHT / 3+(Game.HEIGHT/15), Game.WIDTH / 7, 35);
		g2d.setComposite(makeComposite(1.0f));
		//BAR
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6)+(Game.WIDTH/150), Game.HEIGHT / 3+(Game.HEIGHT/15), (int)Game.clamp((float)levelProgress, 0, Game.WIDTH / 7), 35);
		//BORDER
		g2d.setColor(Color.BLACK);
		g2d.drawRect(Game.WIDTH - (Game.WIDTH / 6)+(Game.WIDTH/150), Game.HEIGHT / 3+(Game.HEIGHT/15), Game.WIDTH / 7, 35);
		//CURRENT LEVEL OF PLAYER DRAWSTRING
		g2d.setColor(Color.WHITE);
		Font levelFont = new Font("Arial", 3, 25);
		g2d.setFont(levelFont);
		g2d.drawString("Level "+ExperienceHandler.getCurrentPlayerLevel(currentPlayer), Game.WIDTH - (Game.WIDTH / 6)+(Game.WIDTH/150), Game.HEIGHT / 3+(Game.HEIGHT/16));
		g2d.drawString(""+(long)ExperienceHandler.getCurrentXp(currentPlayer)+"/"+(long)ExperienceHandler.getXpToNext(currentPlayer),Game.WIDTH - (Game.WIDTH / 6)+(Game.WIDTH/150), Game.HEIGHT / 3+(Game.HEIGHT/8));
		
		levelProgress =((Game.WIDTH / 7)*(ExperienceHandler.getCurrentXp(currentPlayer))/ExperienceHandler.getXpToNext(currentPlayer));
		
		
		
//		Game.log(""+ExperienceHandler.getXpToNext(currentPlayer)+"|||||"+levelProgress+"||||||"+ExperienceHandler.getCurrentXp(currentPlayer)+"||curLevel||"+Player1Info.currentLevel);


		//DRAW SKILLS ONTO SKILLSELECTMENU
		for (int i = 0; i < frames.length; i++) {
			g2d.drawImage(frames[currentPlayer], Game.WIDTH / 10 + i * 150, Game.HEIGHT - Game.HEIGHT / 6, frameWidth, frameHeight, null);
			g2d.drawImage(skills[i], Game.WIDTH / 10 + 6 + (i * 150), Game.HEIGHT - Game.HEIGHT / 6 + 6, skillWidth, skillHeight, null);
		}

		//DRAW KEYBINDS FOR SKILLSELECTMENU
		g2d.setFont(customFont);
		g2d.setColor(Color.WHITE);
		g2d.drawString("A", Game.WIDTH / 9, Game.HEIGHT - Game.HEIGHT / 40);
		g2d.drawString("S", Game.WIDTH / 9 + 162, Game.HEIGHT - Game.HEIGHT / 40);
		g2d.drawString("D", Game.WIDTH / 9 + 308, Game.HEIGHT - Game.HEIGHT / 40);
		g2d.drawString("X", Game.WIDTH / 9 + 462, Game.HEIGHT - Game.HEIGHT / 40);

		if (menu) {
			g2d.setComposite(makeComposite(0.9f));
			g2d.setColor(Color.BLACK);
			g2d.fillRoundRect(Game.WIDTH - (Game.WIDTH / 6), Game.HEIGHT / 2 + (Game.HEIGHT / 15), Game.WIDTH / 6, Game.HEIGHT / 2, 45, 45);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Change Skills", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8));
			g2d.drawString("Use Points", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8) + 75);
			g2d.drawString("Back", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8) + 150);
			g2d.setComposite(makeComposite(0.75f));
			g2d.drawImage(underline[currentPlayer], Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8) + (selectedMenuPoint * 75), Game.WIDTH / 7, 12, null);
		} else {
			g2d.setComposite(makeComposite(0.5f));
			g2d.setColor(Color.BLACK);
			g2d.fillRoundRect(Game.WIDTH - (Game.WIDTH / 6), Game.HEIGHT / 2 + (Game.HEIGHT / 15), Game.WIDTH / 6, Game.HEIGHT / 2, 45, 45);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Change Skills", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8));
			g2d.drawString("Use Points", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8) + 75);
			g2d.drawString("Back", Game.WIDTH - (Game.WIDTH / 6) + 15, Game.HEIGHT / 2 + (Game.HEIGHT / 8) + 150);
		}

		if (skillMenu) {
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(underline[currentPlayer], Game.WIDTH / 9 - (Game.WIDTH / 300) + selectedSkill * 154, Game.HEIGHT - Game.HEIGHT / 35, frameWidth / 2, 15, null);
		}

		if (skillSelectMenu) {

			if (currentPlayer == 0) {

				//LEVEL1
				for (int i = 0; i < p1SkillsL1.length; i++) {
					if (P1SkillHandler.getUnlockStatus(1, i)) {
						g2d.drawImage(p1SkillsL1[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else {
						g2d.setComposite(makeComposite(0.75f));
						g2d.setColor(Color.BLACK);
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setColor(Color.WHITE);
						g2d.setComposite(makeComposite(1f));

					}
				}

				//LEVEL2
				for (int i = 0; i < p1SkillsL2.length; i++) {
					if (P1SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.drawImage(p1SkillsL2[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P1SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}

				}

				//LEVEL3
				for (int i = 0; i < p1SkillsL3.length; i++) {
					if (P1SkillHandler.getUnlockStatus(3, i) && i == selectedSkillChoice) {

						g2d.drawImage(p1SkillsL3[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P1SkillHandler.getUnlockStatus(3, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}
				}
			}

			//Player 2

			if (currentPlayer == 1) {
				for (int i = 0; i < p2SkillsL1.length; i++) {
					if (P2SkillHandler.getUnlockStatus(1, i)) {
						g2d.drawImage(p2SkillsL1[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else {
						g2d.setComposite(makeComposite(0.75f));
						g2d.setColor(Color.BLACK);
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setColor(Color.WHITE);
						g2d.setComposite(makeComposite(1f));
						g2d.drawString("?", Game.WIDTH / 10 + 16 + (selectedSkill * 150) + (Game.WIDTH / 45), Game.HEIGHT - Game.HEIGHT / 6 + (Game.WIDTH / 30) + i * -frameHeight + (i * -10) - (Game.HEIGHT / 7));

					}
				}
				//LEVEL2
				for (int i = 0; i < p2SkillsL2.length; i++) {
					if (P2SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.drawImage(p2SkillsL2[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P2SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}
				}

				//LEVEL3
				for (int i = 0; i < p2SkillsL3.length; i++) {
					if (P2SkillHandler.getUnlockStatus(3, i) && i == selectedSkillChoice) {
						g2d.drawImage(p2SkillsL3[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P2SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}
				}
			}

			//Player 3

			if (currentPlayer == 2) {
				for (int i = 0; i < p3SkillsL1.length; i++) {
					if (P2SkillHandler.getUnlockStatus(1, i)) {
						g2d.drawImage(p3SkillsL1[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else {
						g2d.setComposite(makeComposite(0.75f));
						g2d.setColor(Color.BLACK);
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setColor(Color.WHITE);
						g2d.drawString("?", Game.WIDTH / 10 + 16 + (selectedSkill * 150) + (Game.WIDTH / 45), Game.HEIGHT - Game.HEIGHT / 6 + (Game.WIDTH / 30) + i * -frameHeight + (i * -10) - (Game.HEIGHT / 7));
						g2d.setComposite(makeComposite(1f));
					}
				}
				//LEVEL2
				for (int i = 0; i < p3SkillsL2.length; i++) {
					if (P1SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.drawImage(p3SkillsL2[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P3SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (iconWidth + (iconWidth / 4)), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}

				}
				//LEVEL3
				for (int i = 0; i < p3SkillsL3.length; i++) {
					if (P1SkillHandler.getUnlockStatus(3, i) && i == selectedSkillChoice) {
						g2d.drawImage(p3SkillsL3[i], Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
					} else if (!P3SkillHandler.getUnlockStatus(2, i) && i == selectedSkillChoice) {
						g2d.setColor(Color.BLACK);
						g2d.setComposite(makeComposite(0.75f));
						g2d.fillRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 2 * iconWidth + (iconWidth / 2), Game.HEIGHT - Game.HEIGHT / 6 + i * -iconHeight + (i * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight);
						g2d.setComposite(makeComposite(1f));
					}

				}
			}
			//Draw frame on selected skill choice
			g2d.drawImage(frames[currentPlayer],
					//determines frame x position based on selected skill/keybind
					Game.WIDTH / 10 + 13 + (selectedSkill * 150) + (selectedSkillChoiceHorizontal * (iconWidth + iconWidth / 4)),
					//determines frame y position based on start of the for loop that draws the acutal skills(seen above)
					//adds y coordinte to start based on currentSkillChoice * iconHeight
					//then proceeds to do some random fucking magic to make things align
					Game.HEIGHT - Game.HEIGHT / 6 + selectedSkillChoice * -iconHeight + (selectedSkillChoice * -10) - (Game.HEIGHT / 7), iconWidth, iconHeight, null);
		}

		currentSelectedSkillAsString = Integer.toString(currentPlayer + 1) + Integer.toString(selectedSkillChoice + 1);
		currentSelectedSkillAsInteger = Integer.parseInt(currentSelectedSkillAsString);
		if (skillSelectMenu) {
			renderTooltip(g2d, currentSelectedSkillAsInteger, (selectedSkillChoiceHorizontal + 1));
		}

		g2d.setComposite(makeComposite(alpha));
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

	}

	public void renderTooltip(Graphics2D g2d, int skillId, int level) {

		g2d.setColor(Color.BLACK);
		g2d.setComposite(makeComposite(0.8f));

		if (!tooltip) {
			textAlpha = 0.01f;
		}

		tooltipWidth += 12f;
		tooltipHeight += 7f;
		if (selectedSkillChoice >= 4) {
			tooltipX = Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 4 * iconWidth;
			tooltipY = Game.HEIGHT - Game.HEIGHT / 6 + selectedSkillChoice * -iconHeight + (selectedSkillChoice * -10) - (Game.HEIGHT / 6) + iconWidth / 6;
			g2d.fillRoundRect(tooltipX, tooltipY, (int) Game.clamp(tooltipWidth, 0, tooltipMaxWidth), (int) Game.clamp(tooltipHeight, 0, tooltipMaxHeight), 25, 25);
		}
		if (selectedSkillChoice < 4) {
			tooltipX = Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 4 * iconWidth;
			tooltipY = Game.HEIGHT - Game.HEIGHT / 3 + selectedSkillChoice * -iconHeight + (selectedSkillChoice * -10) - (Game.HEIGHT / 6) + iconWidth / 6;
			g2d.fillRoundRect(tooltipX, tooltipY, (int) Game.clamp(tooltipWidth, 0, tooltipMaxWidth), (int) Game.clamp(tooltipHeight, 0, tooltipMaxHeight), 25, 25);
		}
		//		if (selectedSkillChoice == 4) {
		//			g2d.fillRoundRect(Game.WIDTH / 10 + 13 + (selectedSkill * 150) + 4 * iconWidth, Game.HEIGHT - Game.HEIGHT / 6 + selectedSkillChoice * -iconHeight + (selectedSkillChoice * -10) - (Game.HEIGHT / 6) - iconWidth/2, (int) Game.clamp(tooltipWidth, 0, 300), (int) Game.clamp(tooltipHeight, 0, 300), 25, 25);
		//		}
		/*
		 * SkillHandler -> PxSkillHandler -> skill -> get();
		 */
		cooldown = SkillHandler.getSkillCooldown(currentPlayer, skillId, level);
		duration = SkillHandler.getSkillDuration(currentPlayer, skillId, level);
		skillName = SkillHandler.getSkillName(currentPlayer, skillId);
		skillDescription = SkillHandler.getSkillDescription(currentPlayer, skillId, level); //SkillDescription is an array length of [3]

		Font fnt = new Font("Arial", 3, 32);
		Font fnt2 = new Font("Arial", 1, 25);
		Font fnt3 = new Font("Arial", 2, 20);
		g2d.setColor(Color.white);

		if (tooltip) {
			g2d.setComposite(makeComposite(textAlpha));
			if (textAlpha < 0.9f) {
				textAlpha += 0.008f;
			}
			if (currentPlayer == 0) {
				if (P1SkillHandler.getUnlockStatus(level, selectedSkillChoice)) {
					g2d.setFont(fnt);
					g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));

					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: " + (cooldown / 1000) + "s", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					if (duration != 0) {
						g2d.drawString("Duration: " + (duration / 1000) + "s", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					}
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("" + skillDescription[i], tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				} else {
					g2d.setFont(fnt);
					if (P1SkillHandler.getUnlockStatus((1), selectedSkillChoice)) {
						g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					} else {
						g2d.drawString("???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					}
					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: ???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Duration: ???", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("???????????????", tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				}
			}
			if (currentPlayer == 1) {
				if (P2SkillHandler.getUnlockStatus(level, selectedSkillChoice)) {

					g2d.setFont(fnt);
					g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));

					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: " + (cooldown / 1000) + "s", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					if (duration != 0) {
						g2d.drawString("Duration: " + (duration / 1000) + "s", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					}
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("" + skillDescription[i], tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				} else {
					g2d.setFont(fnt);
					if (P1SkillHandler.getUnlockStatus((1), selectedSkillChoice)) {
						g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					} else {
						g2d.drawString("???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					}
					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: ???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Duration: ???", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("???????????????", tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				}
			}
			if (currentPlayer == 2) {
				if (P3SkillHandler.getUnlockStatus(level, selectedSkillChoice)) {

					g2d.setFont(fnt);
					g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));

					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: " + (cooldown / 1000) + "s", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					if (duration != 0) {
						g2d.drawString("Duration: " + (duration / 1000) + "s", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					}
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("" + skillDescription[i], tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				} else {
					g2d.setFont(fnt);
					if (P3SkillHandler.getUnlockStatus((1), selectedSkillChoice)) {
						g2d.drawString("" + skillName, tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					} else {
						g2d.drawString("???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 6));
					}
					g2d.setFont(fnt2);
					g2d.drawString("Cooldown: ???", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Duration: ???", tooltipX + 35 + (tooltipMaxWidth / 2), tooltipY + (tooltipMaxHeight / 2));
					g2d.drawString("Description: ", tooltipX + 20, tooltipY + (tooltipMaxHeight / 2) + (tooltipMaxHeight / 4));

					g2d.setFont(fnt3);
					g2d.drawString("Rank " + level, tooltipX + tooltipMaxWidth - (tooltipMaxWidth / 5), tooltipY + (tooltipMaxHeight / 6));
					for (int i = 0; i < skillDescription.length; i++) {
						g2d.drawString("???????????????", tooltipX + tooltipMaxWidth / 4 + (tooltipMaxWidth / 9), tooltipY + (tooltipMaxHeight / 2) + (3 + i) * (tooltipMaxHeight / 11));
					}
				}
			}

		}

	}

	public void refreshIcons() {

		try {
			if (currentPlayer == 0) {
				skills[0] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p1Slot[0]) + ".png"));
				skills[1] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p1Slot[1]) + ".png"));
				skills[2] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p1Slot[2]) + ".png"));
				skills[3] = ImageIO.read(new File("bin/used/skillIcons/p" + (currentPlayer + 1) + "bomb" + Integer.toString(SkillHandler.p1Slot[3]) + ".png"));
			}

			if (currentPlayer == 1) {
				skills[0] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p2Slot[0]) + ".png"));
				skills[1] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p2Slot[1]) + ".png"));
				skills[2] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p2Slot[2]) + ".png"));
				skills[3] = ImageIO.read(new File("bin/used/skillIcons/p" + (currentPlayer + 1) + "bomb" + Integer.toString(SkillHandler.p2Slot[3]) + ".png"));
			}

			if (currentPlayer == 2) {
				skills[0] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p3Slot[0]) + ".png"));
				skills[1] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p3Slot[1]) + ".png"));
				skills[2] = ImageIO.read(new File("bin/used/skillIcons/skill" + Integer.toString(SkillHandler.p3Slot[2]) + ".png"));
				skills[3] = ImageIO.read(new File("bin/used/skillIcons/p" + (currentPlayer + 1) + "bomb" + Integer.toString(SkillHandler.p3Slot[3]) + ".png"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void assignSkill(int player, int selectedSkill, int selectedSkillChoice, int selectedSkillChoiceHorizontal) {

		//player, selectedSkillChoice, selectedSkillChoiceHorizontal

		String skill, skillChoice, skillChoiceVertical;

		skill = Integer.toString((player + 1));
		skillChoice = Integer.toString((selectedSkillChoice + 1));
		skillChoiceVertical = Integer.toString((selectedSkillChoiceHorizontal + 1));
		skillID = skill + skillChoice + skillChoiceVertical;
		skillIDNumber = Integer.parseInt(skillID);
		//		skillID = Integer.toString((currentPlayer+1) +""+ (selectedSkillChoice+1) +""+(selectedSkillChoiceHorizontal+1));

		if (player == 0) {
			SkillHandler.p1Slot[selectedSkill] = skillIDNumber;
		}

		if (player == 1) {
			SkillHandler.p2Slot[selectedSkill] = skillIDNumber;
		}

		if (player == 2) {
			SkillHandler.p3Slot[selectedSkill] = skillIDNumber;
		}
		writeToSave(player, skillIDNumber, selectedSkill);
		refreshIcons();

	}

	private void writeToSave(int player, int skillId, int skillSlot) {

		fileName = "bin/saves/" + MainSettings.currentSaveFile + "/p" + Integer.toString(player) + "/skillSlot.txt";
		if (player == 0) {
			for (int i = 0; i < saveSkills.length; i++) {
				saveSkills[i] = SkillHandler.p1Slot[i];
			}
		}
		if (player == 1) {
			for (int i = 0; i < saveSkills.length; i++) {
				saveSkills[i] = SkillHandler.p2Slot[i];
			}
		}
		if (player == 2) {
			for (int i = 0; i < saveSkills.length; i++) {
				saveSkills[i] = SkillHandler.p3Slot[i];
			}
		}

		saveSkills[skillSlot] = skillId;

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < saveSkills.length; i++) {
				writer.write(Integer.toString(saveSkills[i]));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		a = 0; //reset a for future use of method

	}

}
