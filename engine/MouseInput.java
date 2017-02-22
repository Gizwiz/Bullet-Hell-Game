package com.game.engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	protected Handler handler;
	public static boolean mouseClicked = false;
	public static MouseEvent event;
	
	public MouseInput(Handler handler){
		this.handler = handler;
	}
	
	public void mouseClicked(MouseEvent arg0, int xMin, int xMax, int yMin, int yMax) {
//		
//		mouseClicked = true;
//		System.out.println(""+arg0.getX());
	}

	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
//		event = arg0;
//		mouseClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		event = arg0;
		mouseClicked = true;
	}

	public static int getX(){
		
		return event.getX();
	}
	
	public static int getY(){
		
		return event.getY();
	}


	
	
	
	
}
