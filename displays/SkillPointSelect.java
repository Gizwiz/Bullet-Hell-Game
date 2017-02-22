package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.ExperienceHandler;
import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.engine.SkillHandler;
import com.game.enums.STATE;
import com.game.player1Skills.P1SkillHandler;

public class SkillPointSelect extends KeyInput {

	Handler handler;

	private int frameWidth = 100;
	private int frameHeight = 98;
	private int iconWidth = 75;
	private int iconHeight = 75;
	private int skillWidth = 90;
	private int skillHeight = 90;
	private int currentPlayer;
	private int startFrameAt;
	private int currentFrameAt;
	private int frameX = Game.WIDTH / 10 -5;
	private int frameY = Game.WIDTH/5 -5;
	
	private int skillX;
	private int skillY;

	private double levelProgress;

	private Image[] player = new Image[3];
	private Image[] background = new Image[3];
	private Image[] pcBackground = new Image[3];
	private Image[] underline = new Image[3];

	private Image[] p1SkillsL1 = new Image[9];
	private Image[] p2SkillsL1 = new Image[9];
	private Image[] p3SkillsL1 = new Image[9];

	private Image[] p1SkillsL2 = new Image[9];
	private Image[] p2SkillsL2 = new Image[9];
	private Image[] p3SkillsL2 = new Image[9];

	private Image[] p1SkillsL3 = new Image[9];
	private Image[] p2SkillsL3 = new Image[9];
	private Image[] p3SkillsL3 = new Image[9];
	
	private Image[] frame = new Image[3];

	private Font customFont;

	public SkillPointSelect(Handler handler) {
		super(handler);
		this.handler = handler;

		try {
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
			
			frame[0] = ImageIO.read(new File("bin/used/framep1.png"));
			frame[1] = ImageIO.read(new File("bin/used/framep2.png"));
			frame[2] = ImageIO.read(new File("bin/used/framep3.png"));
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
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(75f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(currentPlayer == 0){
			startFrameAt = 111;
		}
		if(currentPlayer == 1){
			startFrameAt = 211;
		}
		if(currentPlayer == 2){
			startFrameAt = 311;
		}
		
		currentFrameAt = startFrameAt;
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	public void tick() {
		currentPlayer = SkillHandler.currentPlayer;

		if (keyEight) {
			keyEight = !keyEight;
			currentFrameAt = startFrameAt;
			frameY = Game.WIDTH/5 -5;
			frameX = Game.WIDTH / 10 -5;
			Game.gameState = STATE.SkillSelect;
		}
		
		

	}


	public void render(Graphics2D g2d) {

		g2d.setComposite(makeComposite(0.4f));
		g2d.drawImage(pcBackground[currentPlayer], 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g2d.setComposite(makeComposite(0.8f));
		g2d.drawImage(background[currentPlayer], 0, 0, Game.WIDTH, Game.HEIGHT, null);

		g2d.setComposite(makeComposite(0.75f));
		g2d.fillRect(Game.WIDTH / 40, Game.HEIGHT / 40, Game.WIDTH - (Game.WIDTH / 5), Game.HEIGHT - (Game.HEIGHT / 20));
		g2d.setComposite(makeComposite(1f));

		g2d.setColor(Color.WHITE);
		g2d.setFont(customFont);

		//DRAW SKILL TREES

		

	g2d.setStroke(new BasicStroke(1));
	//		g2d.fillRect(Game.WIDTH-(Game.WIDTH/4), 0, Game.WIDTH/4, Game.HEIGHT-(Game.HEIGHT/5));

	//DRAWING OF PLAYER IN UPPER RIGHT CORNER
	g2d.drawImage(player[currentPlayer],Game.WIDTH-(Game.WIDTH/7)+(Game.WIDTH/250),Game.HEIGHT/20,Game.WIDTH/10,Game.HEIGHT/4+Game.HEIGHT/14,null);

	//DRAWING OF XP BAR BELOW CHARACTER
	g2d.setColor(Color.WHITE);
	//BAR BACKGROUND
	g2d.setComposite(

	makeComposite(0.3f));
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 150), Game.HEIGHT / 3 + (Game.HEIGHT / 15), Game.WIDTH / 7, 35);
		g2d.setComposite(makeComposite(1.0f));
		//BAR
		g2d.fillRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 150), Game.HEIGHT / 3 + (Game.HEIGHT / 15), (int) Game.clamp((float) levelProgress, 0, Game.WIDTH / 7), 35);
		//BORDER
		g2d.setColor(Color.BLACK);
		g2d.drawRect(Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 150), Game.HEIGHT / 3 + (Game.HEIGHT / 15), Game.WIDTH / 7, 35);
		//CURRENT LEVEL OF PLAYER DRAWSTRING
		g2d.setColor(Color.WHITE);
		Font levelFont = new Font("Arial", 3, 25);
		g2d.setFont(levelFont);
		g2d.drawString("Level " + ExperienceHandler.getCurrentPlayerLevel(currentPlayer), Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 150), Game.HEIGHT / 3 + (Game.HEIGHT / 16));
		g2d.drawString("" + (long) ExperienceHandler.getCurrentXp(currentPlayer) + "/" + (long) ExperienceHandler.getXpToNext(currentPlayer), Game.WIDTH - (Game.WIDTH / 6) + (Game.WIDTH / 150), Game.HEIGHT / 3 + (Game.HEIGHT / 8));

		levelProgress = ((Game.WIDTH / 7) * (ExperienceHandler.getCurrentXp(currentPlayer)) / ExperienceHandler.getXpToNext(currentPlayer));

	}

}
