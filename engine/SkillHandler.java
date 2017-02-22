package com.game.engine;

import com.game.enums.ID;
import com.game.player1Skills.P1SkillHandler;
import com.game.player2Skills.P2SkillHandler;
import com.game.player3Skills.P3SkillHandler;

public class SkillHandler{

	
	public static int currentPlayer;
	public static int slot1, slot2, slot3, bomb;
	public static String slotOne, slotTwo, slotThree, slotBomb;
	
	public static int[] p1Slot = new int[4];
	public static int[] p2Slot = new int[4];
	public static int[] p3Slot = new int[4];
	
	private static float cooldown;
	private static float duration;
	private static String skillName;
	private static String[] skillDescription = new String[3];
	
	
	public SkillHandler(){
//		slot1 = 111;
//
//		slot2 = 121;
//
//		slot3 = 131;
//
//		bomb = 1;
//
//		slotOne = Integer.toString(slot1);
//		slotTwo = Integer.toString(slot2);
//		slotThree = Integer.toString(slot3);
//		slotBomb = Integer.toString(bomb);
		
		
		this.p1Slot[0] = 111;
		this.p1Slot[1] = 121;
		this.p1Slot[2] = 131;
		this.p1Slot[3] = 1;
		
		this.p2Slot[0] = 111;
		this.p2Slot[1] = 121;
		this.p2Slot[2] = 131;
		this.p2Slot[3] = 1;
		
		this.p3Slot[0] = 111;
		this.p3Slot[1] = 121;
		this.p3Slot[2] = 131;
		this.p3Slot[3] = 1;
		
	}
	

	public SkillHandler(int[] slotArray, int player){
		
		//Determines current player from save number for use in skill select screen
		
		if(player == 0){
			this.p1Slot[0] = slotArray[0];
			this.p1Slot[1] = slotArray[1];
			this.p1Slot[2] = slotArray[2];
			this.p1Slot[3] = slotArray[3];
		}
		if(player == 1){
			this.p2Slot[0] = slotArray[0];
			this.p2Slot[1] = slotArray[1];
			this.p2Slot[2] = slotArray[2];
			this.p2Slot[3] = slotArray[3];
		}
		if(player == 2){
			this.p3Slot[0] = slotArray[0];
			this.p3Slot[1] = slotArray[1];
			this.p3Slot[2] = slotArray[2];
			this.p3Slot[3] = slotArray[3];
		}
		
		
	}
	
	public static void getSkillInfo(){
		slotOne = Integer.toString(slot1);
		slotTwo = Integer.toString(slot2);
		slotThree = Integer.toString(slot3);
		slotBomb = Integer.toString(bomb);
	}
	
	//selectSkill class for tooltip rendering
	public static float getSkillCooldown(int player, int skillId, int level){
		
		switch(player){
			case 0:
				cooldown = P1SkillHandler.getCooldown(skillId, level);
				break;
			case 1:
				cooldown = P2SkillHandler.getCooldown(skillId, level);
				break;
			case 2:
				cooldown = P3SkillHandler.getCooldown(skillId, level);
				break;
			default:
				cooldown = 0;
				break;
		}
		
		return cooldown;
	}
	
	public static float getSkillDuration(int player, int skillId, int level){
		
		switch(player){
			case 0:
				duration = P1SkillHandler.getDuration(skillId, level);
				break;
			case 1:
				duration = P2SkillHandler.getDuration(skillId, level);
				break;
			case 2:
				duration = P3SkillHandler.getDuration(skillId, level);
				break;
			default:
				duration = 0;
				break;
		}
		
		return duration;
	}
	
	public static String getSkillName(int player, int skillId){
		
		switch(player){
			case 0:
				skillName = P1SkillHandler.getSkillName(skillId);
				break;
			case 1:
				skillName = P2SkillHandler.getSkillName(skillId);
				break;
			case 2:
				skillName = P3SkillHandler.getSkillName(skillId);
				break;
			default:
				skillName = "skill.name not found";
				break;		
		}
		
		return skillName;
	}
	
	public static String[] getSkillDescription(int player, int skillId, int level){
		
		switch (player){
		case 0:
			skillDescription = P1SkillHandler.getSkillDescription(skillId, level);
			break;
		case 1:
			skillDescription = P2SkillHandler.getSkillDescription(skillId, level);
			break;
		case 2:
			skillDescription = P3SkillHandler.getSkillDescription(skillId, level);
			break;
		default:
			skillDescription[0] = "skill.description not found, line 1";
			skillDescription[1] = "skill.description not found, line 2";
			skillDescription[2] = "skill.description not found, line 3";
		
		}
		
		return skillDescription;
	}

	public void assignToSlot(int slot){
		
		
		
	}

	
	
	
	
}
