package com.game.displays;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class InfoMenu extends KeyInput{

	private int startButtonsAt = 3;
	private int maxButtons = 4;
	private int selectedButton = startButtonsAt;
	private Image title;
	private Image[] button = new Image[maxButtons];
	private Image background;
	
	public InfoMenu(Handler handler) {
		super(handler);

		try {
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
			
			button[3] = ImageIO.read(new File("bin/used/info-Patchnotes.png"));
//			button[4] = ImageIO.read(new File("bin/used/info-Patchnotes.png"));
//			button[5] = ImageIO.read(new File("bin/used/info-Patchnotes.png"));
//			button[6] = ImageIO.read(new File("bin/used/info-Back.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tick(){
		if (keySix){
			keySix = false;
			if (selectedButton == 3){
				Game.gameState = STATE.PatchNotes;
			}
			if (selectedButton == 4){
			}
			if (selectedButton == 5){
			}
			if (selectedButton == 6){
			}
		}
		
		if(keyOne){
			keyOne=false;
			selectedButton--;
			if(selectedButton < startButtonsAt){
				selectedButton = startButtonsAt;
			}
		}
		if(keyTwo){
			keyTwo=false;
			selectedButton++;
			if(selectedButton > maxButtons-1){
				selectedButton = maxButtons-1;
			}
		}
		
		if(keyEight){
			keyEight = false;
			Game.gameState = STATE.MainSettings;
		}
		
	}
	
	public void render(Graphics2D g2d){
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		for(int i = startButtonsAt; i< maxButtons; i++){
			if(i != selectedButton){
				g2d.drawImage(button[i], Game.WIDTH/15, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
			//DRAW SELECTED BUTTON MOVED TO THE RIGHT
			if(i == selectedButton){
				g2d.drawImage(button[i], Game.WIDTH/8, i*Game.HEIGHT/8, Game.WIDTH/4, Game.HEIGHT/8, null);
			}
		}
		
	}
	
}
