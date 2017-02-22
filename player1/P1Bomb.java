package com.game.player1;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.engine.SkillHandler;
import com.game.enums.ID;
import com.game.player1Skills.P1SkillHandler;

public class P1Bomb {

		P1SkillHandler p1SkillHandler;
		Handler handler;
		private int bombId;
		private double bombCooldown;
		private String bombName;
		
		GameObject bomb;
	
		public P1Bomb(Handler handler){
			this.handler = handler;
			p1SkillHandler = new P1SkillHandler(handler);
			
		}
		
		public GameObject getBomb(){
			
			bombId = SkillHandler.p1Slot[3];
			bomb = useBomb(bombId);				
			return bomb;
			
		}
		
		private GameObject useBomb(int bombId){
			
			switch(bombId){
			
			case 1: BombOne bombOne = new BombOne(Player1.playerX, Player1.playerY, ID.Bomb, 0, 0, handler);
					bombCooldown = bombOne.getCooldown();
					return bombOne;
			default: return null;
			
			}
			
		}
		
		public double getCooldown(){
			return bombCooldown;
		}

	
}
