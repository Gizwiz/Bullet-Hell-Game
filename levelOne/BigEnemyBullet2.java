package com.game.levelOne;


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
import com.game.engine.Path;
import com.game.enums.ID;
import com.game.player1.Player1;
import com.game.player2.Player2;
import com.game.player3.Player3;


//CURVED BULLET RIGHT
public class BigEnemyBullet2 extends GameObject{
	
	private Image bullet;
	private Handler handler;
	private float startX, startY, currentX, currentY;
	private double theta = 90;
	private Path path;

	public BigEnemyBullet2(float x, float y, float velX, float velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.startX = x;
		this.startY = y;
		path = new Path(x, y, (startX), (startY+400), (x+100), (y+200), 1);
//		this.velY = velY;

		try {
			bullet = ImageIO.read(new File("bin/used/basicEnemyBullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public Rectangle getBounds(){
		return new Rectangle((int)x+13,(int)y+13, 8, 7); 
	}
	public void collision(){
		for (int i=0; i<handler.object.size(); i++){
			
			GameObject tempObject = handler.object.get(i);
			//if tempObject is ENEMY OR ENTITY, AKA IS NOT PLAYER
			if (
					tempObject.getId() == ID.Player ||
					tempObject.getId() == ID.Bomb){
				if(getBounds().intersects(tempObject.getBounds())){
					handler.removeObject(this);
				}
			}
			if (tempObject.getId() == ID.Player ){
				if(getBounds().intersects(tempObject.getBounds())){

					if(CharacterSelect.player == 0 && !Player1.invulnerable){
						Player1.loseLife();
					}
					if(CharacterSelect.player == 1 && !Player2.invulnerable){
						Player2.loseLife();
					}
					if(CharacterSelect.player == 2 && !Player3.invulnerable){
						Player3.loseLife();

					}
				}
			}
		}
	}	
	public void tick() {

		if(CharacterSelect.player == 0 && !Player1.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 1 && !Player2.invulnerable){
		collision();
		}
		if(CharacterSelect.player == 2 && !Player3.invulnerable){
		collision();
		}
		
		
//		path.calculatePath(this.getX(), this.getY());
		x = this.getX();
		y = this.getY();
		
		x = path.moveToX(getX());
		y = path.moveToY(getY());
		
//		currentX = this.getX();
//		currentY = this.getY();
//		
//		path.calculatePath(currentX, currentY);
		
//		x=bulletPath.moveToX(currentX);
//		y=bulletPath.moveToY(currentY);
		

//
//		theta = theta + Math.toRadians(1);
//		
//		x = (float) (x+Math.cos(theta) * -5);
//		y = (float) (y+Math.sin(-theta) * 12);

//		y = (float) (y+Math.cos(theta) * 10);
//		y = (float) (y+Math.cos(theta));
//		System.out.println(x);
		
		if(y >= Game.HEIGHT){
			handler.removeObject(this);
		}
		if(x <= 200 || x >= Game.WIDTH-350){
			handler.removeObject(this);
		}
		
 
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(bullet, (int)x, (int)y, 35, 35, null);
		if(MainSettings.showHitBoxes){
		g2d.setColor(Color.red);
			g2d.drawRect((int)x+13,(int)y+13, 8, 7);
		}
	}
	
	

}