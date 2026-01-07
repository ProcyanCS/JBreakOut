package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/audio/direction_change.wav");
		soundURL[1] = getClass().getResource("/audio/glass_break_big.wav");
		soundURL[2] = getClass().getResource("/audio/glass_break_small.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
