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
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;

public class NewGame extends KeyInput{
	
	private Image titleAfter, titleRebirth, background, underline;
	
	private int selectedSave;
	
	private String fileName;
	
	private boolean rowsLeft = true, end = false;
	
	private BufferedReader reader;
	
	private Font customFont;
	
	public NewGame(Handler handler){
		
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
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Covington-sc.shadow.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
		
	public void tick(){
		
		if(keyOne && selectedSave>0){
			keyOne = !keyOne;
		}
		
		
	}
	
	public void render(Graphics2D g2d){
		
		Font fnt = new Font("Colonna MT", 0, 45);
		g2d.setFont(customFont);
		g2d.setColor(Color.BLACK);
		
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

		g2d.drawImage(titleAfter, Game.WIDTH/5,  Game.HEIGHT/100-(Game.HEIGHT/20), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH/2-(Game.WIDTH/10), Game.HEIGHT/100+(Game.HEIGHT/10), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/20), null);
		
	}
	
	public void read(){
		
		fileName = "bin/Saves/"+selectedSave+"/General.txt";
		
		//read general file
		
	}
	
	public void write(){
		
		
		
		
		
	}
	
}
