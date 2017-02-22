package com.game.displays;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.Handler;
import com.game.engine.KeyInput;
import com.game.engine.MouseInput;
import com.game.enums.STATE;

public class IntroDisplay extends KeyInput{

//	private Button button1, button2, button3;
	
	private Image titleAfter, titleRebirth, background, underline;
	private Image[] text = new Image[3];
	
	private int selectedButton = 0;
	private double pos;
	private int direction = 0; //0=up, 1=down
	private int underlineLength;
	private float alpha;


	
	public IntroDisplay(Handler handler){
		super(handler);
		
		alpha = 0.0f;
		
		try {
			
			titleAfter = ImageIO.read(new File("bin/used/title-after.png"));
			titleRebirth = ImageIO.read(new File("bin/used/title-rebirth.png"));
			background = ImageIO.read(new File("bin/used/mainMenuBack.jpg"));
			underline = ImageIO.read(new File("bin/used/underline2.png"));
			
			text[0] =  ImageIO.read(new File("bin/used/continueGame.png"));
			text[1] =  ImageIO.read(new File("bin/used/newGame.png"));
			text[2] =  ImageIO.read(new File("bin/used/quitGame.png"));
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	private String getResourceAsStream(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private AlphaComposite makeComposite(float alpha){
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	
	
	public void tick(){
		if(alpha<0.993f){
		alpha+=0.007f;
		}
		
		if(keySix){
			alpha=1.0f;
		}
		
		if(keyOne && selectedButton>0){
			keyOne = !keyOne;
			selectedButton--;
			underlineLength=0;
		}
		if(keyTwo && selectedButton<2){
			keyTwo = !keyTwo;
			selectedButton++;
			underlineLength=0;
		}
		
		if(keyOne && selectedButton == 0){
			keyOne = !keyOne;
			selectedButton = 2;
			underlineLength = 0;
		}
		if(keyTwo && selectedButton == 2){
			keyOne = !keyOne;
			selectedButton = 0;
			underlineLength = 0;
		}
		
		//UNDERLINE LENGTH
		if(underlineLength<475){
			underlineLength+=10;
		}
		
		
		if(MainMenu.loaded == true){
			if(selectedButton == 0 && keySix){
				keySix = !keySix;
				alpha = 0.0f;
				Game.gameState = STATE.ContinueMenu;
			}
			
			if(selectedButton == 1 && keySix){
				keySix = !keySix;
				Game.gameState = STATE.NewGame;
			}
			
			if(selectedButton == 2 && keySix){
				keySix = !keySix;
				System.exit(1);
				
			}
		}
		
		if(keyEscape){
			System.exit(1);
		}
		
		
//		pos--;
		//0=up, 1=down
		//0=pos--, 1=pos++
		
		if(pos<2 && direction==1){
			pos+=0.08;
			if(pos>1.92){
				direction=0;
				pos-=0.3;
			}
		}
		
		if(pos>-2 && direction==0){
			pos-=0.08;
			if(pos<=-1.92){
				direction=1;
				pos+=0.3;
			}
		}
		
		if(MouseInput.mouseClicked && MouseInput.getX()>Game.WIDTH/2-Game.WIDTH/7 && MouseInput.getX()<(Game.WIDTH/2-Game.WIDTH/7+underlineLength)) {
			if(MouseInput.getY()>Game.HEIGHT/2+1*100-100 && MouseInput.getY()<((Game.HEIGHT/2+1*100-100)+100)){
			MouseInput.mouseClicked = false;
			System.out.println("||"+(Game.HEIGHT/2+1*100-100)+"||"+(((Game.HEIGHT/2+1*100-100)+100))+"|||"+MouseInput.getY());
			System.out.println("mouse click register on ContinueButton");
			
			if(selectedButton != 0){
				selectedButton = 0;
			} else{
				Game.gameState = STATE.ContinueMenu;
			}
			
			}
		}
		
		if(MouseInput.mouseClicked && MouseInput.getX()>Game.WIDTH/2-Game.WIDTH/7 && MouseInput.getX()<(Game.WIDTH/2-Game.WIDTH/7+underlineLength)) {
			if(MouseInput.getY()>Game.HEIGHT/2+2*100-100 && MouseInput.getY()<((Game.HEIGHT/2+2*100-100)+100)){
			MouseInput.mouseClicked = false;
			System.out.println("||"+(Game.HEIGHT/2+2*100-100)+"||"+(((Game.HEIGHT/2+2*100-100)+100))+"|||"+MouseInput.getY());
			System.out.println("mouse click register on NewGameButton");
				
			if(selectedButton != 1){
				selectedButton = 1;
			}else{
				Game.gameState = STATE.NewGame;
			}
			
			}
		}
		
		if(MouseInput.mouseClicked && MouseInput.getX()>Game.WIDTH/2-Game.WIDTH/7 && MouseInput.getX()<(Game.WIDTH/2-Game.WIDTH/7+underlineLength)) {
			if(MouseInput.getY()>Game.HEIGHT/2+3*100-100 && MouseInput.getY()<((Game.HEIGHT/2+3*100-100)+100)){
			MouseInput.mouseClicked = false;
			System.out.println("||"+(Game.HEIGHT/2+3*100-100)+"||"+(((Game.HEIGHT/2+3*100-100)+100))+"|||"+MouseInput.getY());
			System.out.println("mouse click register on Q");
			if(selectedButton !=2){
				selectedButton = 2;
			}else{
			System.exit(1);
			}
			
			}
			

		}
		
	}
	
	public void render(Graphics2D g2d){
		
		
		
		g2d.drawImage(background, 0, 0, Game.WIDTH, Game.HEIGHT, null);
		
		g2d.setComposite(makeComposite(alpha));
		g2d.drawImage(titleAfter, Game.WIDTH/5,  Game.HEIGHT/100-(Game.HEIGHT/20), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/25), null);
		g2d.drawImage(titleRebirth, Game.WIDTH/2-(Game.WIDTH/10), Game.HEIGHT/100+(Game.HEIGHT/10), Game.WIDTH/3, Game.HEIGHT/3-(Game.HEIGHT/20), null);

		g2d.setComposite(makeComposite(alpha));
		for(int i=0; i<text.length; i++){
			if(i!=selectedButton){
				g2d.drawImage(text[i], Game.WIDTH/2-Game.WIDTH/5-50, Game.HEIGHT/2+i*100-100, 800, 375, null);
			}
			else{
				g2d.drawImage(text[i], Game.WIDTH/2-Game.WIDTH/5-50, (int)((int)Game.HEIGHT/2+i*100-100+pos), 800, 375, null);
			}
//		g2d.drawImage(text[1], Game.WIDTH/2-Game.WIDTH/5-50, Game.HEIGHT/2+1*100-100, 800, 375, null);
//		g2d.drawImage(text[2], Game.WIDTH/2-Game.WIDTH/5-50, Game.HEIGHT/2+2*100-100, 800, 375, null);
		}

		
		
		if(selectedButton==0){
			g2d.drawImage(underline, Game.WIDTH/2-Game.WIDTH/7, Game.HEIGHT/2+1*100-10, underlineLength, 20 , null);
		}
		if(selectedButton==1){
			g2d.drawImage(underline, Game.WIDTH/2-Game.WIDTH/7, Game.HEIGHT/2+2*100-10, underlineLength, 20 , null);	
		}
		if(selectedButton==2){
			g2d.drawImage(underline, Game.WIDTH/2-Game.WIDTH/7, Game.HEIGHT/2+3*100-10, underlineLength, 20 , null);	
		}

		
	}

	
	
}
