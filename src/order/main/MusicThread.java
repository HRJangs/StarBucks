//음악이 재생되고있음

package order.main;

import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MusicThread extends Thread{
	Media hit;
	MediaPlayer mediaPlayer;
	URL[] url;
	int i=0;
	
	
	public MusicThread(URL[] url) {
		this.url = url;
	}
	
	public void next(int num) {
		this.i=num;
		
		hit = new Media(url[i].toString());
		mediaPlayer=new MediaPlayer(hit);
		run();
		while(mediaPlayer==null){
		
			mediaPlayer.stop();
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();
		}	
		
	}
	

	public void run() {
		if(i==0){
			hit = new Media(url[i].toString());
			mediaPlayer=new MediaPlayer(hit);
	
				
			}
		mediaPlayer.play();
		final JFXPanel fxPanel = new JFXPanel();	
	}

}
	
	

