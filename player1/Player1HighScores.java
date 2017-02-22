package com.game.player1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.KeyInput;
import com.game.enums.STATE;

public class Player1HighScores{
	
	public static int bestScore; //FOR GAMEHUD RENDER
	private int[] scoreArray = new int[11];
	private int[] readerScoreArray = new int[10];
	private int i=0;
	private String line = null;
	private boolean rowsLeft = true, end = false;
	private Image player, arrowRight, arrowLeft;
	private String fileName = "bin/used/highScores1.txt";
	private BufferedReader reader;
	private PrintWriter writer;
	private Font customFont;

	public Player1HighScores(){
		try {
			reader = new BufferedReader(new FileReader(fileName));
			read();
			player = ImageIO.read(new File("bin/used/player1DOWN.png"));
			arrowRight = ImageIO.read(new File("bin/used/arrowRight.png"));
			arrowLeft = ImageIO.read(new File("bin/used/arrowLeft.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		KeyInput.keySix = false;
		
		 try {
			 customFont = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")).deriveFont(75f);
			GraphicsEnvironment ge = 
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/Colonna MT.ttf")));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException{
		try {
	        FileReader fileReader = new FileReader(fileName);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        while((line = bufferedReader.readLine()) != null) {
	        	readerScoreArray[i] = Integer.parseInt(line);
	        	scoreArray[i+1] = readerScoreArray[i];
	        	if(i<9){
	        	i++;
	        	}
	        } 
	        if(i==10){
	        	i=0;
	        }

	        Arrays.sort(scoreArray);
	        bestScore = scoreArray[9];
	        bufferedReader.close();            
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void render(Graphics2D g2d) {
		if(KeyInput.keyThree){
			KeyInput.keyThree = false;
			Game.gameState = STATE.Player3HighScores;
		}
		if(KeyInput.keyFour){
			KeyInput.keyFour = false;
			Game.gameState = STATE.Player2HighScores;
		}
		if(KeyInput.keyEight == true){
			KeyInput.keyEight = false;
			Game.gameState = STATE.MainMenu;
		}
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2d.drawImage(player, 810, 240, 245, 400, null);
		g2d.drawImage(arrowRight, 1100, 400, 100, 100, null);
		g2d.drawImage(arrowLeft, 660, 400, 100, 100, null);

		
		Font fnt = new Font("Lucida Console", 3, 45);
		Font fnt2 = new Font("Arial", 1, 65);
		for(int j = 1; j<scoreArray.length-1; j++){
			String scoreString = Integer.toString(scoreArray[j]);
						
			g2d.setColor(Color.WHITE);
			g2d.setFont(fnt2);
			g2d.drawString("player1 high scores",350, 100);
			g2d.setFont(customFont);
			g2d.drawString(scoreString, (j*10)+300, j*-60+810);
			g2d.drawString(j+".....", (j*-10)+180, j*60+200);

		}

	}

}
