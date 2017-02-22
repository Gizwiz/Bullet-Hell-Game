package com.game.engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import com.game.displays.MainSettings;



public class AudioPlayer {
	
	private Clip clip;
	private long clipTime;
	
	public AudioPlayer (String file){
		
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(file));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat
					(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(),
					16,
					baseFormat.getChannels(),
					baseFormat.getChannels()*2,
					baseFormat.getSampleRate(), false
				);
				AudioInputStream dais = 
						AudioSystem.getAudioInputStream(decodeFormat, ais);
				clip = AudioSystem.getClip();
				clip.open(dais);

		} 
		
		catch (Exception e){
			e.printStackTrace();
		}
	}
		public void play() {

			if (clip == null) return;
			stop();
			clip.setFramePosition(0);

			clip.start();

			}
		public void loop() {
			if (clip == null) return;
			stop();
			clip.setFramePosition(0);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(MainSettings.musicVolume);
			clip.loop(23);
			}
		public void stop(){
			if (clip.isRunning()) {
				clip.stop();
				clipTime = clip.getMicrosecondPosition();
			}
			
		}
		public void resume(){
			if (clip == null) return;
			clip.setMicrosecondPosition(clipTime);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(MainSettings.musicVolume);
			clip.loop(23);
			
		}
		public void close(){
		stop();
		close();
}
	}

