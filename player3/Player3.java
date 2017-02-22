package com.game.player3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.enums.ID;
import com.game.enums.STATE;
import com.game.pickups.BombPickup;
import com.game.pickups.PowerPickup;
import com.game.player1.BombOne;

public class Player3 extends GameObject{
	Handler handler;
	Game game;
	KeyInput keyInput;
	NewHighScorePlayer3 highScore;
	private Image player, hitbox, fadedPlayer, error;

	public static float playerX, playerY;
	public static float playerScore = 0;
	public static float playerHealth = 3;
	public static float playerBombs = 3;
	public static float power = 0; //PICKUPS NEED
	public static float bombPower = 0;
	public static int currentLevel = 1;
	public static int invulnerableTimer = 900;
	public static boolean invulnerable = false;
	private int startHealth;
	private int playerPower = 1;
	private int shootTimer = 50;
	private int errorMessageTimer = 400;
	private float slow = (4/3);
	private boolean bomb;
	private boolean playermoved = false;
	private static boolean errorMessage = false;
	private int bombTimer = 0;
	private int fadeTimer = invulnerableTimer;


	public Player3(float x, float y, ID Id, float velX, float velY, Handler handler) {
		super(x, y, velX, velY, Id);
		this.handler = handler;
		playerX = x;
		playerY = y;
		playerHealth = MainSettings.playerLives;
		playerBombs=3;
		startHealth = (int) playerHealth;
		try {
			if(CharacterSelect.player==2){
			player=ImageIO.read(new File("bin/used/player3UP.png"));
			fadedPlayer = ImageIO.read(new File("bin/used/player3UPFADED.png"));
			}
			hitbox=ImageIO.read(new File("bin/used/hitbox.png"));
			error = ImageIO.read(new File("bin/used/error.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Rectangle getBounds() {
		if(invulnerable){
		return new Rectangle (-200, -200, 0, 0);
		} else
		return new Rectangle((int) x+5, (int) y+20, 11, 11);
	}
	

	
	public void tick() {
		
		playerScore+=0.3f;
		
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
		if(playerPower==4 && power == 60){
			PowerPickup.allowDrop=false;
		}
		
//////////////////////////////////////////////////////
//					POWER1							//
//////////////////////////////////////////////////////


//////////////////////////////////////////////////////
//					POWER2							//
//////////////////////////////////////////////////////


//////////////////////////////////////////////////////
//					POWER3							//
//////////////////////////////////////////////////////


//////////////////////////////////////////////////////
//					POWER4							//
//////////////////////////////////////////////////////

		
		//BOMB
		if(bombTimer>0){
		bombTimer--;
		}
		if(bombPower >= 99){
			if(playerBombs<= 9){
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
		if (KeyInput.keyEight == true && playerBombs >= 1 && bombTimer <=0){
			bombTimer = 550;
			KeyInput.keyEight = false;
			playerBombs-=1;
			handler.addObject(new BombOne (Player3.playerX+12, Player3.playerY+12,ID.Bomb,0, 0,handler));
		}
		
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
		
		//CLAMP TO SCREEN
		x = Game.clamp((int) x, 200, Game.WIDTH-330);
		y = Game.clamp((int) y, -35, Game.HEIGHT-100);

		//DEATH
		if (playerHealth <= 0){
			playerHealth = 3;
			handler.clearEnemies();
			highScore = new NewHighScorePlayer3();
			Game.gameState = STATE.NewHighScorePlayer2;
		}
		
		
		//INVULNERABILITY
		if(invulnerable){
			if(!playermoved){
			x = Game.WIDTH/2-32;
			y = Game.HEIGHT/2-32;
			playermoved = true;
			}
			invulnerableTimer--;
			
			if(invulnerableTimer ==0){
				playermoved = false;
				invulnerable =false;
				fadeTimer = 450;
			}
		}
	}



	public void render(Graphics2D g2d) {
		//player sprite
		if(!invulnerable){
			g2d.drawImage(player, (int)x, (int)y, 32, 60, null);
			}
			if(invulnerable){
				errorMessageTimer--;
				fadeTimer--;
				if(fadeTimer >= 400 && fadeTimer <= 499){
					g2d.drawImage(fadedPlayer, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 350 && fadeTimer <= 399){
					g2d.drawImage(player, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 300 && fadeTimer <= 349){
					g2d.drawImage(fadedPlayer, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 250 && fadeTimer <= 299){
					g2d.drawImage(player, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 200 && fadeTimer<=249){
					g2d.drawImage(fadedPlayer, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 150 && fadeTimer<=199){
					g2d.drawImage(player, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 100 && fadeTimer<=149){
					g2d.drawImage(fadedPlayer, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 50 && fadeTimer<=99){
					g2d.drawImage(player, (int)x, (int)y, 32,60,null);
				}
				if(fadeTimer >= 1 && fadeTimer<=49){
					g2d.drawImage(fadedPlayer, (int)x, (int)y, 32,60,null);
				}
			}
			
			if(errorMessage && errorMessageTimer>=0){
				
				g2d.drawImage(error, 200, 100, 712, 712, null);
				if(errorMessageTimer==0){
//					errorMessage = false;
					errorMessageTimer = 400;
				}
			}
			
			g2d.setColor(Color.red);
//			g2d.drawRect((int)x, (int)y, 42, 104);
			//hitbox
//			g2d.fillRect((int) x+10, (int) y+43, 11, 11);
			g2d.drawImage(hitbox, (int)x+5, (int)y+20, 22, 22, null);
	}

	public static void loseLife(){
		invulnerableTimer = 900;
		invulnerable = true;
		errorMessage = true;
		playerHealth-=1;
		playerX = Game.WIDTH/2-32;
		playerY = Game.HEIGHT/2-32;
	}



}	

