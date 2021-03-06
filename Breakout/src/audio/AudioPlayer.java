package audio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	public static String gameMusicFile = "file:./src/audio/GameMusic.wav";
	public static String testMusicFile = "file:./src/audio/laser.wav";

	public static void playSoundEffectIndefinitely(String soundToPlay) {

		// Pointer towards the resource to play
		URL soundLocation;
		try {
			soundLocation = new URL(soundToPlay);

			// Stores a predefined audio clip
			Clip clip = null;

			// Convert audio data to different playable formats
			clip = AudioSystem.getClip();

			// Holds a stream of a definite length
			AudioInputStream inputStream;

			inputStream = AudioSystem.getAudioInputStream(soundLocation);

			// Make audio clip available for play
			clip.open(inputStream);

			// Define how many times to loop
			clip.loop(0);

			// Play the clip
			clip.start();
		}

		catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
