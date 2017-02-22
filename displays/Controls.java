package com.game.displays;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class Controls extends KeyInput{

	private Handler handler;
	
	private Image[] buttons = new Image[3];
	private Image arrowRight;
	private int posY = 200;
	private String[] controlString = new String[12];
	private String[] key = new String[12];
	public static int cursorY = 210;
		
	
	public Controls(Handler handler){
		super(handler);
		this.handler = handler;
		try {
			buttons[0] = ImageIO.read(new File("bin/used/button-left.png"));
			buttons[1] = ImageIO.read(new File("bin/used/button-middle.png"));
			buttons[2] = ImageIO.read(new File("bin/used/button-right.png"));
			arrowRight = ImageIO.read(new File("bin/used/arrowRight.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		controlString[0]="SHOOT / CONFIRM";
		controlString[1]="BOMB / BACK";
		controlString[2]="PHASE";
		controlString[3]="DASH";
		controlString[4]="PAUSE";
		controlString[5]="UP";
		controlString[6]="DOWN";
		controlString[7]="LEFT";
		controlString[8]="RIGHT";
		controlString[9]="SKILL 1";
		controlString[10]="SKILL 2";
		controlString[11]="SKILL 3";
		
		key[0] = "Z";
		key[1] = "X";
		key[2] = "SHIFT";
		key[3] = "CONTROL";
		key[4] = "ESC";
		key[5] = "ARROW_UP";
		key[6] = "ARROW_DOWN";
		key[7] = "ARROW_LEFT";
		key[8] = "ARROW_RIGHT";
	}
	
	public void tick(){
		if(keyEight){
			keyEight = false;
			Game.gameState = STATE.MainSettings;
		}
		//CURSOR DOWN
		if(keyTwo){
			Controls.cursorY += 80;
			if(Controls.cursorY > 770){
				Controls.cursorY = 210;
			}
		}
		//CURSOR UP
		if(keyOne){
			Controls.cursorY -= 80;
			if(Controls.cursorY < 210){
				Controls.cursorY = 770;
			}
		}
	}
	
	public void render(Graphics2D g2d){
		
		g2d.drawImage(arrowRight, Game.WIDTH/2-(Game.WIDTH/4), cursorY, 50,50, null);
		Font fnt1= new Font("Calisto MT", 2, 35);
		Font fnt2 = new Font("Calisto MT", 1, 55);
		g2d.setFont(fnt1);
		g2d.setColor(Color.white);
		for (int i = 0; i<8; i++){
			g2d.drawString(controlString[i], Game.WIDTH/2-(Game.WIDTH/5), i*80+250);
			g2d.drawString(key[i],  Game.WIDTH/2+(Game.WIDTH/20), i*80+250);
		}
		g2d.setFont(fnt2);
		g2d.drawString("Controls",  Game.WIDTH/2-(Game.WIDTH/15), 100);
	}
}
