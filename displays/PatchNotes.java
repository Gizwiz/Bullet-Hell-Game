package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class PatchNotes extends KeyInput{

	private String line = null;
	private String[] readLine = new String[1000000];
	private String fileName = "bin/used/patch.txt";
	private boolean rowsLeft = true, end = false;
	private Image up, down, background, titleAfter, titleRebirth;
	private int j=0;
	private int renderHeight = Game.WIDTH/20;
	LinkedList<String> list = new LinkedList<String>();
	
	private BufferedReader reader;
	public static String gameVersion;
	
	public PatchNotes(Handler handler) throws IOException{
		super(handler);
		reader = new BufferedReader(new FileReader(fileName));
		read();
		KeyInput.keySix = false;
		
		titleAfter = ImageIO.read(new File("bin/used/title-after.png"));
		titleRebirth = ImageIO.read(new File("bin/used/title-rebirth.png"));
		background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
		up = ImageIO.read(new File("bin/used/arrowUp.png"));
		down = ImageIO.read(new File("bin/used/arrowDown.png"));
		
	}
	
	public void read() throws IOException{
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while((line = bufferedReader.readLine()) != null){
			list.addLast(line);
			readLine[j] = line;
			j++;
		}
		gameVersion = readLine[0];
		bufferedReader.close();
		reader.close();
	}
	
	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	public void render(Graphics2D g2d){
		Font fnt = new Font("Calisto MT", 5, 20);
		Color purple = new Color(125, 46, 145);
		
		g2d.setFont(fnt);
		g2d.setColor(Color.WHITE);
		g2d.setComposite(makeComposite (0.3f));
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		g2d.setComposite(makeComposite (1f));
		g2d.drawImage(titleAfter, Game.WIDTH/4+(Game.WIDTH/3),  Game.HEIGHT/20-(Game.HEIGHT/20), Game.WIDTH/5, Game.HEIGHT/5-(Game.HEIGHT/25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH/2+(Game.WIDTH/6), Game.HEIGHT/100+(Game.HEIGHT/15), Game.WIDTH/5, Game.HEIGHT/5-(Game.HEIGHT/20), null);
		g2d.drawString("version " + gameVersion, Game.WIDTH/3+(Game.WIDTH/2), Game.HEIGHT/6-(Game.HEIGHT/7));
		g2d.drawString(" (shift scrolls faster)", Game.WIDTH-(Game.WIDTH/6), Game.HEIGHT/23);

		if(!keyOne && !keyTwo){
			
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(up, Game.WIDTH/50, Game.HEIGHT/2-(Game.HEIGHT/5), 80,80, null);
			g2d.drawImage(down, Game.WIDTH/50, Game.HEIGHT/2+(Game.HEIGHT/5), 80,80, null);
		}
		if(keyOne){
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(up, Game.WIDTH/50, Game.HEIGHT/2-(Game.HEIGHT/5), 80,80, null);
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(down, Game.WIDTH/50, Game.HEIGHT/2+(Game.HEIGHT/5), 80,80, null);
		}
		if(keyTwo){
			g2d.setComposite(makeComposite(1f));
			g2d.drawImage(down, Game.WIDTH/50, Game.HEIGHT/2+(Game.HEIGHT/5), 80,80, null);
			g2d.setComposite(makeComposite(0.5f));
			g2d.drawImage(up, Game.WIDTH/50, Game.HEIGHT/2-(Game.HEIGHT/5), 80,80, null);
		}


		g2d.setComposite(makeComposite(1));
		for(int i = 0; i<list.size(); i++){
			String renderLine = readLine[i];
			g2d.drawString(renderLine, Game.WIDTH/13, i*25+renderHeight);
		}
		//BLOCKS TEXT
		g2d.setColor(Color.BLACK);
//		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);

//		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
	
//		g2d.fillRect(0,0,Game.WIDTH,Game.HEIGHT/16);
//		g2d.fillRect(0,Game.HEIGHT-(Game.WIDTH/15),Game.WIDTH,Game.HEIGHT/5);
		
//		g2d.setColor(Color.WHITE);
//		g2d.drawRect(Game.WIDTH/20,Game.WIDTH/30,Game.WIDTH-(Game.WIDTH/10), Game.HEIGHT-(Game.WIDTH/10));
	}
	
	
	public void tick(){
		
		if(keyOne){
			if(keySeven){
				renderHeight += 10;
			}
			else{
			renderHeight += 2;
			}
			if(renderHeight>90){
				renderHeight = 90;
			}
		}
		if(keyTwo){
			if(keySeven){
				renderHeight -= 10;
			}
			else{
			renderHeight -= 2;
			}
			if(renderHeight < list.size()*-24){
				renderHeight = list.size()*-24;
			}
		}
		
		if(keySix || keyEight){
			keySix = false;
			keyEight = false;
			Game.gameState = STATE.Info;
		}
		
	}	
	
	
}
