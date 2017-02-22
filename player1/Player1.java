package com.game.player1;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.GameHUD;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.ID;
import com.game.enums.STATE;
import com.game.pickups.BombPickup;
import com.game.pickups.PowerPickup;
import com.game.player1Skills.P1SkillHandler;

public class Player1 extends GameObject{
	Handler handler;
	Game game;
	KeyInput keyInput;
	NewHighScorePlayer1 highScore;

	P1SkillHandler skillHandler;
	P1Skill1 skill1;
	P1Skill2 skill2;
	P1Skill3 skill3;
	P1Bomb bombSkill;
	
	GameObject skillOne, skillTwo, skillThree, skillBomb;
	
	private Image player, wingLeft, wingRight, hitbox, fadedPlayer, error, playerside;
	private Thread playerThread;
	private boolean running = false;
	
	public static float playerX, playerY;
	public static float playerScore = 12000;
	public static float playerHealth = 3;
	public static float playerBombs = 3;
	public static float power = 0; //PICKUPS NEED
	public static float bombPower = 0;
	public static int currentLevel = 1;
	public static int invulnerableTimer = 650;
	
	public static boolean invulnerable = false;
	private int startHealth;
	
	private int playerPower = 1;
	private int shootTimer = 50, sideShootTimer=0, sideShootTimer2 = 0;
	private double errorMessageTimer = 400;
	
	private float slow = (4/3);
	private float opacity = 0.1f;
	private float wingOpacity = 0;
	
	private boolean bomb;
	private boolean playermoved = false;
	private static boolean errorMessage = false;
	
	private int bombTimer = 0;
	private int fadeTimer = invulnerableTimer;
	private int offset = 100;
	private int frames = 0;
	
	public static double skill1Cooldown, skill2Cooldown, skill3Cooldown;
	public static double skill1CooldownLeft, skill2CooldownLeft, skill3CooldownLeft;
	public static long skill1UseTime, skill2UseTime, skill3UseTime;
	
	public static double bombCooldown;
	public static double bombCooldownLeft;
	public static long bombUseTime;

