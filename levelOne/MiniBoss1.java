package com.game.levelOne;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.displays.CharacterSelect;
import com.game.displays.MainSettings;
import com.game.displays.Progress;
import com.game.engine.Game;
import com.game.engine.GameObject;
import com.game.engine.Handler;
import com.game.enums.ID;
import com.game.levels.LevelOneEasy;
import com.game.pickups.ScorePickup;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


/*
Big enemy that requires multiple hits to kill. 3 phases.
 */

public class MiniBoss1 extends GameObject implements Runnable{
	private Thread bossThread;
	private boolean running = false;
	public static float frames = 0;
	
	private Handler handler;
	private int playerHealth; //THIS IS TO CHECK IF PLAYER HAS DIED. IF PLAYER HAS NOT DIED GIVE BONUS SCORE.
	private int shootTimer = 15;
	private int shootTimer2 = 220;
	private int shootTimer3 = 500;
	private int moveTimer1 = 201;
	private int moveTimer2 = 200;
	private int moveTimer3 = 201;
	private int moveTimer4 = 200;
	private int moveTimer5 = 201;
	private int moveTimer6 = 200;
	private int moveTimer7 = 201;
	private int moveTimer8 = 200;
	private int moveTimer9 = 201;
	private int phaseTimer = 700; //At 0 go phase change
	private int deathTimer = 200; //At 0 despawn
	private int timeToDefeat = 99995500; //At 0 go berserk
	private int scoreSpawnTimer = 15;
	private int greenValue = 255;
	private int i = 0;
	private int phase=1;
	private int endScore;
	private float bossHealth = 25500.0f;
	private float bossHP;
	private float startX, startY;
	private float randX, randY, randVelX, randVelY;
	private double theta = 0;
	private int hasPickupPower, hasPickupBomb;
	private boolean hasPickupP = false; //POWER PICKUP
	private boolean hasPickupB  =false; //BOMB PICKUP
	private boolean phaseChange = false;
	private boolean engaged = true;
	private boolean berserk = false; //If timeToDefeat is to 0 go to berserk phase
	private Random r =new Random(); //randX
	private Random t =new Random();	//randY
	private Random f =new Random();	//randVelX
	private Random v =new Random();	//randVelY
	
