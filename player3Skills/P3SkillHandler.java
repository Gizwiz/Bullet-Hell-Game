package com.game.player3Skills;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.player2.Player2;
import com.game.player2Skills.ArcaneProjectile;

public class P3SkillHandler extends GameObject {

	Handler handler;
	private int beam = 311;
	private int wrath = 321;
	private int explosion = 331;
	private int skill4 = 341;
	private int fireWind = 351;
	private int skill6 = 361;
	private int skill7 = 371;
	private int skill8 = 381;
	private int skill9 = 391;

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

	public P3SkillHandler() {
		this.handler = handler;
	}

	public P3SkillHandler(int[] skillInfoArray) {

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

		if (skillId == 321) {
			ArcaneProjectile arcaneProjectile = new ArcaneProjectile(Player2.playerX, Player2.playerY, handler, ID.PlayerBullet, 1);
			skillCooldown = arcaneProjectile.getCooldown();
			return arcaneProjectile;
		} else
			return null;

	}

	public double getCooldown() {
		//returns cooldown from useSkill() method
		return skillCooldown;
	}

	public static float getCooldown(int skillId, int level) {

		switch (skillId) {
		case 11:
			break;
		case 12:
			break;
		case 13:
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
			break;
		case 12:
			break;
		case 13:
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
			break;
		case 12:
			break;
		case 13:
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
			break;
		case 12:
			break;
		case 13:
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
