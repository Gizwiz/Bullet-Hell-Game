//package com.game.bosses;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//import com.game.displays.CharacterSelect;
//import com.game.engine.Game;
//import com.game.engine.GameObject;
//import com.game.engine.Handler;
//import com.game.enums.ID;
//import com.game.levelOne.BasicEnemyBullet;
//import com.game.levels.LevelOne;
//import com.game.player1.Player1;
//import com.game.player2.Player2;
//import com.game.player3.Player3;
//import com.game.zdump.BasicCurvedBulletLeft;
//import com.game.zdump.BasicCurvedBulletRight;
//
//public class LevelOneBoss extends GameObject{
//	
//	private Handler handler;
//	private BufferedImage bossHPbar;
//	private float bossHealth = 25500.0f;
//	private float bossHP;
//	private int greenValue = 255;
//	private int timer1 = 200;
//	private int timer2 = 100;
//	private int timer3 = 0;
//	private int timer4 = 0;
//	private int shootTimer1 = 15;
//	private int shootTimer2 = 100;
//	private int moveTimer1 = 250;
//	private float vX, vY;
//	public LevelOneBoss(float x, float y, ID id, Handler handler) {
//		super(x, y, id);
//		
//		this.handler = handler;
//		try {
//			bossHPbar = ImageIO.read(new File("bin/used/bossHP.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		velX = 0;
//		velY = 1;
//		vX = (float) (Math.sqrt(y)-22);
//		vY = -2;
//	}
//
//	public Rectangle getBounds(){
//		return new Rectangle((int) x, (int) y, 122, 122);
//	}
//	public void collision(){
//		
//		for (int i=0; i<handler.object.size(); i++){
//			
//			GameObject tempObject = handler.object.get(i);
//			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
//			if (tempObject.getId() == ID.BasicPlayerBullet ||
//				tempObject.getId() == ID.BasicPlayerCurvedBullet ||
//				tempObject.getId() == ID.Bomb ||
//				tempObject.getId() == ID.BombOneSide){
//				if(getBounds().intersects(tempObject.getBounds())){
//					if(CharacterSelect.player == 0){
//						Player1.playerScore+=25;
//					}
//					if(CharacterSelect.player == 1){
//						Player2.playerScore+=25;
//					}
//					if(CharacterSelect.player == 2){
//						Player3.playerScore+=25;
//					}
//					
//					bossHealth -= 1f;
//					if (bossHealth == 0){	
//						if(CharacterSelect.player == 0){
//							Player1.playerScore+=200000;
//						}
//						if(CharacterSelect.player == 1){
//							Player2.playerScore+=200000;
//						}
//						if(CharacterSelect.player == 2){
//							Player3.playerScore+=200000;
//						}
//					
//					handler.removeObject(this);LevelOne.bossDefeat = true;
//					
//				}
//			}
//		}
//			if (tempObject.getId() == ID.Player){
//				if(getBounds().intersects(tempObject.getBounds())){
//					if(CharacterSelect.player == 0){
//						Player1.playerHealth -= 1;
//					}
//					if(CharacterSelect.player == 1){
//						Player2.playerHealth -= 1;
//					}
//					if(CharacterSelect.player == 2){
//						Player3.playerHealth -= 1;
//					}
//				}
//			}
//	}
//	}
//	public void tick() {
//		
//		
//if(shootTimer1 == 0){
//
//	for(int i = 0; i<250; i+=25){	
//	handler.addObject(new BasicCurvedBulletRight(x+i, (float) Math.sqrt(x*i-50), 0, -1, ID.BasicCurvedBulletRight, handler));
//	}
//	for(int i = 0; i<250; i+=25){	
//	handler.addObject(new BasicCurvedBulletLeft(x-i, (float) Math.sqrt(x*i-50), 0, -1, ID.BasicCurvedBulletLeft, handler));
//	}
//	for (int i = 0; i<1000; i+=100){
//		handler.addObject(new BasicEnemyBullet(i, 0, 1, 6, ID.EnemyBullet, handler));
//	}
//		shootTimer1 = 115;
//}
//		
//		bossHP = bossHealth/25500.0f;
//		if (timer1>0){
//		timer1--;
//		}
//		collision();
//		x += velX;
//		y += velY;
//
//		if (timer1== 0 && timer2>0){
//			timer3++;
//			timer2--;
//			velY=-1;
//			velX=1;
//		}
//		if (timer2==0){
//			shootTimer1--;
//			shootTimer2--;
//			moveTimer1--;
//			velY=0; velX=0;
//
//				}
////			if(moveTimer1==0){
////				moveTimer1=250;
////			}
////							
//		if(y <= 0 || y >= Game.HEIGHT-122)
//			velY *= 0;
//		if(x <= 0 || x >= Game.WIDTH-480)
//			velX *= -1;
//		bossHealth = (int) Game.clamp(bossHealth, 0, 25500);
//		greenValue = (int) Game.clamp(greenValue, 0, 255);
//	
//		greenValue =(int) (bossHealth/100.0f);
// 
//}
//
//	@Override
//	public void render(Graphics2D g2d) {
//		
//		//HP BAR
//		g2d.drawImage(bossHPbar, 145,10, 725, 30,null);
//		g2d.setColor(new Color(75, greenValue, 0));
//		g2d.fillRect(155,18, (int)(bossHP*700.0f),10);
//		
//		Font fnt = new Font("Arial", 1, 25);
//		g2d.setFont(fnt);
//		g2d.drawString("moveTimer1: "+moveTimer1,100,100);
////		g2d.drawString("Timer2: "+timer2,100,150);
////		g2d.drawString("Timer3: "+timer3,100,200);
////		g2d.drawString("Timer4: "+timer4,100,250);
////		g2d.drawString("shootTimer: "+shootTimer1,100,300);
//	
//		
//		//BOSS ITSELF
//		g2d.setColor(Color.red);
//		g2d.fillRect((int)x-56, (int)y, 122, 122);
//		
//		//BOSS POSITION BAR
//		g2d.setColor(Color.red);
//		g2d.fillRect(0, 870, 700, 20);
//		g2d.setColor(Color.black);
//		g2d.fillRect((int)x+50, 870, 20, 20);
//		
//	}
//
//}