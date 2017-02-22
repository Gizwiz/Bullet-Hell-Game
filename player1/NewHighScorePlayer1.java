package com.game.player1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.game.engine.Game;
import com.game.engine.KeyInput;
import com.game.enums.STATE;



public class NewHighScorePlayer1{
	
private int[] scoreArray = new int[11];
private int[] readerScoreArray = new int[10];
private int playerScore = 0;
private int i = 0;
private int blink = 150; //FOR RENDER ARROW BLINK
private String fileName = "bin/used/highScores1.txt";
private String scoreString;
String line = null;
private Image arrow;

public NewHighScorePlayer1(){
	KeyInput.keySix = false;
	read();
	
}
public void read(){
	try {
		arrow = ImageIO.read(new File("bin/used/arrowRight.png"));
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

        
   
        bufferedReader.close();            
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	playerScore = (int) Player1.playerScore;
	if (playerScore > readerScoreArray[0] && playerScore>0){
	scoreArray[0] = playerScore;
	Arrays.sort(scoreArray);
	System.out.println("reading");
	write();
	}
	
	
}

public void write(){
	try{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);	
		  for(int j = 1; j<scoreArray.length; j++){
				String writerScoreString = Integer.toString(scoreArray[j]);
				bufferedWriter.write(writerScoreString+"\n");
			}
		bufferedWriter.close();
	} catch (IOException e){
		e.printStackTrace();
	}
	playerScore = 0;
	Game.reRead = true;
}

public void render(Graphics2D g2d){
	Font fnt = new Font("Lucida Console", 3, 25);
	Font fnt2 = new Font("Lucida Console", 1, 50);
	g2d.setFont(fnt);
	g2d.setColor(Color.white);
	g2d.drawString("player1 high scores",350, 100);
	g2d.setFont(fnt);
	
	for(int a = 1; a<10; a++){
	g2d.drawString(a+".....", (a*-10)+425, a*50+180);
	}
	
	//RENDERS THE SCORE
	for(int b = 1; b<scoreArray.length-1; b++){
	scoreString = Integer.toString(scoreArray[b]);
	g2d.drawString(scoreString, (b*10)+450, b*-50+685);
	}
	
	g2d.setFont(fnt2);
	g2d.drawString("Press shoot to continue", 350, 800);
	
	blink--;
	if(blink <= 800){
	g2d.drawImage(arrow, 290,762,50,50,null);
	if(blink == 0){
	blink = 1600;
	}
	}
	
	if (KeyInput.keySix){
		KeyInput.keySix = false;
		Game.gameState = STATE.GameOver;
	}
}


}