	private Image bossHPbar,bossPositionBar, bossPosition, phaseShield;
	public MiniBoss1(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		try {
			bossHPbar = ImageIO.read(new File("bin/used/bossHP.png"));
			bossPositionBar = ImageIO.read(new File("bin/used/bossPositionBar.png"));
			bossPosition = ImageIO.read(new File("bin/used/bossPositionIndicator.png"));
			phaseShield = ImageIO.read(new File("bin/used/bossMagicCircle1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		velY = 1;	
		
		start();
	}
	
	public void start(){
		bossThread = new Thread(this);
		bossThread.start();
		running = true;
		
	}
	
	public synchronized void stop(){
		try{
			bossThread.join();
			running = false;
		}
		catch(Exception e){
			
		}
	}
	

	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 90.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0.1;
		long timer = System.currentTimeMillis();
		frames = 0;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime=now;
			while(delta>=1){
				update();
				delta--;
			}
			if(running)
//				render();
			frames++;
			
			if(System.currentTimeMillis()-timer>1000){
				timer+=1000;
//				System.out.println("FPS: "+frames);
				frames=0;
			}
		}
		stop();
		
			}

	
	 
	public Rectangle getBounds(){
		if(phaseChange){
		return new Rectangle((int)x-225, (int)y-180, 500, 500);
		}else
		return new Rectangle((int)x-31, (int)y, 122, 122);
		
	}
	public void collision(){
		
		if(!phaseChange){
		for (int i=0; i<handler.object.size(); i++){	
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (tempObject.getId() == ID.PlayerBullet ||
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){
					if(CharacterSelect.player == 0){
						Player1.playerScore+=5;
					}
					if(CharacterSelect.player == 1){
						Player2.playerScore+=5;
					}
					if(CharacterSelect.player == 2){
						Player3.playerScore+=5;
					}
					
					if (phase == 1){
					bossHealth -= 4;
					}
					
					if(Player1.power <= 19){
					bossHealth -= 6;
					}else{
					bossHealth -=2;	
					}
					
					if (bossHealth == 0 && phase == 3){	
						if(CharacterSelect.player == 0){
							Player1.playerScore+=50000;
						}
						if(CharacterSelect.player == 1){
							Player2.playerScore+=50000;
						}
						if(CharacterSelect.player == 2){
							Player3.playerScore+=50000;
						}
						engaged=false;
						Progress.bossEngaged = false;
					}	
					
					if (bossHealth == 0 && berserk){	
						if(CharacterSelect.player == 0){
							Player1.playerScore+=50000;
						}
						if(CharacterSelect.player == 1){
							Player2.playerScore+=50000;
						}
						if(CharacterSelect.player == 2){
							Player3.playerScore+=50000;
						}
						engaged=false;
						Progress.bossEngaged = false;
					}	
				}
				
			if (tempObject.getId() == ID.Player){
				if(getBounds().intersects(tempObject.getBounds())){
					if(CharacterSelect.player == 0){
						Player1.loseLife();
					}
					if(CharacterSelect.player == 1){
						Player2.loseLife();
					}
					if(CharacterSelect.player == 2){
						Player3.loseLife();

					}
					}
				}
			}
		}
		}	
		if(phaseChange){
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getId() == ID.PlayerBullet ||
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(tempObject);
					}
			}
		}
}

	private void update(){
		//HP TICKER
		bossHP = bossHealth/25500.0f;
				
		//HEALTH BAR GREENVALUE CLAMP TICKER
		bossHealth = (int) Game.clamp(bossHealth, 0, 25500);
		greenValue = (int) Game.clamp(greenValue, 0, 255);
	
		//GREENVALUE CHANGER
		greenValue =(int) (bossHealth/100.0f);
	}
	
	public void tick() {
		
			
//		System.out.println("bossThread running: " + bossThread.isAlive());
		
		//MOVEMENT TICKER
		x += velX;
		y += velY;
		
		//COLLISION TICKER
		//If boss is not changing phase check for collision
		if(!phaseChange && engaged){
			collision();
		}
		
		//DEATH -- SCORE RENDER AND REMOVE BOSS, DESPAWN BULLETS
		if(!engaged){
		deathTimer--;
		scoreSpawnTimer--;
		theta = theta + Math.toRadians(2);
		float a = (float) (x + Math.sin(-theta)*100);
		float b = (float) (y + Math.cos(theta)*100);
		if(scoreSpawnTimer == 0){
		handler.addObject(new ScorePickup(a, b, ID.Pickup, handler));
		scoreSpawnTimer = 20;
		}
		LevelOneEasy.bossEngage = false;
		if(deathTimer == 0){
		handler.removeObject(this);
		}
		if(deathTimer == 199){
		handler.clearBullets();
		handler.clearBullets();
		handler.clearBullets();
		}
		}
		//TIMER TICKERS
		shootTimer--;
		if(moveTimer1>0){
		moveTimer1--;
		}
		
		//BERSERK TICKER
		timeToDefeat--;
		if(timeToDefeat==0){
			phase = 4;
			berserk=true;
			velX = 2;
			shootTimer = 100;
			shootTimer2 = 100;
		}
		//PHASE CHANGERS
		if(bossHealth<=0 && !phaseChange && phase<3){
			handler.clearBullets();
			handler.clearBullets();
			handler.clearBullets();
			phaseChange = true;
			if(phase == 1){
				 moveTimer1 = 400;
				 moveTimer2 = 2000;
				 moveTimer3 = 400;
				 moveTimer4 = 2000;
				 moveTimer5 = 400;
				 moveTimer6 = 2000;
				 moveTimer7 = 400;
				 moveTimer8 = 2000;
				 moveTimer9 = 400;
				}
			if(phase == 2){
				moveTimer1 = 2000;
			}
			phase++;
			
			//RESET ALL MOVE TIMERS

		}
		//RECOVER BOSS HP ON PHASE CHANGE
		if(phaseChange  && engaged){
			bossHealth+=100;
			phaseTimer--;
			if (x < Game.WIDTH/2-150){
				velX = 1;
			}
			if(x > Game.WIDTH/2-150){
				velX = -1;
			}
			if(x == Game.WIDTH/2-150){
				velX = 0;
			}
			if(y < 100){
				velY = 1;
			}
			if(y > 100){
				velY = -1;
			}
			if (y == 100){
				velY = 0;
			}
			if(phaseTimer == 0 && phase<4){
				phaseChange = false;
				phaseTimer = 700;
			}
		}
		
		//SYSTEM.OUT.PRINTS
		
		//MOVING AND SHOOTING
		
/*/////////////////////////////////////////////
				PHASE 1
/////////////////////////////////////////////*/	
		if(!phaseChange && phase == 1 && engaged){
		if(moveTimer1 == 0 && moveTimer2>0){
			shootTimer3--;
			shootTimer2--;
			moveTimer9 = 200;
			moveTimer2--;
			velX = -2;
			
			if (shootTimer2 == 0){
				//i determines number of bullets to spawn
				if (shootTimer2 == 0){
					if (i<25){
					theta = theta + Math.toRadians(40);
					float a = (float) (x + Math.sin(-theta)*300);
					float b = (float) (y + Math.cos(theta)*300);
					float c = (float) (x + Math.sin(-theta)*400);
					float d = (float) (y + Math.cos(theta)*400);
					float e = (float) (x + Math.sin(-theta)*500);
					float f = (float) (y + Math.cos(theta)*500);
					handler.addObject(new MiniBossBullet1(a, b, 1f, 0, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet1(c, d, 1f, 0, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet1(e, f, 1f, 0, ID.EnemyBullet, handler));
					i++;
					shootTimer2 = 2;
					}
					}
			}
		}
		if(moveTimer2 <= 0 && moveTimer3>0){
			shootTimer2--;
			shootTimer3 = 10;
			moveTimer3--;
			velX=0;
			if (shootTimer2 == 0){
				if (i<25){
				theta = theta + Math.toRadians(30);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(-theta)*400);
				float d = (float) (y + Math.cos(theta)*400);
				float e = (float) (x + Math.sin(-theta)*500);
				float f = (float) (y + Math.cos(theta)*500);
				handler.addObject(new MiniBossBullet1(a, b, 1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, 1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(e, f, 1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer2 = 2;
				}
				}
		}
		if(moveTimer3 <= 0 && moveTimer4>0){
			shootTimer2 = 10;
			shootTimer3--;
			i=0;
			moveTimer4--;
			velX=2;
		}
		if(moveTimer4 <= 0 && moveTimer5>0){
			shootTimer2--;
			moveTimer5--;
			shootTimer3 = 10;
			velX=0;
			if (shootTimer2 == 0){
				if (i<25){
				theta = theta + Math.toRadians(40);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(-theta)*400);
				float d = (float) (y + Math.cos(theta)*400);
				float e = (float) (x + Math.sin(-theta)*500);
				float f = (float) (y + Math.cos(theta)*500);
				handler.addObject(new MiniBossBullet1(a, b, -1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, -1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(e, f, -1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer2 = 2;
				}
				}
		}
		if(moveTimer5 <= 0 && moveTimer6>0){
			shootTimer2 = 10;
			shootTimer3--;
			i=0;
			moveTimer6--;
			velX=2;
		}
		if(moveTimer6 <=0 && moveTimer7>0){
			shootTimer2--;
			moveTimer7--;
			shootTimer3 = 10;
			velX=0;
			if (shootTimer2 == 0){
				if (i<30){
				theta = theta + Math.toRadians(40);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(-theta)*400);
				float d = (float) (y + Math.cos(theta)*400);
				float e = (float) (x + Math.sin(-theta)*500);
				float f = (float) (y + Math.cos(theta)*500);
				
				handler.addObject(new MiniBossBullet1(a, b, -1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, -1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(e, f, -1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer2 = 2;
				}
				}
		}
		if(moveTimer7 <= 0 && moveTimer8>0){
			shootTimer2 = 10;
			shootTimer3--;
			i=0;
			moveTimer8--;
			velX=-2;
		}
		if(moveTimer8 <= 0 && moveTimer9>0){
			shootTimer2--;
			shootTimer3 = 10;
			moveTimer1=150;
			moveTimer9--;
			velX=0;
			if (shootTimer2 == 0){
				if (i<40){
				theta = theta + Math.toRadians(20);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(-theta)*400);
				float d = (float) (y + Math.cos(theta)*400);
				float e = (float) (x + Math.sin(-theta)*500);
				float f = (float) (y + Math.cos(theta)*500);;
				handler.addObject(new MiniBossBullet1(a, b, 1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, -1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(e, f, -1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer2 = 2;
				}
				}
		}
		if(moveTimer9 <= 0){
			shootTimer2=200;
			i=0;
			moveTimer2=200;
			moveTimer3=201;
			moveTimer4=200;
			moveTimer5=201;
			moveTimer6=200;
			moveTimer7=201;
			moveTimer8=200;	
		}
		}
		
/*/////////////////////////////////////////////
					PHASE 2
/////////////////////////////////////////////*/		

		
		if(!phaseChange && phase == 2 && engaged){
			shootTimer--;
			shootTimer2--;
			
			if(shootTimer<0){
				shootTimer = 200;
			}
			if(shootTimer2<0){
				shootTimer2 = 50;
			}
			if (i>=45){
				i=0;
			}
			
			if(shootTimer == 0){
				if (i<45){
					
					handler.addObject(new MiniBossBullet2(x+25, y+150, 0f, 1, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet2(x-75, y+150, 0f, 1, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet2(x+25, y+150, 0.3f, 1, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet2(x-75, y+150, -0.3f, 1, ID.EnemyBullet, handler));
					
					handler.addObject(new MiniBossBullet2(x+95, y-150, 0f, 1, ID.EnemyBullet, handler));
					handler.addObject(new MiniBossBullet2(x-155, y-150, 0f, 1, ID.EnemyBullet, handler));
					i++;
					shootTimer = 200;
				}
			}	
			
			if(shootTimer2 == 0){
				theta = theta + Math.toRadians(50);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*200);
				handler.addObject(new MiniBossBullet3(a, b, 0f, 1, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(a+75, b, 0.3f, 1, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(a-75, b, -0.3f, 1, ID.EnemyBullet, handler));
				shootTimer2 = 50;
			}

			moveTimer1--;
			if(moveTimer1 >= 0){
				x += 0.5f;
			}
			if(moveTimer1 <= -1000 && moveTimer1 >= -1400){
				x-=1;
			}
			if(moveTimer1 <= -2400 && moveTimer1 >= -2800){
				x+=1;
			}
			if(moveTimer1 <= -3800 && moveTimer1 >= -4200){
				x-= 0.5f;
			}
			if(moveTimer1 <= -5200 && moveTimer1 >= -5600){
				x+=1;
			}
			if(moveTimer1 <= -6600 && moveTimer1 >= -7000){
				x-=1;
			}
			if(moveTimer1 <= -7400){
				moveTimer1 = 400;
			}
			
		}
		
		
/*/////////////////////////////////////////////
					PHASE 3
/////////////////////////////////////////////*/
		if(!phaseChange && phase == 3 && engaged){
			shootTimer--;
			shootTimer2--;
			shootTimer3--;
			
			if(shootTimer<-1){
				shootTimer = 200;
			}
			if(shootTimer2<0){
				shootTimer2 = 50;
			}
			if(shootTimer3<0){
				shootTimer3 = 500;
			}
			if (i>=100){
				i=0;
			}
			
			if (shootTimer <= 0){
				if (i<100){
				theta = theta + Math.toRadians(15);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(theta)*500);
				float d = (float) (y + Math.cos(-theta)*500);
				handler.addObject(new MiniBossBullet1(a, b, 1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, -1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer = 10;
				}
				}
			if(i>44){
				i=0;
				shootTimer = 700;
			}
			
			if(shootTimer2 == 0){
				handler.addObject(new MiniBossBullet3(x+85, y+85, 1, 0.75f, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(x+125, y+85, 1.1f, 0.7f, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(x+45, y+85, 0.9f, 0.8f, ID.EnemyBullet, handler));
				
				handler.addObject(new MiniBossBullet3(x-85, y+85, -1, 0.95f, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(x-125, y+85, -1.1f, 0.9f, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet3(x-45, y+85, -0.9f, 1, ID.EnemyBullet, handler));

				shootTimer2 = 75;
			}
			
			if(shootTimer3 == 0){
				handler.addObject(new MiniBossHomingBullet(x, y+85, 0, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossHomingBullet(x+200, y+85, 0, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossHomingBullet(x-200, y+85, 0, 0, ID.EnemyBullet, handler));
				shootTimer3 = 500;
			}
			
			
			moveTimer1 --;
			
			if(moveTimer1 <= 0 && moveTimer1 >= -1000){
				x += 0.3f;
			}
			if(moveTimer1 <= -2000  && moveTimer1 >= -3000){
				x -= 0.3f;
			}
			if(moveTimer1 <= -3001){
				moveTimer1 = 2000;
			}
			
		}
		
/*/////////////////////////////////////////////
					BERSERK	
/////////////////////////////////////////////*/
		if(phase == 4 && engaged && berserk){
			shootTimer--;
			shootTimer2--;
			randX = r.nextInt(Game.WIDTH);
			randVelX = f.nextInt(4);
			randVelY = v.nextInt(4);
			if(shootTimer <= 0){
			handler.addObject(new BasicEnemyBullet (randX, 1, randVelY, randVelX,ID.EnemyBullet, handler));
			shootTimer = 15;
			}
			
			if (shootTimer2 <= 0){
				if (i<45){
				theta = theta + Math.toRadians(20);
				float a = (float) (x + Math.sin(-theta)*300);
				float b = (float) (y + Math.cos(theta)*300);
				float c = (float) (x + Math.sin(-theta)*500);
				float d = (float) (y + Math.cos(theta)*500);
				handler.addObject(new MiniBossBullet1(a, b, 1f, 0, ID.EnemyBullet, handler));
				handler.addObject(new MiniBossBullet1(c, d, -1f, 0, ID.EnemyBullet, handler));
				i++;
				shootTimer2 = 2;
				}
				}
			if(i>44){
				i=0;
				shootTimer2 = 700;
			}
		}
		//CLAMPING TICKERS
		if(y <= 0 || y >= Game.HEIGHT-32){
			handler.removeObject(this);
		}
		if (phase == 1 && y>= 100){
			velY = 0;
		}
		if (phase == 2 && y==45){
			velY = 0;
		}
		if (phase == 3 && y==145){
			velY = 0;
		}
		if(x <= 260 || x >= Game.WIDTH-375){
			velX *= -1f;
		}
//		if(x <= 50 || x >= Game.WIDTH-375){
//			velX *= -1;
//		}
	}



	public void render(Graphics2D g2d) {
		
		g2d.setColor(Color.WHITE);
		
		
		Font fnt = new Font("Calisto MT", 1, 15);
		
		if(engaged){
		//HP BAR
		g2d.drawImage(bossHPbar, Game.WIDTH/5+(Game.WIDTH/35),10, Game.WIDTH/2+(Game.WIDTH/60), 30,null);
		g2d.setColor(new Color(150, greenValue, 0));
		g2d.fillRoundRect(Game.WIDTH/5+(Game.WIDTH/27),19, (int)(bossHP*Game.WIDTH/2),9, 12, 12);
		g2d.setColor(Color.white);
		g2d.setFont(fnt);
		if(timeToDefeat>0){
		g2d.drawString("Time: "+timeToDefeat, Game.WIDTH/2-(Game.WIDTH/3-20),55);
		}
		
		//BOSS POSITION BAR
		g2d.drawImage(bossPositionBar, Game.WIDTH/2-Game.WIDTH/3-50, Game.HEIGHT-Game.HEIGHT/35,  Game.WIDTH-Game.WIDTH/3, 10, null);
		g2d.drawImage(bossPosition, (int)x,  Game.HEIGHT-Game.HEIGHT/33, 15, 15, null);
		//BOSS ITSELF
		g2d.setColor(Color.white);
		g2d.fillRect((int)x-31, (int)y, 122, 122);
		
		//PHASE SHIELD
		if(phaseChange  && engaged){
		g2d.drawImage(phaseShield, (int)x-225, (int)y-180, 500, 500, null);
		}
		
		//HITBOX
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
		g2d.drawRect((int)x-31, (int)y, 122, 122);
		}
		}
		
		if(!engaged){
			g2d.setColor(Color.white);
			if(endScore < 50000){
				endScore+=350;
			}
			g2d.drawString("+"+endScore, x-25, y+250);

		}
	}
	
	

}