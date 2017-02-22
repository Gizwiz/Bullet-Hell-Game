package com.game.displays;

import java.awt.Color;
import java.awt.Graphics2D;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class SmallSettings extends KeyInput{

	private int currentMap;
	
	public SmallSettings(Handler handler){
		super(handler);
	
	}
	
		
	public void tick(){
		
		if(keyEight){
			keyEight = !keyEight;
			Game.gameState=STATE.Map;
		}
		
	}
	
	public void render(Graphics2D g2d){
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}
	

}
	
