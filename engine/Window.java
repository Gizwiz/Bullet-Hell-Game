package com.game.engine;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Window extends Canvas {

	/**
		 * 
		 */
	private static final long serialVersionUID = 6010137390490345263L;
	private static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[2];
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean windowed169 = false; // 16:9 res
	public static boolean fullScreen = false;
	public static boolean borderlessFullScreen = true;

	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);

		/*
		 * 854x480 1280x720 1366x768 1920x1080
		 */

		// WINDOWED
		if (windowed169) {
			Game.WIDTH = 1280;
			Game.HEIGHT = 720;
			frame.setPreferredSize(new Dimension(width, height));
			frame.setMaximumSize(new Dimension(width, height));
			frame.setMinimumSize(new Dimension(width, height));
			frame.setLocation(0, 0);
			frame.setUndecorated(false);
		}
		// BORDERLESS FULLSCREEN
		if (borderlessFullScreen) {
			Game.WIDTH = getToolkit().getScreenSize().width;
			Game.HEIGHT = getToolkit().getScreenSize().height;
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setUndecorated(true);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Point hotSpot = new Point(0, 0);
			BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
			Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
			setCursor(invisibleCursor);
		}

		// FULLSCREEN
		if (fullScreen) {
			device.setFullScreenWindow(frame);
			frame.setUndecorated(true);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			// Point hotSpot = new Point(0,0);
			// BufferedImage cursorImage = new BufferedImage(1, 1,
			// BufferedImage.TRANSLUCENT);
			// Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage,
			// hotSpot, "InvisibleCursor");
			// setCursor(invisibleCursor);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		frame.requestFocus();
		game.start();

	}
}
