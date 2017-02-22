package com.game.player1;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.engine.SkillHandler;
import com.game.player1Skills.P1SkillHandler;

public class P1Skill2{
	P1SkillHandler p1SkillHandler;
	Handler handler;
	private int skillId;
	private double skillCooldown;
	private String skillName;
	
	GameObject skill;
	
	public P1Skill2(Handler handler){
		this.handler = handler;
		p1SkillHandler = new P1SkillHandler(handler);
		
	}
	
	public GameObject getSkill(){
		
		skillId = SkillHandler.p1Slot[1];
		skill = p1SkillHandler.useSkill(skillId);
		
		//gets cooldown from P1SkillHandler class
		skillCooldown = p1SkillHandler.getCooldown();
		return skill;

	}

	//returns cooldown from getSkill() method to Player1
	public double getCooldown(){
		return skillCooldown;
	}

	public void tick() {
		
		
	}


	public void render(Graphics2D g2d) {

		
	}


	public Rectangle getBounds() {

		return null;
	}

}