	public Player1(float x, float y, ID Id, float velX, float velY, Handler handler) {
		super(x, y, velX, velY, Id);
		this.handler = handler;
		skill1 = new P1Skill1(handler);
		skill2 = new P1Skill2(handler);
		skill3 = new P1Skill3(handler);
		bombSkill = new P1Bomb(handler);
		skillHandler = new P1SkillHandler(handler);
		playerX = x;
		playerY = y;
		playerHealth = MainSettings.playerLives;
		playerBombs=3;
		startHealth = (int) playerHealth;
		errorMessage = false; //when restarted will not display lose life error
		
		try {
			if(CharacterSelect.player==0){
			player=ImageIO.read(new File("bin/used/player1UP.png"));
			wingLeft = ImageIO.read(new File("bin/used/wing1Left.png"));
			wingRight = ImageIO.read(new File("bin/used/wing1Right.png"));
			fadedPlayer = ImageIO.read(new File("bin/used/player1UPFADED.png"));
			playerside = ImageIO.read(new File("bin/used/Player1Side.png"));
			}
			hitbox=ImageIO.read(new File("bin/used/hitbox.png"));
			error = ImageIO.read(new File("bin/used/error.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public Rectangle getBounds() {
		//UPPER BOUND IS FOR NORMAL HIT BOX.
		//LOWER BOUND IS FOR INVULNERABILITY. HITBOX IS OUT OF SCREEN AREA == GODMODE.

//		if(invulnerable){
//		return new Rectangle(-200, -200, 0, 0);	
//		}else
		return new Rectangle((int)x+13, (int)y+30, 10, 9);
		
//		return new Rectangle(-200, -200, 0, 0);
	}

	public void tick() {
		
		playerX = x;
		playerY = y;
		playerScore+=0.3f;


		if(KeyInput.keySix){
		sideShootTimer--;
		if(sideShootTimer<0){
			sideShootTimer=150;
		}
		}
		if(KeyInput.keySix){
		sideShootTimer2--;
		if(sideShootTimer2<0){
			sideShootTimer2=150;
		}
		}
		
		if(shootTimer>0){
		shootTimer--;
		
		}
		//POWER LEVELS AND PICKUPS
		if(power>=0 && power<=9){
			playerPower = 1;
			PowerPickup.allowDrop=true;
		}
		if (power>10 && power<=19){
			playerPower = 2;
			PowerPickup.allowDrop=true;
		}
		if (power>=20 && power<=39){
			playerPower = 3;
			PowerPickup.allowDrop=true;
		}
		if (power>40 && power<=59){
			playerPower = 4;
			PowerPickup.allowDrop=true;
		}
		if(playerPower==4 && power >= 60){
			power = 60;
			PowerPickup.allowDrop=false;
		}
//////////////////////////////////////////////////////
//					POWER1							//
//////////////////////////////////////////////////////
		if (playerPower == 1){
		if (shootTimer == 0 &&KeyInput.keySix == true){
			if(KeyInput.keySeven==true){
			handler.addObject(new BasicPlayerBullet(x+7,y, 0, -12, ID.PlayerBullet, handler));
			}
			if (KeyInput.keySeven==false){
			handler.addObject(new BasicPlayerBullet(x+7,y, 0, -12, ID.PlayerBullet, handler));
			}
		shootTimer = 12;
		}
		}
		
//////////////////////////////////////////////////////
//					POWER2							//
//////////////////////////////////////////////////////		
		if (playerPower == 2){
		if (shootTimer == 0 && KeyInput.keySix == true){
			if(KeyInput.keySeven==true){
				handler.addObject(new BasicPlayerBullet(x-5,y, 0, -9, ID.PlayerBullet, handler));
				handler.addObject(new BasicPlayerBullet(x+15,y, 0, -9, ID.PlayerBullet, handler));	
			}
			if (KeyInput.keySeven==false){
				handler.addObject(new BasicPlayerBullet(x-30,y, 0, -9, ID.PlayerBullet, handler));
				handler.addObject(new BasicPlayerBullet(x+40,y, 0, -9, ID.PlayerBullet, handler));	
			}
			shootTimer = 12;
		}
		}
//////////////////////////////////////////////////////
//					POWER3							//
//////////////////////////////////////////////////////
		if (playerPower == 3){
			//NORMAL MODE
			if(!KeyInput.keySeven){
				offset ++;
				if(offset>145){
					offset=145;
				}
			if(KeyInput.keySix){
			if(sideShootTimer==150){
				//RIGHT
			handler.addObject(new FrostBeam(Game.clamp((int) x+offset+5, 0, Game.WIDTH-330), y+offset, ID.PlayerBullet, handler));
//			sideShootTimer = 150;
			}
			if(sideShootTimer2<=0){
				//LEFT
			handler.addObject(new FrostBeam(Game.clamp((int) x-offset+4, 0, Game.WIDTH-330), y+offset, ID.PlayerBullet, handler));
//			sideShootTimer2=150;
			}
			}
			if(KeyInput.keySix && shootTimer == 0){
				handler.addObject(new BasicPlayerBullet(x-30,y, 0, -9, ID.PlayerBullet, handler));
				handler.addObject(new BasicPlayerBullet(x+40,y, 0, -9, ID.PlayerBullet, handler));		
			shootTimer = 9;
			}
			}
	
			
			//PHASE MODE
			if(KeyInput.keySeven==true){
				offset -=3;
				if(offset<45){
					offset=45;
				}
				if(KeyInput.keySix){
				if(sideShootTimer==50){
					//RIGHT
					handler.addObject(new FrostBeam(x+offset+11, y+offset, ID.PlayerBullet, handler));
//					sideShootTimer=150;
					}
				if(sideShootTimer2<=0){
					//LEFT
					handler.addObject(new FrostBeam(x-offset+4, y+offset, ID.PlayerBullet, handler));
					sideShootTimer2 = 100;
					}
				}
				if(KeyInput.keySix && shootTimer == 0){
					handler.addObject(new BasicPlayerBullet(x+7,y, 0, -9, ID.PlayerBullet, handler));
					handler.addObject(new BasicPlayerBullet(x-1,y, 0, -9, ID.PlayerBullet, handler));
					handler.addObject(new BasicPlayerBullet(x+7,y, 0, -9, ID.PlayerBullet, handler));
				shootTimer = 9;
				}
			}
			
				

		}

		
		
//////////////////////////////////////////////////////
//					POWER4							//
//////////////////////////////////////////////////////		
		if (playerPower == 4){
			//NORMAL MODE
			if(!KeyInput.keySeven){
				offset ++;
				if(offset>145){
					offset=145;
				}
			if(KeyInput.keySix){
			if(sideShootTimer==50){
				//RIGHT
			handler.addObject(new FrostBeam(Game.clamp((int) x+offset+5, 0, Game.WIDTH-330), y+offset, ID.PlayerBullet, handler));
				//LEFT
			handler.addObject(new FrostBeam(Game.clamp((int) x-offset+65, 0, Game.WIDTH-330), y+offset-50, ID.PlayerBullet, handler));
			
//			sideShootTimer = 150;
			}
			if(sideShootTimer2<=0){
				//LEFT
			handler.addObject(new FrostBeam(Game.clamp((int) x-offset+4, 0, Game.WIDTH-330), y+offset, ID.PlayerBullet, handler));
				//RIGHT
			handler.addObject(new FrostBeam(Game.clamp((int) x+offset-54, 0, Game.WIDTH-330), y+offset-50, ID.PlayerBullet, handler));
			sideShootTimer2=100;
			}
			}
			if(KeyInput.keySix && shootTimer == 0){
				handler.addObject(new BasicPlayerBullet(x+7,y, 0, -7, ID.PlayerBullet, handler));
				handler.addObject(new BasicPlayerBullet(x-1,y, -1, -7, ID.PlayerBullet, handler));
				handler.addObject(new BasicPlayerBullet(x+7,y, 1, -7, ID.PlayerBullet, handler));
			shootTimer = 9;
			}
			}
	
			
			//PHASE MODE
			if(KeyInput.keySeven==true){
				offset -=3;
				if(offset<45){
					offset=45;
				}
				if(KeyInput.keySix){
				if(sideShootTimer==50){
					//RIGHT
					handler.addObject(new FrostBeam(x+offset+11, y+offset, ID.PlayerBullet, handler));
					//LEFT
					handler.addObject(new FrostBeam(x+offset-58, y+offset-50, ID.PlayerBullet, handler));
					
//					sideShootTimer=150;
					}
				if(sideShootTimer2<=0){
					//LEFT
					handler.addObject(new FrostBeam(x-offset+4, y+offset, ID.PlayerBullet, handler));
					//RIGHT
					handler.addObject(new FrostBeam(x+offset-24, y+offset-50, ID.PlayerBullet, handler));
					sideShootTimer2 = 100;
					}
				}
				if(KeyInput.keySix && shootTimer == 0){
					handler.addObject(new BasicPlayerBullet(x+7,y, 0, -7, ID.PlayerBullet, handler));
					handler.addObject(new BasicPlayerBullet(x-1,y, 0, -7, ID.PlayerBullet, handler));
					handler.addObject(new BasicPlayerBullet(x+7,y, 0, -7, ID.PlayerBullet, handler));
				shootTimer = 9;
				}
			}
			
				
		}

		//BOMB
		if(bombTimer>0){
		bombTimer--;
		}
		
		if(bombPower >= 99){
			if(playerBombs<9){
			playerBombs +=1;
			}
			bombPower = 0;			
		}
		
		if(playerBombs==9){
			BombPickup.allowDrop=false;
		}
		
		if(playerBombs<9){
			BombPickup.allowDrop=true;
		}
		
//		if (KeyInput.keyEight == true && playerBombs >= 1 && bombTimer <=0){
//			bombTimer = 550;
//			KeyInput.keyEight = false;
//			playerBombs-=1;
//			handler.addObject(new BombOne (x, y,ID.Bomb,0, 0,handler));
//		}
		
		//UP
		if(KeyInput.keyOne){
			velY = -4f;
		}
		//DOWN
		if(KeyInput.keyTwo){
			velY = 4f;
		}
		//LEFT
		if(KeyInput.keyThree){
			velX = -4f;
		}
		//RIGHT
		if(KeyInput.keyFour){
			velX = 4f;
		}
		
		//IF LEFT AND RIGHT PRESSED
		if(KeyInput.keyDownVert[0] && !KeyInput.keyDownVert[1] && KeyInput.keyDownVert[2] ){
			/*pressing right will make keyDown[1] = true, rendering this invalid and making player move right
			pressing left will make keyDown[1] = false, fulfilling the demands and moving player left,
			regardless of right being pressed. 
			*/
			velX = -4f;
		}
		
		//IF UP AND DOWN PRESSED
		if(KeyInput.keyDownHor[0] && !KeyInput.keyDownHor[1] && KeyInput.keyDownHor[2] ){
			/*pressing up will make keyDown[1] = true, rendering this invalid and making player move up
			pressing down will make keyDown[1] = false, fulfilling the demands and moving player down,
			regardless of up being pressed. 
			*/
			velY = -4f;
		}

		
		//STOP MOVEMENT IF NOTHING IS PRESSED
		if(!KeyInput.keyOne && !KeyInput.keyTwo){ velY=0; }
		if(!KeyInput.keyThree && !KeyInput.keyFour){ velX=0; }
		

		//MOVEMENT SPEED MODIFIERS
		if (!KeyInput.keySeven && !KeyInput.keyNine){
		x += velX;
		y += velY;
		}
		//SLOW
		if(KeyInput.keySeven){
		x += velX/3;
		y += velY/3;
		}
		//FAST
		if(KeyInput.keyNine){
		x += velX*2;
		y += velY*2;
		}
		
		//skill spawning
		
		if(KeyInput.keySkill1 && skill1CooldownLeft <= 0){
			KeyInput.keySkill1 = !KeyInput.keySkill1;
	
			//GameObject skillOne from skill1 class getSkill method
			skillOne = skill1.getSkill();
			handler.addObject(skillOne);
			
			//getter P1Skill1 class for cooldown
			skill1Cooldown = skill1.getCooldown();
			skill1UseTime = System.currentTimeMillis();
			GameHUD.skill1UseTime = System.currentTimeMillis();
			skill1CooldownLeft = skill1Cooldown;
			
		}
		
		if(KeyInput.keySkill2 && skill2CooldownLeft <= 0){
			KeyInput.keySkill2 = !KeyInput.keySkill2;
			
			//GameObject skillOne from skill2 class getSkill method
			skillTwo = skill2.getSkill();
			handler.addObject(skillTwo);
			
			//getter P1Skill2 class for cooldown
			skill2Cooldown = skill2.getCooldown();
			skill2UseTime = System.currentTimeMillis();
			GameHUD.skill2UseTime = System.currentTimeMillis();
			skill2CooldownLeft = skill2Cooldown;
		}
		
		if(KeyInput.keySkill3 && skill3CooldownLeft == 0){
			KeyInput.keySkill3 = !KeyInput.keySkill3;
			
			//GameObject skillOne from skill3 class getSkill method
			skillThree = skill3.getSkill();
			handler.addObject(skillThree);
			
			//getter P1Skill3 class for cooldown
			skill3Cooldown = skill3.getCooldown();
			skill3UseTime = System.currentTimeMillis();
			GameHUD.skill3UseTime = System.currentTimeMillis();
			skill3CooldownLeft = skill3Cooldown;
			
		}
		
		//BOMB
			if(keyInput.keyEight && bombCooldownLeft <= 0 && playerBombs > 0){
			KeyInput.keyEight = !KeyInput.keyEight;	
			playerBombs--;
			//GameObject skillBomb from bomb class getBomb method
			skillBomb = bombSkill.getBomb();
			handler.addObject(skillBomb);
			
			//getter p1Bomb class for cooldown
			bombCooldown = bombSkill.getCooldown();
			bombUseTime = System.currentTimeMillis();
			GameHUD.bombUseTime = System.currentTimeMillis();
			bombCooldownLeft = bombCooldown;
			
			}
			
		//Skill cooldown calulation

		if(skill1CooldownLeft > 0){
			skill1CooldownLeft = skill1Cooldown - (System.currentTimeMillis() - skill1UseTime);
		}
		if(skill2CooldownLeft > 0){
			skill2CooldownLeft = skill2Cooldown - (System.currentTimeMillis() - skill2UseTime);
		}
		if(skill3CooldownLeft > 0){
			skill3CooldownLeft = skill3Cooldown - (System.currentTimeMillis() - skill3UseTime);
		}
		
		//bomb cooldown calculation
		if(bombCooldownLeft > 0){
			bombCooldownLeft = bombCooldown - (System.currentTimeMillis() - bombUseTime);
		}

		
		//CLAMP TO SCREEN
		x = Game.clamp((int) x, 200, Game.WIDTH-330);
		y = Game.clamp((int) y, -35, Game.HEIGHT-100);

		//DEATH
		if (playerHealth <= 0){
			playerHealth = 3;
			handler.clearEnemies();
			highScore = new NewHighScorePlayer1();
			Game.gameState = STATE.NewHighScorePlayer1;
			
		}

		//INVULNERABILITY
		if(invulnerable){
			invulnerableTimer--;
			if(!playermoved){
			x = Game.WIDTH/2-32;
			y = Game.HEIGHT/2-32;
			playermoved = true;
			}
		
			
			if(invulnerableTimer <=0){
				playermoved = false;
				invulnerable =false;
				fadeTimer = 450;
		}
	}
}

	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}

	public void render(Graphics2D g2d) {
		
		//player sprite
		g2d.drawImage(player, (int)x, (int)y, 36, 65, null);
		
		if(KeyInput.keySeven){
			wingOpacity+=0.03f;
			g2d.setComposite(makeComposite(wingOpacity));
			g2d.drawImage(wingLeft, (int)x-63, (int)y-22, 75, 85, null);
			g2d.drawImage(wingRight, (int)x+24, (int)y-22, 75, 85, null);
			g2d.setComposite(makeComposite(1));
			if(wingOpacity > 0.9f){
				wingOpacity= 0.9f;
			}
		}
		if(!KeyInput.keySeven && wingOpacity >= 0.001f){
			wingOpacity-=0.005f;
			g2d.setComposite(makeComposite(wingOpacity));
			g2d.drawImage(wingLeft, (int)x-63, (int)y-22, 75, 85, null);
			g2d.drawImage(wingRight, (int)x+24, (int)y-22, 75, 85, null);
			g2d.setComposite(makeComposite(1));
			if(wingOpacity<=0.1f){
			wingOpacity= 0.006f;
			}
		}
		
		if(invulnerable){
			if(errorMessage){
				g2d.drawImage(error, 200, 100, 712, 712, null);
				errorMessageTimer-=0.1;
				if(errorMessageTimer<=0){
					errorMessage = false;
					errorMessageTimer = 400;
				}
			}
			//set player opacity for invulnerability
			opacity+=0.001f;
			if(opacity>=0.95f){
				opacity = 0.01f;
			}
			g2d.setComposite(makeComposite(opacity));
			g2d.drawImage(player, (int)x, (int)y, 36, 65, null);
			g2d.setComposite(makeComposite(1f));
			
		}
		if(playerPower == 3){
			g2d.drawImage(playerside, (int)Game.clamp((int) x-offset, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset, -35, Game.HEIGHT-55), 35, 35, null);
			g2d.drawImage(playerside, (int)Game.clamp((int) x+offset, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset, -35, Game.HEIGHT-55), 35, 35, null);
		}
		if(playerPower == 4){
			g2d.drawImage(playerside, (int)Game.clamp((int) x-offset+60, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset-60, -35, Game.HEIGHT-55), 35, 35, null);
			g2d.drawImage(playerside, (int)Game.clamp((int) x+offset, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset, -35, Game.HEIGHT-55), 35, 35, null);
			g2d.drawImage(playerside, (int)Game.clamp((int) x-offset, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset, -35, Game.HEIGHT-55), 35, 35, null);
			g2d.drawImage(playerside, (int)Game.clamp((int) x+offset-60, 0, Game.WIDTH-330), (int) Game.clamp((int) y+offset-60, -35, Game.HEIGHT-55), 35, 35, null);
			
		}
		


		//hitbox
		g2d.drawImage(hitbox, (int)x+7, (int)y+24, 22, 22, null);
//		g2d.setColor(Color.WHITE);
//		g2d.drawRect((int)x+13, (int)y+30, 10, 9);
	}

	public static void loseLife(){
		invulnerableTimer = 650;
		invulnerable = true;
		errorMessage = true;
		playerHealth-=1;
		playerX = Game.WIDTH/2-32;
		playerY = Game.HEIGHT/2-32;
	}



}	

