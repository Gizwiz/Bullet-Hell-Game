package com.game.displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.enums.STATE;

public class LoadingScreen {

	Handler handler;
	public double progress = 0;
	private double progBar;
	public static boolean loaded = false;
	private Image titleAfter, titleRebirth, background;
	private Font customFont;
	
	public LoadingScreen(Handler handler){
		this.handler = handler;
		
		try {
			titleAfter = ImageIO.read(new File("bin/used/title-after.png"));
			titleRebirth = ImageIO.read(new File("bin/used/title-rebirth.png"));
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Covington-sc.shadow.ttf")).deriveFont(75f);
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Covington-sc.shadow.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void tick(){
		
//		if(KeyInput.keySix){
//			progress = 1;
//		}
//		
//		if(progress < 19){
//			progress ++;
//		}
		
		progBar = progress*((Game.WIDTH-200)/Game.amountToLoad);
		if(Game.allLoaded == true){
			Game.gameState = STATE.MainMenu;
		}
		
	}
	
	public void render(Graphics2D g2d){
		
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

		g2d.drawImage(titleAfter, Game.WIDTH/5,  Game.HEIGHT/100-(Game.HEIGHT/20), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH/2-(Game.WIDTH/10), Game.HEIGHT/100+(Game.HEIGHT/10), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/20), null);
		
		
		g2d.setColor(Color.WHITE);
		g2d.drawRoundRect(Game.WIDTH-(Game.WIDTH-100), Game.HEIGHT/2+Game.HEIGHT/3+100, Game.WIDTH-200, 20, 50, 50);
		g2d.fillRoundRect(Game.WIDTH-(Game.WIDTH-100), Game.HEIGHT/2+Game.HEIGHT/3+100, (int)Game.clamp((int)progBar, 0, Game.WIDTH-200), 20, 50, 50);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(customFont);
		g2d.drawString("Loading...", Game.WIDTH/2-175,  Game.HEIGHT/2+Game.HEIGHT/3+75);
	}
		
	
}
