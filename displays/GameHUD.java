package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.ExperienceHandler;
import com.game.engine.Game;
import com.game.engine.KeyInput;
import com.game.engine.SkillHandler;
import com.game.levels.LevelOneEasy;
import com.game.player1.Player1;
import com.game.player1.Player1HighScores;
import com.game.player1.Player1Info;
import com.game.player2.Player2;
import com.game.player3.Player3;

public class GameHUD{

	private float levelTimer;
	private double progStep1, progStep2, progStep3;
	private double prog1, prog2, prog3;
	private double prog4; //for bombs
	private double levelProgress;
	private static int currentPlayer;

	private long now;
	private long useTime;
	private long timeDelta1, timeDelta2;

	private int frameSize = 75;
	private int bombFrameSize = 100;
	private int iconSize = 65;

	public static int leftBorderSize = 200;
	public static int rightBorderSize = 300;

	private Image[] border = new Image[20];
	private Image[] icons = new Image[4];
	private Image frame;
	private Image underline, lifeStar, bombStar;

	private static Image skillFrame;
	private static Image[] skills = new Image[4];

	public static String[] skillId = new String[3];

	public static long skill1UseTime, skill2UseTime, skill3UseTime;
	public static long bombUseTime;

	private boolean loaded = false;

	public void GameHud() {

		try {
			underline = ImageIO.read(new File("bin/used/underline.png"));
			lifeStar = ImageIO.read(new File("bin/used/lifeStar.png"));
			bombStar = ImageIO.read(new File("bin/used/bombStar.png"));

			border[0] = ImageIO.read(new File("bin/used/l1backleft.png"));
			border[1] = ImageIO.read(new File("bin/used/l1backright.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		levelTimer = getLevelTimer();

	}

	public static void setIcons() {
		//This method is called from the reset() methods from individual levels
		currentPlayer = SkillHandler.currentPlayer;

		try {
			skillFrame = ImageIO.read(new File("bin/used/framep" + Integer.toString(currentPlayer + 1) + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//		
		if (currentPlayer == 0) {
			try {
				for (int i = 0; i < 3; i++) {
					skills[i] = ImageIO.read(new File("bin/used/skillIcons/skill" + SkillHandler.p1Slot[i] + ".png"));
				}
				skills[3] = ImageIO.read(new File("bin/used/skillIcons/p1bomb" + SkillHandler.p1Slot[3] + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (currentPlayer == 1) {
			try {
				for (int i = 0; i < 3; i++) {
					skills[i] = ImageIO.read(new File("bin/used/skillIcons/skill" + SkillHandler.p2Slot[i] + ".png"));
					skills[3] = ImageIO.read(new File("bin/used/skillIcons/p2bomb" + SkillHandler.p2Slot[3] + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		if (currentPlayer == 2) {
			try {
				for (int i = 0; i < 3; i++) {
					skills[i] = ImageIO.read(new File("bin/used/skillIcons/skill" + SkillHandler.p3Slot[i] + ".png"));
					skills[3] = ImageIO.read(new File("bin/used/skillIcons/p3bomb" + SkillHandler.p3Slot[3] + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}

	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public void tick() {

		if (!loaded) {
			GameHud();
			loaded = true;
		}
		levelTimer--;

		if (currentPlayer == 0) {

			prog1 = (frameSize - 4) / ((Player1.skill1Cooldown / 1000) / (Player1.skill1CooldownLeft / 1000));
			prog2 = (frameSize - 4) / ((Player1.skill2Cooldown / 1000) / (Player1.skill2CooldownLeft / 1000));
			prog3 = (frameSize - 4) / ((Player1.skill2Cooldown / 1000) / (Player1.skill3CooldownLeft / 1000));
			prog4 = (bombFrameSize - 8) / ((Player1.bombCooldown / 1000) / (Player1.bombCooldownLeft / 1000));
		}
		//		if(currentPlayer == 1){
		//			prog1 = (frameSize-4) / ((Player2.skill1Cooldown/1000)/(Player1.skill1CooldownLeft/1000));
		//			prog2 = (frameSize-4) / ((Player2.skill2Cooldown/1000)/(Player1.skill2CooldownLeft/1000));
		//			prog3 = (frameSize-4) / ((Player2.skill2Cooldown/1000)/(Player1.skill3CooldownLeft/1000));	
		//		}
		//		if(currentPlayer == 2){
		//			prog1 = (frameSize-4) / ((Player3.skill1Cooldown/1000)/(Player1.skill1CooldownLeft/1000));
		//			prog2 = (frameSize-4) / ((Player3.skill2Cooldown/1000)/(Player1.skill2CooldownLeft/1000));
		//			prog3 = (frameSize-4) / ((Player3.skill2Cooldown/1000)/(Player1.skill3CooldownLeft/1000));	
		//		}
		//		
		levelProgress =((Game.WIDTH / 7)*(ExperienceHandler.getCurrentXp(currentPlayer))/ExperienceHandler.getXpToNext(currentPlayer));
		
//		if(KeyInput.keyThree){
//			KeyInput.keyThree = !KeyInput.keyThree;
//			Player1Info.xp-=1000;
//		}
//		if(KeyInput.keyFour){
//			KeyInput.keyFour = !KeyInput.keyFour;
//			Player1Info.xp+=1000;
//		}

			
	}
	
	

	public void render(Graphics2D g2d) {
		//SKILL BAR
		//		g2d.setColor(Color.black);
		//		g2d.fillRect(0, Game.HEIGHT-Game.HEIGHT/10, Game.WIDTH, Game.HEIGHT-Game.HEIGHT/10);

		//BORDERS
		if (MainSettings.currentLevel == 1)
			g2d.drawImage(border[0], 0, 0, leftBorderSize, Game.HEIGHT, null);
		g2d.drawImage(border[1], Game.WIDTH - 300, 0, rightBorderSize, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 2)
		//		g2d.drawImage(border[2], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[3], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 3)
		//		g2d.drawImage(border[4], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[5], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 4)
		//		g2d.drawImage(border[6], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[7], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 5)
		//		g2d.drawImage(border[8], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[9], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 6)
		//		g2d.drawImage(border[10], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[11], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 7)
		//		g2d.drawImage(border[12], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[13], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 8)
		//		g2d.drawImage(border[14], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[15], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 9)
		//		g2d.drawImage(border[16], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[17], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		//		if(MainSettings.currentLevel == 10)
		//		g2d.drawImage(border[18], 0, 0, 200, Game.HEIGHT, null);
		//		g2d.drawImage(border[19], Game.WIDTH-300, 0, 300, Game.HEIGHT, null);

		if (MainSettings.currentLevel == 999) {
			g2d.drawImage(border[0], 0, 0, leftBorderSize, Game.HEIGHT, null);
			g2d.drawImage(border[1], Game.WIDTH - 300, 0, rightBorderSize, Game.HEIGHT, null);
		}
		g2d.drawImage(underline, Game.WIDTH - 250, 170, 225, 75, null);
		g2d.drawImage(underline, Game.WIDTH - 250, 270, 225, 75, null);
		
		g2d.setColor(Color.white);
		//XP BAR BACKGROUND
		g2d.setComposite(makeComposite(0.3f));
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 50), Game.HEIGHT / 2 + (Game.HEIGHT / 6), Game.WIDTH / 7, 20);
		g2d.setComposite(makeComposite(1.0f));
		//XP BAR
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 50), Game.HEIGHT / 2 + (Game.HEIGHT / 6), (int) Game.clamp((float) levelProgress, 0, Game.WIDTH / 7), 20);
		//XP BORDER
		g2d.setColor(Color.BLACK);
		g2d.drawRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 50), Game.HEIGHT / 2 + (Game.HEIGHT / 6), Game.WIDTH / 7, 20);
		//XP BAR TEXT
		g2d.setColor(Color.white);
		Font fnt = new Font("Arial", 1, 20);
		g2d.setFont(fnt);
		g2d.drawString("Level: "+(int)Player1Info.currentLevel, Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 50), Game.HEIGHT / 2 + (Game.HEIGHT / 7)+10);
		g2d.drawString(""+(int)ExperienceHandler.getCurrentXp(currentPlayer)+"/"+(int)ExperienceHandler.getXpToNext(currentPlayer), Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 50), Game.HEIGHT / 2 + (Game.HEIGHT / 5)+10);

		//SKILLS

		//frames
		for (int i = 0; i < 3; i++) {
			g2d.drawImage(skillFrame, Game.WIDTH - 290 + (i * 100), Game.HEIGHT - 254, frameSize, frameSize, null);
		}

		//Bigger bomb frame
		g2d.drawImage(skillFrame, Game.WIDTH - 202, Game.HEIGHT - 150, bombFrameSize, bombFrameSize, null);

		//skill icons
		for (int i = 0; i < 3; i++) {
			g2d.drawImage(skills[i], Game.WIDTH - 285 + (i * 100), Game.HEIGHT - 247, iconSize, iconSize, null);
		}
		//bigger bomb frame
		g2d.drawImage(skills[3], Game.WIDTH - 197, Game.HEIGHT - 146, 90, 90, null);

		//normal skill Cooldowns
		g2d.setColor(Color.BLACK); //set fade color
		g2d.setComposite(makeComposite(0.85f)); //set fade opacity

		//Clamp all fade overlays by the size of Y
		g2d.fillRect(Game.WIDTH - 290 + (0 * 100) + 3, Game.HEIGHT - 247 + frameSize - 10, frameSize - 6, (int) Game.clamp((float) -prog1, -frameSize + 8, 0));
		g2d.fillRect(Game.WIDTH - 290 + (1 * 100) + 3, Game.HEIGHT - 247 + frameSize - 10, frameSize - 6, (int) Game.clamp((float) -prog2, -frameSize + 8, 0));
		g2d.fillRect(Game.WIDTH - 290 + (2 * 100) + 3, Game.HEIGHT - 247 + frameSize - 10, frameSize - 6, (int) Game.clamp((float) -prog3, -frameSize + 8, 0));
		//			g2d.fillRect(Game.WIDTH-197+3, Game.HEIGHT-146+frameSize-3, frameSize-6, (int) progB);
		g2d.setComposite(makeComposite(1f)); //make opacity of everything else 1 again

		//bomb skill cooldowns

		g2d.setColor(Color.BLACK); //set fade color
		g2d.setComposite(makeComposite(0.85f)); //set fade opacity
		//clamp fade overlay by the size of Y
		g2d.fillRect(Game.WIDTH - 198, Game.HEIGHT - 150 + bombFrameSize - 5, bombFrameSize - 8, (int) Game.clamp((float) -prog4, -bombFrameSize + 8, 0));

		g2d.setComposite(makeComposite(1f)); //make opacity of everything else 1 again

		Font fnt2 = new Font("Lucida Console", 3, 25);
		g2d.setColor(Color.white);
		g2d.setFont(fnt2);
		if (Player1.skill1CooldownLeft / 1000 > 0) {
			g2d.drawString("" + (int) Player1.skill1CooldownLeft / 1000, Game.WIDTH - 270 + (0 * 100), Game.HEIGHT - 247 + frameSize - 10);
		}
		if (Player1.skill2CooldownLeft / 1000 > 0) {
			g2d.drawString("" + (int) Player1.skill2CooldownLeft / 1000, Game.WIDTH - 270 + (1 * 100), Game.HEIGHT - 247 + frameSize - 10);
		}
		if (Player1.skill3CooldownLeft / 1000 > 0) {
			g2d.drawString("" + (int) Player1.skill2CooldownLeft / 1000, Game.WIDTH - 270 + (1 * 100), Game.HEIGHT - 247 + frameSize - 10);
		}
		if (Player1.bombCooldownLeft / 1000 > 0) {
			g2d.drawString("" + (int) Player1.bombCooldownLeft / 1000, Game.WIDTH - 198 + (bombFrameSize / 3 - 8), Game.HEIGHT - 150 + bombFrameSize - 5);
		}

		//			if(Player2.skill1CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill1CooldownLeft/1000, Game.WIDTH-270+(0*100), Game.HEIGHT-247+frameSize-10);
		//			}
		//			if(Player2.skill2CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill2CooldownLeft/1000, Game.WIDTH-270+(1*100), Game.HEIGHT-247+frameSize-10);
		//			}
		//			if(Player2.skill3CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill2CooldownLeft/1000, Game.WIDTH-270+(1*100), Game.HEIGHT-247+frameSize-10);
		//			}
		//			
		//			if(Player3.skill1CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill1CooldownLeft/1000, Game.WIDTH-270+(0*100), Game.HEIGHT-247+frameSize-10);
		//			}
		//			if(Player3.skill2CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill2CooldownLeft/1000, Game.WIDTH-270+(1*100), Game.HEIGHT-247+frameSize-10);
		//			}
		//			if(Player3.skill3CooldownLeft/1000 > 0){
		//				g2d.drawString(""+(int)Player1.skill2CooldownLeft/1000, Game.WIDTH-270+(1*100), Game.HEIGHT-247+frameSize-10);
		//			}

		//SCORES

		g2d.drawString("Best ", Game.WIDTH - 250, 45);

		g2d.drawString("Score ", Game.WIDTH - 250, 125);
		if (CharacterSelect.player == 0) {
			g2d.drawString("" + (int) Player1.playerScore, Game.WIDTH - 250, 150);
			g2d.drawString("" + Player1HighScores.bestScore, Game.WIDTH - 250, 70);
		}
		if (CharacterSelect.player == 1) {
			g2d.drawString("" + (int) Player2.playerScore, Game.WIDTH - 250, 150);
			//		g2d.drawString(""+Player2HighScores.bestScore, 1010, 70);
		}
		if (CharacterSelect.player == 2) {
			g2d.drawString("" + (int) Player3.playerScore, Game.WIDTH - 250, 150);
			//		g2d.drawString(""+Player3HighScores.bestScore, 1010, 70);
		}
		g2d.drawString("Lives", Game.WIDTH - 180, 200);

		g2d.drawString("Bombs", Game.WIDTH - 180, 300);

		//STARS
		if (CharacterSelect.player == 0) {
			if (Player1.playerHealth == 1) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 2) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);

			}
			if (Player1.playerHealth == 3) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 4) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 5) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 6) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 7) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 8) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
			}
			if (Player1.playerHealth == 9) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 10, 230, 25, 25, null);

			}
			//		g2d.drawImage(bombStar, 1000, 330, 25, 25, null);
			if (Player1.playerBombs == 1) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 2) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);

			}
			if (Player1.playerBombs == 3) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 4) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 5) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 6) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 7) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 8) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
			}
			if (Player1.playerBombs == 9) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 10, 330, 25, 25, null);

			}
		}

		if (CharacterSelect.player == 1) {
			if (Player2.playerHealth == 1) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 2) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);

			}
			if (Player2.playerHealth == 3) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 4) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 5) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 6) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 7) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 8) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
			}
			if (Player2.playerHealth == 9) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 10, 230, 25, 25, null);

			}
			//	g2d.drawImage(bombStar, 1000, 330, 25, 25, null);
			if (Player2.playerBombs == 1) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 2) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);

			}
			if (Player2.playerBombs == 3) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 4) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 5) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 6) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 7) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 8) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
			}
			if (Player2.playerBombs == 9) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 10, 330, 25, 25, null);

			}
		}

		if (CharacterSelect.player == 2) {
			if (Player3.playerHealth == 1) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 2) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);

			}
			if (Player3.playerHealth == 3) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 4) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 5) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 6) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 7) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 8) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
			}
			if (Player3.playerHealth == 9) {
				g2d.drawImage(lifeStar, Game.WIDTH - 250, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 220, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 190, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 160, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 130, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 100, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 70, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 40, 230, 25, 25, null);
				g2d.drawImage(lifeStar, Game.WIDTH - 10, 230, 25, 25, null);

			}
			//	g2d.drawImage(bombStar, 1000, 330, 25, 25, null);
			if (Player3.playerBombs == 1) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 2) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);

			}
			if (Player3.playerBombs == 3) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 4) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 5) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 6) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 7) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 8) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
			}
			if (Player3.playerBombs == 9) {
				g2d.drawImage(bombStar, Game.WIDTH - 250, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 220, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 190, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 160, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 130, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 100, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 70, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 40, 330, 25, 25, null);
				g2d.drawImage(bombStar, Game.WIDTH - 10, 330, 25, 25, null);

			}
		}
	}

	public int getLevelTimer() {
		return (int) levelTimer;
	}

	public void setLevelTimer(int levelTimer) {
		this.levelTimer = levelTimer;
	}

}
