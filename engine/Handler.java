package com.game.engine;

import java.awt.Graphics2D;
import java.util.LinkedList;

import com.game.enums.ID;
import com.game.enums.STATE;

public class Handler {
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick(){
		//loop objects and tick
		for (int i=0; i < object.size(); i++){
			if (Game.gameState == STATE.Level || 
				Game.gameState == STATE.TestLevel
						){
			GameObject tempObject = object.get(i); //id of object
			tempObject.tick();
			}
		}
	}
	public void render(Graphics2D g2d){
		//loop objects and render
		for (int i=0; i < object.size(); i++){
			if (Game.gameState == STATE.Level ||
					Game.gameState == STATE.TestLevel
						){
			GameObject tempObject = object.get(i); //id of object
			tempObject.render(g2d);
			}
			
	}
	}
	public void clearEnemies(){
		
		//Loops through all objects and removes. Loops many, many times because I am incompetent. Otherwise will not remove everything
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
			for (int i=0; i < object.size(); i++){
				GameObject tempObject = object.get(i); //id of object
				removeObject(tempObject);
		}
	}
	
	public void clearBullets(){
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
			for(int i = 0; i< object.size(); i++){
				GameObject tempObject = object.get(i);
				if(tempObject.id == ID.EnemyBullet){
					removeObject(tempObject);
				}
			}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
		
	}
	
	public void removeObject(GameObject object){

		this.object.remove(object);

	}
	
	public boolean checkExistance(GameObject object){
		
		return true;
	}
}
