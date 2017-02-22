package com.game.player1Skills;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player1.Player1;

public class P1SkillHandler extends GameObject {

	Handler handler;
	private int shield = 111;
	private int icicle = 121;
	private int frostball = 131;
	private int skill4 = 141;
	private int skill5 = 151;
	private int skill6 = 161;
	private int skill7 = 171;
	private int skill8 = 181;
	private int skill9 = 191;

	private static int[] unlockedSkillsL1 = new int[9];
	private static int[] unlockedSkillsL2 = new int[9];
	private static int[] unlockedSkillsL3 = new int[9];

	private int j = 0;

	private static boolean isTrue = false;

	private String skillName;
	private double skillCooldown;

	private static float cooldownForHandler;
	private static float durationForHandler;
	private static String skillNameForHandler;
	private static String[] skillDescriptionForHandler = new String[3];

	public P1SkillHandler(Handler handler) {
		this.handler = handler;
	}

	//Used by continueMenu
	public P1SkillHandler(int[] skillInfoArray) {

		for (int i = 0; i < unlockedSkillsL1.length; i++) {
			this.unlockedSkillsL1[i] = skillInfoArray[i * 3];
		}

		for (int i = 0; i < unlockedSkillsL2.length; i++) {
			this.unlockedSkillsL2[i] = skillInfoArray[(i * 3) + 1];
		}
		for (int i = 0; i < unlockedSkillsL3.length; i++) {
			this.unlockedSkillsL3[i] = skillInfoArray[(i * 3) + 2];
		}

	}

	public GameObject useSkill(int skillId) {

		//SKILL
		if (skillId == 111) {
			//Creates skill object
			SkillShield skillShield = new SkillShield(0, 0, handler, ID.Bomb, 1);
			//gets cooldown from skill's class
			skillCooldown = skillShield.getCooldown();
			System.out.println("P1SkillHandler has created an object of the skill " + skillId);
			return skillShield;

		}
		if (skillId == 112) {
			//Creates skill object
			SkillShield skillShield = new SkillShield(0, 0, handler, ID.Bomb, 2);
			//gets cooldown from skill's class
			skillCooldown = skillShield.getCooldown();
			System.out.println("P1SkillHandler has created an object of the skill " + skillId);
			return skillShield;

		}
		if (skillId == 113) {
			//Creates skill object
			SkillShield skillShield = new SkillShield(0, 0, handler, ID.Bomb, 3);
			//gets cooldown from skill's class
			skillCooldown = skillShield.getCooldown();
			System.out.println("P1SkillHandler has created an object of the skill " + skillId);
			return skillShield;

		}

		//ICE NAILS
		if (skillId == 121) {
			IceNails iceNails = new IceNails(Player1.playerX + 5, Player1.playerY - 75, handler, ID.PlayerBullet, 1);
			skillCooldown = iceNails.getCooldown();
			return iceNails;
		}
		if (skillId == 122) {
			IceNails iceNails = new IceNails(Player1.playerX + 5, Player1.playerY - 75, handler, ID.PlayerBullet, 2);
			skillCooldown = iceNails.getCooldown();
			return iceNails;
		}
		if (skillId == 123) {
			IceNails iceNails = new IceNails(Player1.playerX + 5, Player1.playerY - 75, handler, ID.PlayerBullet, 3);
			skillCooldown = iceNails.getCooldown();
			return iceNails;
		}
		if (skillId == 131) {
			Frostball frostball = new Frostball(Player1.playerX + 5, Player1.playerY, handler, ID.PlayerBullet, 1);
			skillCooldown = frostball.getCooldown();
			return frostball;
		}
		//		if(skillId == 141){
		//			
		//		}
		//		if(skillId == 151){
		//			
		//		}
		//		if(skillId == 161){
		//			
		//		}
		//		if(skillId == 171){
		//			
		//		}
		//		if(skillId == 181){
		//			
		//		}
		//		if(skillId == 191){
		//			
		//		}
		else
			return null;
	}

	public double getCooldown() {
		//returns cooldown from useSkill() method
		return skillCooldown;
	}

	public static float getCooldown(int skillId, int level) {

		switch (skillId) {
			case 11:
				cooldownForHandler = SkillShield.getCooldown(level);
				break;
			case 12:
				cooldownForHandler = IceNails.getCooldown(level);
				break;
			case 13:
				cooldownForHandler = Frostball.getCooldown(level);
				break;
			case 14:
				break;
			case 15:
				break;
			case 16:
				break;
			case 17:
				break;
			case 18:
				break;
			case 19:
				break;
			default: 
				cooldownForHandler = 0;
				break;
		}

		return cooldownForHandler;
	}

	public static float getDuration(int skillId, int level) {

		switch (skillId) {
		case 11:
			durationForHandler = SkillShield.getDuration(level);
			break;
		case 12:
			durationForHandler = IceNails.getDuration(level);
			break;
		case 13:
			durationForHandler = Frostball.getDuration(level);
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		default:
			durationForHandler = 0;
			break;
		}

		return durationForHandler;
	}

	public static String getSkillName(int skillId) {

		switch (skillId) {
		case 11:
			skillNameForHandler = SkillShield.getSkillName();
			break;
		case 12:
			skillNameForHandler = IceNails.getSkillName();
			break;
		case 13:
			skillNameForHandler = Frostball.getSkillName();
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		default:
			skillNameForHandler = "skill.name not found";
			break;
		}

		return skillNameForHandler;

	}

	public static String[] getSkillDescription(int skillId, int level) {

		switch (skillId) {
		case 11:
			skillDescriptionForHandler = SkillShield.getSkillDescription();
			break;
		case 12:
			skillDescriptionForHandler = IceNails.getSkillDescription(level);
			break;
		case 13:
			skillDescriptionForHandler = Frostball.getSkillDescription(level);
			break;
		case 14:
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		default:
			skillDescriptionForHandler[0] = "skill.description not found: line 1";
			skillDescriptionForHandler[1] = "skill.description not found: line 2";
			skillDescriptionForHandler[2] = "skill.description not found: line 3";
			skillDescriptionForHandler[3] = "skill.description not found: line 4";
			break;
		}

		return skillDescriptionForHandler;

	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(0, 0, 0, 0);
	}

	public static boolean getUnlockStatus(int level, int skill) {
		if (level == 1) {
			if (unlockedSkillsL1[skill] == 1) {
				
				isTrue = true;
			} else {
				isTrue = false;
			}
		}

		else if (level == 2) {
			if (unlockedSkillsL2[skill] == 1) {
				isTrue = true;
			} else {
				isTrue = false;
			}
		}

		else if (level == 3) {
			if (unlockedSkillsL3[skill] == 1) {
				isTrue = true;
			} else {
				isTrue = false;
			}
		}

		if (isTrue == true) {

			return true;

		} else {
			return false;
		}

	}

}
